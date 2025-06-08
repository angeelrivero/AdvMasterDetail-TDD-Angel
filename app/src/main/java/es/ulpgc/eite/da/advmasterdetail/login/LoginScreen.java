package es.ulpgc.eite.da.advmasterdetail.login;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.UserDao;
import es.ulpgc.eite.da.advmasterdetail.database.FavoriteDao;
import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;

public class LoginScreen {

  public static void configure(LoginContract.View view) {

    WeakReference<FragmentActivity> context =
            new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = AppMediator.getInstance();
    LoginContract.Presenter presenter = new LoginPresenter(mediator);

    // Uso siempre el singleton global del Mediator
    CatalogDatabase database = mediator.getDatabase(context.get());

    UserDao userDao = database.userDao();
    UserRepository userRepository = new UserRepository(userDao);

    FavoriteDao favoriteDao = database.favoriteDao();
    FavoriteRepository favoriteRepository = new FavoriteRepository(favoriteDao);
    mediator.setFavoriteRepository(favoriteRepository); // ÚNICA instancia global


    LoginContract.Model model = new LoginModel(userRepository);
    presenter.injectModel(model);
    presenter.injectView(new WeakReference<>(view));
    view.injectPresenter(presenter);
  }
}

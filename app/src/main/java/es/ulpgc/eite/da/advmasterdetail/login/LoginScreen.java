package es.ulpgc.eite.da.advmasterdetail.login;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.UserDao;
import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;

public class LoginScreen {

  private static final String DB_NAME = "catalog.db";

  public static void configure(LoginContract.View view) {

    WeakReference<FragmentActivity> context =
            new WeakReference<>((FragmentActivity) view);

    CatalogMediator mediator = CatalogMediator.getInstance();
    LoginContract.Presenter presenter = new LoginPresenter(mediator);

    // Crear base de datos
    CatalogDatabase database = Room.databaseBuilder(
                    context.get().getApplicationContext(),
                    CatalogDatabase.class,
                    DB_NAME
            )
            .fallbackToDestructiveMigration()
            .build();

    // Crear repositorio
    UserDao userDao = database.userDao();
    UserRepository userRepository = new UserRepository(userDao);

    // Crear modelo e inyectar dependencias
    LoginContract.Model model = new LoginModel(userRepository);
    presenter.injectModel(model);
    presenter.injectView(new WeakReference<>(view));
    view.injectPresenter(presenter);
  }
}

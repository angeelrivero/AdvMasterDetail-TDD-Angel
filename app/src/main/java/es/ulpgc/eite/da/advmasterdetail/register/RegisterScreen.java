package es.ulpgc.eite.da.advmasterdetail.register;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.UserDao;
import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;

public class RegisterScreen {

    public static void configure(RegisterContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = AppMediator.getInstance();
        RegisterContract.Presenter presenter = new RegisterPresenter(mediator);

        // *** USA SIEMPRE EL SINGLETON ***
        CatalogDatabase database = mediator.getDatabase(context.get());

        // Crear el repositorio y el modelo
        UserDao userDao = database.userDao();
        UserRepository userRepository = new UserRepository(userDao);
        RegisterContract.Model model = new RegisterModel(userRepository);

        // Inyección MVP
        presenter.injectModel(model);
        presenter.injectView(new WeakReference<>(view));
        view.injectPresenter(presenter);
    }
}

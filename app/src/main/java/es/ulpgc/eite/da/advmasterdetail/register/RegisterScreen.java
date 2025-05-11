package es.ulpgc.eite.da.advmasterdetail.register;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.room.Room;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.UserDao;
import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;

public class RegisterScreen {

    private static final String DB_NAME = "catalog.db";

    public static void configure(RegisterContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        CatalogMediator mediator = CatalogMediator.getInstance();
        RegisterContract.Presenter presenter = new RegisterPresenter(mediator);

        // Instanciar la base de datos
        CatalogDatabase database = Room.databaseBuilder(
                        context.get().getApplicationContext(),
                        CatalogDatabase.class,
                        DB_NAME
                )
                .fallbackToDestructiveMigration()
                .build();

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

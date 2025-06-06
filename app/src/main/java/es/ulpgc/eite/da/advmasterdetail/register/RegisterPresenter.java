package es.ulpgc.eite.da.advmasterdetail.register;

import java.lang.ref.WeakReference;
import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;

public class RegisterPresenter implements RegisterContract.Presenter {

    public static final String TAG = "RegisterPresenter";

    private WeakReference<RegisterContract.View> view;
    private AppMediator mediator;
    private RegisterContract.Model model;
    private RegisterState state;

    public RegisterPresenter(AppMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        Log.d(TAG, "onCreateCalled()");
        state = new RegisterState();
    }

    @Override
    public void onRecreateCalled() {
        Log.d(TAG, "onRecreateCalled()");
        state = mediator.getRegisterScreenState();
    }

    @Override
    public void onResumeCalled() {
        Log.d(TAG, "onResumeCalled()");
        view.get().injectPresenter(this); // por si se reconstruye
    }

    @Override
    public void onPauseCalled() {
        Log.d(TAG, "onPauseCalled()");
        mediator.setRegisterScreenState(state);
    }

    @Override
    public void onDestroyCalled() {
        Log.d(TAG, "onDestroyCalled()");
    }

    @Override
    public void onBackButtonPressed() {
        Log.d(TAG, "onBackButtonPressed()");
        view.get().navigateToPreviousScreen(); // ← vuelve al Login
    }

    @Override
    public void onRegisterButtonClicked(String username, String email, String password, String repeatPassword) {
        Log.d(TAG, "onRegisterButtonClicked()");

        // Validación simple
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            view.get().showValidationError("Rellena todos los campos.");
            return;
        }

        if (!password.equals(repeatPassword)) {
            view.get().showValidationError("Las contraseñas no coinciden.");
            return;
        }

        // Llamada asíncrona para comprobar si existe el usuario
        model.userExists(username, email, exists -> {
            if (exists) {
                view.get().showValidationError("Este usuario ya existe.");
                return;
            }
            // Si no existe, guarda el usuario también asíncrono
            model.saveUser(username, email, password, () -> {
                view.get().finishRegistration();
            });
        });
    }

    @Override
    public void injectView(WeakReference<RegisterContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(RegisterContract.Model model) {
        this.model = model;
    }
}

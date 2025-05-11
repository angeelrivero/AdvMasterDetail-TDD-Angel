package es.ulpgc.eite.da.advmasterdetail.register;

import java.lang.ref.WeakReference;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;

public class RegisterPresenter implements RegisterContract.Presenter {

    public static final String TAG = "RegisterPresenter";

    private WeakReference<RegisterContract.View> view;
    private CatalogMediator mediator;
    private RegisterContract.Model model;
    private RegisterState state;

    public RegisterPresenter(CatalogMediator mediator) {
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

        if (model.userExists(username, email)) {
            view.get().showValidationError("Este usuario ya existe.");
            return;
        }

        // Guardar usuario en la base de datos
        model.saveUser(username, email, password);
        view.get().finishRegistration();
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

package es.ulpgc.eite.da.advmasterdetail.login;
import android.os.Handler;
import android.os.Looper;

import java.lang.ref.WeakReference;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;

public class LoginPresenter implements LoginContract.Presenter {

    public static final String TAG = "LoginPresenter";

    private WeakReference<LoginContract.View> view;
    private CatalogMediator mediator;
    private LoginContract.Model model;
    private LoginState state;

    public LoginPresenter(CatalogMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void onCreateCalled() {
        Log.d(TAG, "onCreateCalled()");
        state = new LoginState();
    }

    @Override
    public void onRecreateCalled() {
        Log.d(TAG, "onRecreateCalled()");
        state = mediator.getLoginScreenState();
    }

    @Override
    public void onResumeCalled() {
        Log.d(TAG, "onResumeCalled()");
    }

    @Override
    public void onPauseCalled() {
        Log.d(TAG, "onPauseCalled()");
        mediator.setLoginScreenState(state);
    }

    @Override
    public void onDestroyCalled() {
        Log.d(TAG, "onDestroyCalled()");
    }

    @Override
    public void onBackButtonPressed() {
        Log.d(TAG, "onBackButtonPressed()");
        // No hacemos nada, dejar que se cierre la app
    }

    @Override
    public void onLoginButtonClicked(String username, String password) {
        Log.d(TAG, "onLoginButtonClicked()");

        if (username.isEmpty() || password.isEmpty()) {
            new Handler(Looper.getMainLooper()).post(() -> {
                view.get().showLoginError("Introduce usuario y contraseña.");
            });
            return;
        }

        model.validateUser(username, password, isValid -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (isValid) {
                    Log.d(TAG, "Usuario válido. Navegando a lista.");
                    view.get().navigateToMovieList();
                } else {
                    Log.d(TAG, "Credenciales incorrectas.");
                    view.get().showLoginError("Usuario o contraseña incorrectos.");
                }
            });
        });
    }



    @Override
    public void onGuestButtonClicked() {
        Log.d(TAG, "onGuestButtonClicked()");
        view.get().navigateAsGuest();
    }

    @Override
    public void onSignUpClicked() {
        Log.d(TAG, "onSignUpClicked()");
        view.get().navigateToRegisterScreen();
    }

    @Override
    public void injectView(WeakReference<LoginContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(LoginContract.Model model) {
        this.model = model;
    }
}

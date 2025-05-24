package es.ulpgc.eite.da.advmasterdetail.login;

import android.os.Handler;
import android.os.Looper;
import java.lang.ref.WeakReference;
import android.util.Log;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.app.LoginToMovieListState;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class LoginPresenter implements LoginContract.Presenter {

    public static final String TAG = "LoginPresenter";
    private WeakReference<LoginContract.View> view;
    private AppMediator mediator;
    private LoginContract.Model model;
    private LoginState state;

    public LoginPresenter(AppMediator mediator) {
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

        // Espera el UserItem real para guardar el userId
        model.validateUser(username, password, user -> {
            new Handler(Looper.getMainLooper()).post(() -> {
                if (user != null) {
                    Log.d(TAG, "Usuario válido. Navegando a lista. ID=" + user.id);
                    state.userId = user.id;
                    mediator.setLoginScreenState(state);
                    LoginToMovieListState stateToList = new LoginToMovieListState();
                    stateToList.loggedWithAccount = true;
                    mediator.setLoginToMovieListState(stateToList);
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
        LoginToMovieListState state = new LoginToMovieListState();
        state.loggedWithAccount = false;
        mediator.setLoginToMovieListState(state);
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

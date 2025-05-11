package es.ulpgc.eite.da.advmasterdetail.login;

import java.lang.ref.WeakReference;

public interface LoginContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void showLoginError(String message);

        void navigateToMovieList();        // Login exitoso
        void navigateToRegisterScreen();   // Al pulsar "Sign Up"
        void navigateAsGuest();            // Iniciar sin cuenta
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        void onCreateCalled();
        void onRecreateCalled();
        void onResumeCalled();
        void onPauseCalled();
        void onDestroyCalled();
        void onBackButtonPressed();

        void onLoginButtonClicked(String username, String password);
        void onGuestButtonClicked();
        void onSignUpClicked();
    }

    interface Model {
        boolean validateUser(String username, String password);
    }
}

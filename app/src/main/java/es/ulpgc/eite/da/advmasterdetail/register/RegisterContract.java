package es.ulpgc.eite.da.advmasterdetail.register;

import java.lang.ref.WeakReference;

public interface RegisterContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void showValidationError(String message);

        void finishRegistration();

        void navigateToPreviousScreen();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        void onCreateCalled();
        void onResumeCalled();
        void onRecreateCalled();
        void onPauseCalled();
        void onDestroyCalled();
        void onBackButtonPressed();

        void onRegisterButtonClicked(String username, String email, String password, String repeatPassword);
    }

    interface Model {
        boolean userExists(String username, String email);

        void saveUser(String username, String email, String password);
    }
}

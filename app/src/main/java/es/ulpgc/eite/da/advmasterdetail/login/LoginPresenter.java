package es.ulpgc.eite.da.advmasterdetail.login;

/*
Breve comentario sobre estos 2 imports:
Android no permite modificar la interfaz gráfica (UI) desde un hilo que no sea el principal (main thread).
Esto es para evitar problemas de sincronización, bloqueos y errores visuales.
Toast, setText, startActivity, mostrar diálogos, etc., todo eso debe hacerse en el main thread.

Cuando usamos Room (o cualquier consulta a base de datos), por buenas prácticas debes hacerlo fuera del hilo principal
(para no bloquear la app). Pero, cuando terminas y quieres notificar al usuario, debes "volver" al main thread.

 */

import android.os.Handler; //Permite enviar tareas (bloques de código) a un hilo específico.
import android.os.Looper; //Obtiene el "loop" principal, es decir, el hilo principal de la app.

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
        // Este método se llama cuando el usuario pulsa el botón de login.
        Log.d(TAG, "onLoginButtonClicked()");

        // Primero, compruebo si el usuario o la contraseña están vacíos.
        if (username.isEmpty() || password.isEmpty()) {
            // Como quiero mostrar un mensaje en la interfaz, y por seguridad Android no permite modificar la UI desde otro hilo,
            // uso un Handler junto con Looper.getMainLooper() para asegurarme de que el Toast se muestre en el hilo principal (UI thread).
            // Handler permite programar tareas para que se ejecuten en el hilo que yo elija. En este caso, el hilo principal de la app.
            new Handler(Looper.getMainLooper()).post(() -> {
                // Muestro un mensaje de error pidiendo que introduzca usuario y contraseña.
                view.get().showLoginError("Introduce usuario y contraseña.");
            });
            return;
        }

        // Si los campos no están vacíos, pido al modelo que valide el usuario y la contraseña.
        model.validateUser(username, password, isValid -> {
            // El modelo valida el usuario en un hilo secundario (background thread) para no bloquear la interfaz.
            // Cuando termina la validación, vuelvo otra vez al hilo principal usando Handler + Looper.getMainLooper()
            // para poder actualizar la UI con el resultado (éxito o error).
            new Handler(Looper.getMainLooper()).post(() -> {
                if (isValid) {
                    // Si el usuario es válido, navego a la lista de películas.
                    Log.d(TAG, "Usuario válido. Navegando a lista.");
                    view.get().navigateToMovieList();
                } else {
                    // Si el usuario o la contraseña son incorrectos, muestro un mensaje de error.
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

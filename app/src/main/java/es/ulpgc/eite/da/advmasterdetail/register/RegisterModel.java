package es.ulpgc.eite.da.advmasterdetail.register;

import android.util.Log;
import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;

// Implementación del Model para MVP usando métodos asíncronos.
public class RegisterModel implements RegisterContract.Model {

    public static final String TAG = "RegisterModel";

    private final UserRepository userRepository;

    public RegisterModel(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public void userExists(String username, String email, UserExistsCallback callback) {
        Log.d(TAG, "Verificando si el usuario ya existe (asíncrono)...");
        userRepository.userExists(username, email, callback::onResult);
    }

    @Override
    public void saveUser(String username, String email, String password, UserSavedCallback callback) {
        Log.d(TAG, "Guardando nuevo usuario (asíncrono)...");
        userRepository.insertUser(username, email, password, callback::onSaved);
    }
}

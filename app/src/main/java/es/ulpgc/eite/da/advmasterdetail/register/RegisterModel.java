package es.ulpgc.eite.da.advmasterdetail.register;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;

public class RegisterModel implements RegisterContract.Model {

    public static final String TAG = "RegisterModel";

    private final UserRepository userRepository;

    public RegisterModel(UserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean userExists(String username, String email) {
        Log.d(TAG, "Verificando si el usuario ya existe...");
        return userRepository.userExists(username, email);
    }

    @Override
    public void saveUser(String username, String email, String password) {
        Log.d(TAG, "Guardando nuevo usuario...");
        userRepository.insertUser(username, email, password);
    }
}

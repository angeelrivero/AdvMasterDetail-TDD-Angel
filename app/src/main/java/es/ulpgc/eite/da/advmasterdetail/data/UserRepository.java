// UserRepository.java
package es.ulpgc.eite.da.advmasterdetail.data;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import es.ulpgc.eite.da.advmasterdetail.database.UserDao;

// Este repositorio gestiona el acceso asíncrono a los datos de usuario usando Room y Executor.
// Ahora, todas las operaciones de base de datos se hacen en segundo plano (asíncrono),
// cumpliendo así con las buenas prácticas y el requisito del proyecto.

public class UserRepository {

    private final UserDao userDao;
    private final Executor executor = Executors.newSingleThreadExecutor(); // Hilo secundario para Room

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    // Callback para saber si el usuario existe
    public interface UserExistsCallback {
        void onResult(boolean exists);
    }

    // Comprobar si existe un usuario con ese username o email, en segundo plano
    public void userExists(String username, String email, UserExistsCallback callback) {
        executor.execute(() -> {
            boolean exists = userDao.findUserByUsername(username) != null ||
                    userDao.findUserByEmail(email) != null;
            // Notifico el resultado en el hilo principal (UI thread)
            new Handler(Looper.getMainLooper()).post(() -> callback.onResult(exists));
        });
    }

    // Callback para saber cuándo termina de guardar el usuario
    public interface UserSavedCallback {
        void onSaved();
    }

    // Insertar usuario en segundo plano
    public void insertUser(String username, String email, String password, UserSavedCallback callback) {
        executor.execute(() -> {
            UserItem user = new UserItem(username, email, password);
            userDao.insertUser(user);
            Log.d("UserRepository", "Usuario insertado en Room: " + username);
            // Aviso en el hilo principal que ya terminó
            new Handler(Looper.getMainLooper()).post(callback::onSaved);
        });
    }

    public UserItem getUserByCredentials(String username, String password) {
        return userDao.findUserByUsernameAndPassword(username, password);
    }
}

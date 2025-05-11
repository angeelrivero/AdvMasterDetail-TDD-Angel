package es.ulpgc.eite.da.advmasterdetail.login;

import android.util.Log;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class LoginModel implements LoginContract.Model {

  public static final String TAG = "LoginModel";

  private final UserRepository userRepository;
  private final Executor executor = Executors.newSingleThreadExecutor();

  public LoginModel(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void validateUser(String username, String password, ValidateUserCallback callback) {
    executor.execute(() -> {
      Log.d(TAG, "Validando usuario en segundo plano...");
      UserItem user = userRepository.getUserByCredentials(username, password);
      boolean exists = user != null;
      callback.onResult(exists);
    });
  }
}

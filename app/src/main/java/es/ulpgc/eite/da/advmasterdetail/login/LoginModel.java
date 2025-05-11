package es.ulpgc.eite.da.advmasterdetail.login;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.data.UserRepository;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class LoginModel implements LoginContract.Model {

  public static final String TAG = "LoginModel";

  private final UserRepository userRepository;

  public LoginModel(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean validateUser(String username, String password) {
    Log.d(TAG, "Validando usuario...");
    UserItem user = userRepository.getUserByCredentials(username, password);
    return user != null;
  }
}

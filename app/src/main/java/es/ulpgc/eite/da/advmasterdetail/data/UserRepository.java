package es.ulpgc.eite.da.advmasterdetail.data;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.database.UserDao;

public class UserRepository {

    private final UserDao userDao;

    public UserRepository(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean userExists(String username, String email) {
        return userDao.findUserByUsername(username) != null ||
                userDao.findUserByEmail(email) != null;
    }

    public void insertUser(String username, String email, String password) {
        Log.d("UserRepository", "Insertando nuevo usuario");
        UserItem user = new UserItem(username, email, password);
        userDao.insertUser(user);
    }

    public UserItem getUserByCredentials(String email, String password) {
        return userDao.findUserByEmailAndPassword(email, password);
    }
}

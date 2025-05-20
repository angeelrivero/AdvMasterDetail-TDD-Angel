package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    UserItem findUserByUsername(String username);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserItem findUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    UserItem findUserByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    UserItem findUserByUsernameAndPassword(String username, String password);


    @Insert
    void insertUser(UserItem user);
}

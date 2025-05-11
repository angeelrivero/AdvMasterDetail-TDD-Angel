package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;
    public String email;
    public String password;

    public UserItem(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

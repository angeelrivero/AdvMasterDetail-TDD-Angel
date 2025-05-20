package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;


@Database(entities = {MovieItem.class, UserItem.class}, version = 3)
public abstract class CatalogDatabase extends RoomDatabase {
  public abstract MovieDao movieDao();
  public abstract UserDao userDao();

  public abstract FavoriteDao favoriteDao();


}

package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.FavoriteItem;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;


@Database(entities = {MovieItem.class, UserItem.class, FavoriteItem.class}, version = 4, exportSchema = false)
public abstract class CatalogDatabase extends RoomDatabase {
  public abstract MovieDao movieDao();
  public abstract UserDao userDao();

  public abstract FavoriteDao favoriteDao();


}

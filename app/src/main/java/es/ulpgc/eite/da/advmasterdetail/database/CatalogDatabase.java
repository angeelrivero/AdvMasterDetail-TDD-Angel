package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.Converters;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteItem;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;
import androidx.room.TypeConverters;


@Database(entities = {MovieItem.class, UserItem.class, FavoriteItem.class}, version = 4, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CatalogDatabase extends RoomDatabase {
  public abstract MovieDao movieDao();
  public abstract UserDao userDao();

  public abstract FavoriteDao favoriteDao();


}

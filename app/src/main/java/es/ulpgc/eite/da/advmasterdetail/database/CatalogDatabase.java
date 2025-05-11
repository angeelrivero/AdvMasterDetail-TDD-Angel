package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;


@Database(entities = {MovieItem.class}, version = 1)
public abstract class CatalogDatabase extends RoomDatabase {

  public abstract MovieDao movieDao();


}

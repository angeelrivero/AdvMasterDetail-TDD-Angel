package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;


@Dao
public interface ProductDao {


  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertProduct(MovieItem product);

  @Update
  void updateProduct(MovieItem product);

  @Delete
  void deleteProduct(MovieItem product);

  @Query("SELECT * FROM MovieItem")
  List<MovieItem> loadProducts();

  @Query("SELECT * FROM MovieItem WHERE id = :id LIMIT 1")
  MovieItem loadProduct(int id);

  @Query("SELECT * FROM MovieItem WHERE category_id=:categoryId")
  List<MovieItem> loadProducts(final int categoryId);
}

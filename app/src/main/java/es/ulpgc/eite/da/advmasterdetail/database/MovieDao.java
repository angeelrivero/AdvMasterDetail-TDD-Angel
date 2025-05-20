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
public interface MovieDao {

  // Devuelve todas las películas
  @Query("SELECT * FROM movie")
  List<MovieItem> loadMovies();

  // Devuelve una película específica por ID
  @Query("SELECT * FROM movie WHERE id = :id")
  MovieItem loadMovie(int id);

  // Inserta una o más películas
  @Insert
  void insertMovie(MovieItem... movies);

  // Inserta una lista de películas
  @Insert
  void insertMovieList(List<MovieItem> movies);

  // Actualiza una película
  @Update
  void updateMovie(MovieItem movie);


  @Query("DELETE FROM movie")
  void deleteAllMovies();

  // Elimina una película
  @Delete
  void deleteMovie(MovieItem movie);


}


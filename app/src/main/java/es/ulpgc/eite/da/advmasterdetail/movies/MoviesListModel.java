package es.ulpgc.eite.da.advmasterdetail.movies;

import android.util.Log;

import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class MoviesListModel implements MoviesListContract.Model {

  public static final String TAG = MoviesListModel.class.getSimpleName();

  private RepositoryContract repository;

  public MoviesListModel(RepositoryContract repository) {
    this.repository = repository;
  }

  @Override
  public void fetchMovieList(final RepositoryContract.GetMovieListCallback callback) {
    Log.d(TAG, "fetchMovieList() desde modelo");

    repository.loadCatalog(true, error -> {
      Log.d(TAG, "¿Hubo error cargando catálogo?: " + error);
      repository.getMovieList(movies -> {
        Log.d(TAG, "Películas cargadas: " + movies.size());
        callback.setMovieList(movies);
      });
    });
  }

}

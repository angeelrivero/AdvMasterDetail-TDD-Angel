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
    Log.d(TAG, "fetchMovieList()");

    // Llama al repositorio para obtener la lista de películas
    repository.getMovieList(new RepositoryContract.GetMovieListCallback() {
      @Override
      public void setMovieList(List<MovieItem> movies) {
        callback.setMovieList(movies); // Devuelve la lista al presentador
      }
    });
  }
}

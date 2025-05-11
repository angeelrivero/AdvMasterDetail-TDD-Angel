package es.ulpgc.eite.da.advmasterdetail.app;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.movie.MovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.movies.MoviesListState;
import es.ulpgc.eite.da.advmasterdetail.register.RegisterState;

public class CatalogMediator {

  private MoviesListState moviesListState;
  private MovieDetailState movieDetailState;
  private RegisterState registerState;

  private MovieItem selectedMovie;

  private static CatalogMediator INSTANCE;

  private CatalogMediator() {}

  public static void resetInstance() {
    INSTANCE = null;
  }

  public static CatalogMediator getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CatalogMediator();
    }
    return INSTANCE;
  }

  // --- Métodos para guardar/recuperar estado de la lista ---

  public MoviesListState getMoviesListState() {
    return moviesListState;
  }

  public void setMoviesListState(MoviesListState state) {
    this.moviesListState = state;
  }

  // --- Métodos para guardar/recuperar estado del detalle ---

  public MovieDetailState getMovieDetailState() {
    return movieDetailState;
  }

  public void setMovieDetailState(MovieDetailState state) {
    this.movieDetailState = state;
  }

  // --- Película seleccionada (para detalle) ---

  public MovieItem getSelectedMovie() {
    return selectedMovie;
  }

  public void setSelectedMovie(MovieItem item) {
    this.selectedMovie = item;
  }

  public RegisterState getRegisterScreenState() {
    return registerState;
  }

  public void setRegisterScreenState(RegisterState state) {
    this.registerState = state;
  }


}

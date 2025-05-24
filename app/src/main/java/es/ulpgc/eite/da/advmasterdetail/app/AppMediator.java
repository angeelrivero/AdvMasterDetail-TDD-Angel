package es.ulpgc.eite.da.advmasterdetail.app;

import android.content.Context;
import androidx.room.Room;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.favorites.FavoritesState;
import es.ulpgc.eite.da.advmasterdetail.login.LoginState;
import es.ulpgc.eite.da.advmasterdetail.movie.MovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.movies.MoviesListState;
import es.ulpgc.eite.da.advmasterdetail.register.RegisterState;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;

public class AppMediator {

  private MoviesListState moviesListState;
  private MovieDetailState movieDetailState;
  private RegisterState registerState;
  private LoginState loginState;
  private FavoriteRepository favoriteRepository;
  private CatalogRepository catalogRepository;
  private FavoritesState favoritesState;
  private LoginToMovieListState loginToMovieListState;
  private MovieDetailToFavoriteState movieDetailToFavoriteState;
  private FavoriteToMovieDetailState favoriteToMovieDetailState;
  private MovieDetailToMovieListState movieDetailToMovieListState;
  private MovieListToMovieDetailState movieListToMovieDetailState;
  private static CatalogDatabase database;

  private MovieItem selectedMovie;

  private static AppMediator INSTANCE;

  private AppMediator() {}

  public static void resetInstance() {
    INSTANCE = null;
    database = null;
  }

  public static AppMediator getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new AppMediator();
    }
    return INSTANCE;
  }

  // --- ÚNICO punto de acceso a la base de datos ---
  public CatalogDatabase getDatabase(Context context) {
    if (database == null) {
      database = Room.databaseBuilder(
                      context.getApplicationContext(),
                      CatalogDatabase.class,
                      "catalog.db"
              )
              .fallbackToDestructiveMigration()
              .build();
    }
    return database;
  }

  // --- Estados, repos y getters/setters globales ---

  public MoviesListState getMoviesListState() { return moviesListState; }
  public void setMoviesListState(MoviesListState state) { this.moviesListState = state; }

  public MovieDetailState getMovieDetailState() { return movieDetailState; }
  public void setMovieDetailState(MovieDetailState state) { this.movieDetailState = state; }

  public MovieItem getSelectedMovie() { return selectedMovie; }
  public void setSelectedMovie(MovieItem item) { this.selectedMovie = item; }

  public RegisterState getRegisterScreenState() { return registerState; }
  public void setRegisterScreenState(RegisterState state) { this.registerState = state; }

  public LoginState getLoginScreenState() { return loginState; }
  public void setLoginScreenState(LoginState state) { this.loginState = state; }

  public FavoriteRepository getFavoriteRepository() { return favoriteRepository; }
  public void setFavoriteRepository(FavoriteRepository repository) { this.favoriteRepository = repository; }

  public CatalogRepository getCatalogRepository() { return catalogRepository; }
  public void setCatalogRepository(CatalogRepository repository) { this.catalogRepository = repository; }

  public FavoritesState getFavoritesScreenState() { return favoritesState; }
  public void setFavoritesScreenState(FavoritesState state) { this.favoritesState = state; }

  public LoginToMovieListState getLoginToMovieListState() {
    if (loginToMovieListState == null) loginToMovieListState = new LoginToMovieListState();
    return loginToMovieListState;
  }
  public void setLoginToMovieListState(LoginToMovieListState state) { this.loginToMovieListState = state; }

  public MovieDetailToFavoriteState getMovieDetailToFavoriteState() {
    if (movieDetailToFavoriteState == null) movieDetailToFavoriteState = new MovieDetailToFavoriteState();
    return movieDetailToFavoriteState;
  }
  public void setMovieDetailToFavoriteState(MovieDetailToFavoriteState state) { this.movieDetailToFavoriteState = state; }

  public FavoriteToMovieDetailState getFavoriteToMovieDetailState() {
    if (favoriteToMovieDetailState == null) favoriteToMovieDetailState = new FavoriteToMovieDetailState();
    return favoriteToMovieDetailState;
  }
  public void setFavoriteToMovieDetailState(FavoriteToMovieDetailState state) { this.favoriteToMovieDetailState = state; }

  public MovieDetailToMovieListState getMovieDetailToMovieListState() {
    if (movieDetailToMovieListState == null) movieDetailToMovieListState = new MovieDetailToMovieListState();
    return movieDetailToMovieListState;
  }
  public void setMovieDetailToMovieListState(MovieDetailToMovieListState state) { this.movieDetailToMovieListState = state; }

  public MovieListToMovieDetailState getMovieListToMovieDetailState() {
    if (movieListToMovieDetailState == null) movieListToMovieDetailState = new MovieListToMovieDetailState();
    return movieListToMovieDetailState;
  }
  public void setMovieListToMovieDetailState(MovieListToMovieDetailState state) { this.movieListToMovieDetailState = state; }
}

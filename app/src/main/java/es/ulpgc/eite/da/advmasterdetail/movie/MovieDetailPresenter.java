package es.ulpgc.eite.da.advmasterdetail.movie;

import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.app.FavoriteToMovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.app.MovieListToMovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.app.MovieDetailToMovieListState;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

  private WeakReference<MovieDetailContract.View> view;
  private MovieDetailState state;
  private MovieDetailContract.Model model;
  private AppMediator mediator;

  public MovieDetailPresenter(AppMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreateCalled() {
    android.util.Log.d("FavoriteTest", "onCreateCalled() en MovieDetailPresenter");
    // Primero mira si viene desde favoritos
    FavoriteToMovieDetailState fromFavorites = mediator.getFavoriteToMovieDetailState();
    if (fromFavorites != null && fromFavorites.movie != null) {
      state = new MovieDetailState();
      state.movie = fromFavorites.movie;
      state.isFavorite = fromFavorites.isFavorite;
      android.util.Log.d("FavoriteTest", "Recibido de Favoritos: " + state.movie.title + ", isFavorite: " + state.isFavorite);
      mediator.setFavoriteToMovieDetailState(null); // Limpia el estado para evitar problemas de navegación
      return;
    }


    MovieListToMovieDetailState fromList = mediator.getMovieListToMovieDetailState();
    state = new MovieDetailState();
    if (fromList != null) {
      state.movie = fromList.movie;
      state.isFavorite = fromList.isFavorite;
      android.util.Log.d("FavoriteTest", "Recibido de MovieList: " + (state.movie != null ? state.movie.title : "null")
              + ", isFavorite: " + state.isFavorite);
    } else {
      android.util.Log.d("FavoriteTest", "No se recibió estado desde MovieList");
    }
  }

  @Override
  public void onRecreateCalled() {
    state = mediator.getMovieDetailState();
    android.util.Log.d("FavoriteTest", "onRecreateCalled() - Recuperando estado recreado");
  }

  @Override
  public void onResumeCalled() {
    android.util.Log.d("FavoriteTest", "onResumeCalled() en MovieDetailPresenter");
    fetchMovieDetailData();
  }

  @Override
  public void onPauseCalled() {
    android.util.Log.d("FavoriteTest", "onPauseCalled() en MovieDetailPresenter");
    if (state != null && state.movie != null) {
      MovieDetailToMovieListState toList = new MovieDetailToMovieListState();
      toList.movieId = state.movie.id;
      toList.isFavorite = state.isFavorite;
      mediator.setMovieDetailToMovieListState(toList);
      android.util.Log.d("FavoriteTest", "Estado pasado a MoviesList: movieId=" + toList.movieId + ", isFavorite=" + toList.isFavorite);
    }
    mediator.setMovieDetailState(state);
  }

  private MovieItem getMovieFromState() {
    return state != null ? state.movie : null;
  }

  @Override
  public void fetchMovieDetailData() {
    MovieItem movie = getMovieFromState();
    if (movie != null) {
      //CONVIERTE actorsString en actors antes de mostrar la película
      if (movie.actors == null && movie.actorsString != null && !movie.actorsString.isEmpty()) {
        movie.actors = es.ulpgc.eite.da.advmasterdetail.data.Converters.toList(movie.actorsString);
      }

      state.movie = movie;
      FavoriteRepository favoriteRepo = mediator.getFavoriteRepository();
      if (favoriteRepo != null) {
        int userId = getUserId();
        int movieId = movie.id;
        favoriteRepo.isFavorite(userId, movieId, new FavoriteRepository.Callback<Boolean>() {
          @Override
          public void onResult(Boolean isFav) {
            state.isFavorite = isFav;
            android.util.Log.d("FavoriteTest", "fetchMovieDetailData() - isFavorite en BD: " + isFav);
            if (view.get() != null) {
              view.get().displayMovieDetailData(state);
            }
          }
        });
      } else {
        state.isFavorite = false;
        android.util.Log.d("FavoriteTest", "fetchMovieDetailData() - favoriteRepo es null");
        if (view.get() != null) {
          view.get().displayMovieDetailData(state);
        }
      }
    } else {
      android.util.Log.d("FavoriteTest", "fetchMovieDetailData() - movie es null");
    }
  }


  @Override
  public void toggleFavorite() {
    android.util.Log.d("FavoriteTest", "Entrando en toggleFavorite, estado actual: " + state.isFavorite);

    if (state.movie == null) {
      android.util.Log.d("FavoriteTest", "state.movie es null");
      return;
    }

    FavoriteRepository favoriteRepo = mediator.getFavoriteRepository();
    if (favoriteRepo == null) {
      android.util.Log.d("FavoriteTest", "favoriteRepo es null");
      return;
    }

    int userId = getUserId();
    int movieId = state.movie.id;
    boolean newFavoriteStatus = !state.isFavorite;

    android.util.Log.d("FavoriteTest", "Intentando cambiar favorito a: " + newFavoriteStatus);

    if (newFavoriteStatus) {
      favoriteRepo.addFavorite(userId, movieId, new FavoriteRepository.Callback<Void>() {
        @Override
        public void onResult(Void result) {
          android.util.Log.d("FavoriteTest", "Callback addFavorite ejecutado");
          state.isFavorite = true;
          if (view.get() != null) {
            view.get().displayMovieDetailData(state);
          }
        }
      });
    } else {
      favoriteRepo.removeFavorite(userId, movieId, new FavoriteRepository.Callback<Void>() {
        @Override
        public void onResult(Void result) {
          android.util.Log.d("FavoriteTest", "Callback removeFavorite ejecutado");
          state.isFavorite = false;
          if (view.get() != null) {
            view.get().displayMovieDetailData(state);
          }
        }
      });
    }
  }

  private int getUserId() {
    if (mediator.getLoginScreenState() != null) {
      return mediator.getLoginScreenState().userId;
    }
    throw new IllegalStateException("No hay usuario logueado, no se puede guardar favorito.");
  }

  @Override
  public void injectView(WeakReference<MovieDetailContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(MovieDetailContract.Model model) {
    this.model = model;
  }
}

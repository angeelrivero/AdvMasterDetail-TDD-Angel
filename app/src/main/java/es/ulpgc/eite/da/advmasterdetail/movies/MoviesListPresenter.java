package es.ulpgc.eite.da.advmasterdetail.movies;

import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.app.MovieListToMovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.app.MovieDetailToMovieListState;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteItem;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;

public class MoviesListPresenter implements MoviesListContract.Presenter {

  public static final String TAG = "MoviesListPresenter";

  private WeakReference<MoviesListContract.View> view;
  private MoviesListState state;
  private MoviesListContract.Model model;
  private AppMediator mediator;

  public MoviesListPresenter(AppMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreateCalled() {
    Log.d(TAG, "onCreateCalled()");
    state = new MoviesListState();

    model.fetchMovieList(movies -> {
      state.movies = movies;
      if (view.get() != null) {
        view.get().displayMovieListData(state);
      }
    });
  }

  @Override
  public void onRecreateCalled() {
    Log.d(TAG, "onRecreateCalled()");
    state = mediator.getMoviesListState();
    if (view.get() != null) {
      view.get().displayMovieListData(state);
    }
  }

  @Override
  public void onResumeCalled() {
    Log.d(TAG, "onResumeCalled()");

    // Solo loguea el estado recibido (si lo necesitas para debug)
    MovieDetailToMovieListState fromDetail = mediator.getMovieDetailToMovieListState();
    if (fromDetail != null) {
      Log.d(TAG, "onResumeCalled() - MovieDetailToMovieListState movieId: " + fromDetail.movieId + " isFavorite: " + fromDetail.isFavorite);
      mediator.setMovieDetailToMovieListState(null);
    }

    // SIEMPRE refresca la lista y favoritos desde BD
    fetchMovieListData();
  }

  @Override
  public void onPauseCalled() {
    Log.d(TAG, "onPauseCalled()");
    mediator.setMoviesListState(state);
  }

  @Override
  public void onDestroyCalled() {
    Log.d(TAG, "onDestroyCalled()");
    mediator.setMoviesListState(state);
  }

  @Override
  public void onBackButtonPressed() {
    Log.d(TAG, "onBackButtonPressed()");
    mediator.setMoviesListState(state);
  }

  @Override
  public void fetchMovieListData() {
    Log.d(TAG, "fetchMovieListData() desde presenter");

    int userId = mediator.getLoginScreenState() != null ? mediator.getLoginScreenState().userId : -1;
    FavoriteRepository favoriteRepo = mediator.getFavoriteRepository();

    model.fetchMovieList(movies -> {
      if (userId != -1 && favoriteRepo != null) {
        // SIEMPRE consulta favoritos de BD
        favoriteRepo.getFavoritesForUser(userId, favorites -> {
          Set<Integer> favIds = new HashSet<>();
          for (FavoriteItem fav : favorites) favIds.add(fav.movieId);

          MoviesListState viewModel = new MoviesListState();
          viewModel.movies = movies;
          viewModel.favoriteIds = favIds;

          state.movies = movies;
          state.favoriteIds = favIds;

          Log.d(TAG, "fetchMovieListData() - favoritos recuperados: " + favIds);

          if (view.get() != null) {
            view.get().displayMovieListData(viewModel);
          }
        });
      } else {
        MoviesListState viewModel = new MoviesListState();
        viewModel.movies = movies;
        viewModel.favoriteIds = new HashSet<>();

        state.movies = movies;
        state.favoriteIds = new HashSet<>();

        Log.d(TAG, "fetchMovieListData() - sin usuario, favoritos vacíos");

        if (view.get() != null) {
          view.get().displayMovieListData(viewModel);
        }
      }
    });
  }

  @Override
  public void selectedMovieData(MovieItem item) {
    Log.d(TAG, "selectedMovieData(): " + item.title);
    mediator.setSelectedMovie(item);

    boolean isFavorite = state.favoriteIds != null && state.favoriteIds.contains(item.id);
    MovieListToMovieDetailState toDetail = new MovieListToMovieDetailState();
    toDetail.movie = item;
    toDetail.isFavorite = isFavorite;
    mediator.setMovieListToMovieDetailState(toDetail);

    Log.d(TAG, "selectedMovieData() - movieId: " + item.id + " isFavorite: " + isFavorite);

    if (view.get() != null) {
      view.get().navigateToMovieDetailScreen();
    }
  }

  @Override
  public void injectView(WeakReference<MoviesListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(MoviesListContract.Model model) {
    this.model = model;
  }
}

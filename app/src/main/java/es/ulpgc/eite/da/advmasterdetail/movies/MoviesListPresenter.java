package es.ulpgc.eite.da.advmasterdetail.movies;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public class MoviesListPresenter implements MoviesListContract.Presenter {

  public static final String TAG = "MoviesListPresenter";

  private WeakReference<MoviesListContract.View> view;
  private MoviesListState state;
  private MoviesListContract.Model model;
  private CatalogMediator mediator;

  public MoviesListPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreateCalled() {
    Log.d(TAG, "onCreateCalled()");
    state = new MoviesListState(); // inicializa el estado
    fetchMovieListData(); // carga la lista desde el modelo
  }

  @Override
  public void onRecreateCalled() {
    Log.d(TAG, "onRecreateCalled()");
    state = mediator.getMoviesListState(); // recupera estado si se recrea
    if (view.get() != null) {
      view.get().displayMovieListData(state);
    }
  }

  @Override
  public void onPauseCalled() {
    Log.d(TAG, "onPauseCalled()");
    mediator.setMoviesListState(state); // guarda el estado
  }

  @Override
  public void fetchMovieListData() {
    Log.d(TAG, "fetchMovieListData()");

    model.fetchMovieList(movies -> {
      state.movies = movies;
      if (view.get() != null) {
        view.get().displayMovieListData(state);
      }
    });
  }

  @Override
  public void selectedMovieData(MovieItem item) {
    Log.d(TAG, "selectedMovieData(): " + item.title);
    mediator.setSelectedMovie(item); // guarda la película seleccionada
    if (view.get() != null) {
      view.get().navigateToMovieDetailScreen(); // navega al detalle
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

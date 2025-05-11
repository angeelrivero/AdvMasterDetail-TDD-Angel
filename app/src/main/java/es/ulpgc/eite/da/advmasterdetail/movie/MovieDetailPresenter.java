package es.ulpgc.eite.da.advmasterdetail.movie;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public class MovieDetailPresenter implements MovieDetailContract.Presenter {

  public static final String TAG = "MovieDetailPresenter";

  private WeakReference<MovieDetailContract.View> view;
  private MovieDetailState state;
  private MovieDetailContract.Model model;
  private CatalogMediator mediator;

  public MovieDetailPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreateCalled() {
    state = new MovieDetailState();
  }

  @Override
  public void onRecreateCalled() {
    state = mediator.getMovieDetailState();
  }

  @Override
  public void onPauseCalled() {
    mediator.setMovieDetailState(state);
  }

  private MovieItem getMovieFromListScreen() {
    return mediator.getSelectedMovie();
  }

  @Override
  public void fetchMovieDetailData() {
    MovieItem movie = getMovieFromListScreen();
    if (movie != null) {
      state.movie = movie;
    }

    if (view.get() != null) {
      view.get().displayMovieDetailData(state);
    }
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

package es.ulpgc.eite.da.advmasterdetail.favorites;

import java.lang.ref.WeakReference;
import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.app.FavoriteToMovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public class FavoritesPresenter implements FavoritesContract.Presenter {

  public static final String TAG = "FavoritesPresenter";

  private WeakReference<FavoritesContract.View> view;
  private FavoritesContract.Model model;
  private FavoritesState state;
  private AppMediator mediator;

  public FavoritesPresenter(AppMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void injectView(WeakReference<FavoritesContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(FavoritesContract.Model model) {
    this.model = model;
  }

  @Override
  public void onCreateCalled() {
    Log.d(TAG, "onCreateCalled()");
    state = new FavoritesState();
    fetchFavoritesData();
  }

  @Override
  public void onRecreateCalled() {
    Log.d(TAG, "onRecreateCalled()");
    state = mediator.getFavoritesScreenState();
    if (state == null) {    // <--- PROTEGE SI VIENE A NULL
      state = new FavoritesState();
    }
    if (view != null && view.get() != null) {
      FavoritesViewModel vm = new FavoritesViewModel();
      vm.favorites = state.favorites;
      view.get().displayFavoritesData(vm);
    }
  }

  @Override
  public void onPauseCalled() {
    Log.d(TAG, "onPauseCalled()");
    mediator.setFavoritesScreenState(state);
  }

  @Override
  public void onResumeCalled() {
    Log.d(TAG, "onResumeCalled()");
    fetchFavoritesData();
  }

  @Override
  public void fetchFavoritesData() {
    int userId = mediator.getLoginScreenState() != null ? mediator.getLoginScreenState().userId : -1;
    if (userId == -1) {
      if (view != null && view.get() != null) {
        view.get().displayFavoritesData(new FavoritesViewModel());
      }
      return;
    }

    // ---- AQUI: INICIALIZA STATE SI ES NULL ----
    if (state == null) {
      state = new FavoritesState();
    }

    model.fetchFavorites(userId, favorites -> {
      state.favorites = favorites;
      if (view != null && view.get() != null) {
        FavoritesViewModel vm = new FavoritesViewModel();
        vm.favorites = favorites;
        view.get().displayFavoritesData(vm);
      }
    });
  }

  @Override
  public void selectedFavoriteMovieData(MovieItem item) {
    mediator.setSelectedMovie(item);

    // PASA EL ESTADO AL DETALLE (nuevo: FavoriteToMovieDetailState)
    FavoriteToMovieDetailState toDetail = new FavoriteToMovieDetailState();
    toDetail.movie = item;
    toDetail.isFavorite = true; // Si está aquí es porque es favorita
    mediator.setFavoriteToMovieDetailState(toDetail);

    if (view != null && view.get() != null) {
      view.get().navigateToMovieDetailScreen();
    }
  }
}

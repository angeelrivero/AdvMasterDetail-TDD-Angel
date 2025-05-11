package es.ulpgc.eite.da.advmasterdetail.movies;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;


public class MoviesListPresenter implements MoviesListContract.Presenter {

  public static String TAG = "AdvMasterDetail.MoviesListPresenter";

  private WeakReference<MoviesListContract.View> view;
  private MoviesListState state;
  private MoviesListContract.Model model;
  private CatalogMediator mediator;

  public MoviesListPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
  }


  @Override
  public void onCreateCalled() {
    // Log.e(TAG, "onCreateCalled");

    state = new MoviesListState();
  }

  @Override
  public void onRecreateCalled() {
    // Log.e(TAG, "onRecreateCalled");

    state = mediator.getProductListState();
  }

  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled()");

    mediator.setProductListState(state);
  }


  @Override
  public void fetchProductListData() {
    // Log.e(TAG, "fetchProductListData()");

    // set passed state
    CategoryItem category = mediator.getCategory();

    if (category != null) {
      state.category = category;
    }

    // call the model
    model.fetchProductListData(state.category, products -> {
      state.products = products;

      view.get().displayProductListData(state);
    });

  }

  @Override
  public void selectedProductData(MovieItem item) {
    mediator.setProduct(item);
    view.get().navigateToProductDetailScreen();
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

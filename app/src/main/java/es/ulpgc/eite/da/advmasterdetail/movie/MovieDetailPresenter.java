package es.ulpgc.eite.da.advmasterdetail.movie;

import android.util.Log;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;


public class MovieDetailPresenter implements MovieDetailContract.Presenter {

  public static String TAG = "AdvMasterDetail.MovieDetailPresenter";

  private WeakReference<MovieDetailContract.View> view;
  private MovieDetailState state;
  private MovieDetailContract.Model model;
  private CatalogMediator mediator;

  public MovieDetailPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
  }

  @Override
  public void onCreateCalled() {
    // Log.e(TAG, "onCreateCalled");

    state = new MovieDetailState();
  }

  @Override
  public void onRecreateCalled() {
    // Log.e(TAG, "onRecreateCalled");

    state = mediator.getProductDetailState();
  }

  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled()");

    mediator.setProductDetailState(state);
  }


  private MovieItem getDataFromProductListScreen() {

    // set passed state
    CategoryItem category = mediator.getCategory();

    if (category != null) {
      state.category = category;
    }

    MovieItem product = mediator.getProduct();
    return product;
  }


  @Override
  public void fetchProductDetailData() {
    // Log.e(TAG, "fetchProductDetailData()");

    // set passed state
    MovieItem product = getDataFromProductListScreen();
    if(product != null) {
        state.product = product;
    }

    view.get().displayProductDetailData(state);
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

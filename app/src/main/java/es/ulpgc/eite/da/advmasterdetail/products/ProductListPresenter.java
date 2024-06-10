package es.ulpgc.eite.da.advmasterdetail.products;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class ProductListPresenter implements ProductListContract.Presenter {

  public static String TAG = "AdvMasterDetail.ProductListPresenter";

  private WeakReference<ProductListContract.View> view;
  private ProductListState state;
  //private ProductListViewModel viewModel;
  private ProductListContract.Model model;
  //private ProductListContract.Router router;
  private CatalogMediator mediator;

  public ProductListPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
    //state = mediator.getProductListState();
  }


//  public ProductListPresenter(ProductListState state) {
//    this.state = state;
//  }


  @Override
  public void onCreateCalled() {
    // Log.e(TAG, "onCreateCalled");

    state = new ProductListState();
    //mediator.setProductListState(state);

    viewModel = new ProductListViewModel();
  }

  @Override
  public void onRecreateCalled() {
    // Log.e(TAG, "onRecreateCalled");

    state = mediator.getProductListState();

    viewModel = new ProductListViewModel();
  }

  @Override
  public void onPauseCalled() {
    Log.e(TAG, "onPauseCalled()");

    mediator.setProductListState(state);
  }

  //  @Override
//  public void injectRouter(ProductListContract.Router router) {
//    this.router = router;
//  }

  @Override
  public void fetchProductListData() {
    // Log.e(TAG, "fetchProductListData()");

    // set passed state
    CategoryItem category = mediator.getCategory();
    //CategoryItem category = getDataFromCategoryListScreen();
    //CategoryItem category = router.getDataFromCategoryListScreen();

    if (category != null) {
      state.category = category;
    }

    /*// call the model
    model.fetchProductListData(state.category, products -> {
      viewModel.products = products;

      view.get().displayProductListData(viewModel);
    });*/

    // call the model
    model.fetchProductListData(state.category, products -> {
      state.products = products;

      view.get().displayProductListData(state);
    });

  }

//  private void passDataToProductDetailScreen(ProductItem item) {
//
//    mediator.setProduct(item);
//  }

//  private CategoryItem getDataFromCategoryListScreen() {
//    CategoryItem category = mediator.getCategory();
//    return category;
//  }


  @Override
  public void selectedProductData(ProductItem item) {
    //router.passDataToProductDetailScreen(item);
    //passDataToProductDetailScreen(item);
    mediator.setProduct(item);
    //router.navigateToProductDetailScreen();
    view.get().navigateToProductDetailScreen();
  }

  @Override
  public void injectView(WeakReference<ProductListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(ProductListContract.Model model) {
    this.model = model;
  }


}

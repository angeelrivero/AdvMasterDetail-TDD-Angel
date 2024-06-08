package es.ulpgc.eite.da.advmasterdetail.product;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.categories.CategoryListState;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;


public class ProductDetailPresenter implements ProductDetailContract.Presenter {

  public static String TAG = "AdvMasterDetail.ProductDetailPresenter";

  private WeakReference<ProductDetailContract.View> view;
  private ProductDetailState state;
  private ProductDetailContract.Model model;
  //private ProductDetailContract.Router router;
  private CatalogMediator mediator;

  public ProductDetailPresenter(CatalogMediator mediator) {
    this.mediator = mediator;
    //state = mediator.getProductDetailState();
  }

//  public ProductDetailPresenter(ProductDetailState state) {
//    this.state = state;
//  }


  @Override
  public void onCreateCalled() {
    // Log.e(TAG, "onCreateCalled");

    state = new ProductDetailState();
    mediator.setProductDetailState(state);
  }

  @Override
  public void onRecreateCalled() {
    // Log.e(TAG, "onRecreateCalled");

    state = mediator.getProductDetailState();
  }

  @Override
  public void injectView(WeakReference<ProductDetailContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(ProductDetailContract.Model model) {
    this.model = model;
  }

//  @Override
//  public void injectRouter(ProductDetailContract.Router router) {
//    this.router = router;
//  }

  private ProductItem getDataFromProductListScreen() {

    // set passed state
    CategoryItem category = mediator.getCategory();
    //CategoryItem category = getDataFromCategoryListScreen();
    //CategoryItem category = router.getDataFromCategoryListScreen();

    if (category != null) {
      state.category = category;
    }

    ProductItem product = mediator.getProduct();
    return product;
  }

  @Override
  public void fetchProductDetailData() {
    // Log.e(TAG, "fetchProductDetailData()");

    // set passed state
    ProductItem product = getDataFromProductListScreen();
    //ProductItem product = router.getDataFromProductListScreen();
    if(product != null) {
        state.product = product;
    }

    view.get().displayProductDetailData(state);
  }

}

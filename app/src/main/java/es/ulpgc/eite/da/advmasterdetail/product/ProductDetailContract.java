package es.ulpgc.eite.da.advmasterdetail.product;

import java.lang.ref.WeakReference;

interface ProductDetailContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayProductDetailData(ProductDetailViewModel viewModel);
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);
    //void injectRouter(Router router);

    void fetchProductDetailData();

    void onCreateCalled();

    void onRecreateCalled();
  }

  interface Model {

  }

//  interface Router {
//
//    ProductItem getDataFromProductListScreen();
//  }
}
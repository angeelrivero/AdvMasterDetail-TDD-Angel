package es.ulpgc.eite.da.advmasterdetail.product;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;


public class ProductDetailScreen {

  public static void configure(ProductDetailContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    //CatalogMediator mediator = (CatalogMediator) context.get().getApplication();
    CatalogMediator mediator = CatalogMediator.getInstance();
    //ProductDetailState state = mediator.getProductDetailState();

    //ProductDetailContract.Router router = new ProductDetailRouter(mediator);
    //ProductDetailContract.Presenter presenter=new ProductDetailPresenter(state);
    ProductDetailContract.Presenter presenter=new ProductDetailPresenter(mediator);
    ProductDetailModel model = new ProductDetailModel();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    //presenter.injectRouter(router);
    view.injectPresenter(presenter);

  }

}

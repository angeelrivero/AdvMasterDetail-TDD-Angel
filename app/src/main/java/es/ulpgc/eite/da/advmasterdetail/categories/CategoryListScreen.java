package es.ulpgc.eite.da.advmasterdetail.categories;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class CategoryListScreen {

  public static void configure(CategoryListContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    //CatalogMediator mediator = (CatalogMediator) context.get().getApplication();
    CatalogMediator mediator = CatalogMediator.getInstance();
    //CategoryListState state = mediator.getCategoryListState();
    RepositoryContract repository = CatalogRepository.getInstance(context.get());

    //CategoryListContract.Router router = new CategoryListRouter(mediator);
    //CategoryListContract.Presenter presenter=new CategoryListPresenter(state);
    CategoryListContract.Presenter presenter=new CategoryListPresenter(mediator);
    CategoryListModel model = new CategoryListModel(repository);
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    //presenter.injectRouter(router);
    view.injectPresenter(presenter);

  }


}

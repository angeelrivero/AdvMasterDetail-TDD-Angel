package es.ulpgc.eite.da.advmasterdetail.movies;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class MoviesListScreen {

  public static void configure(MoviesListContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    CatalogMediator mediator = CatalogMediator.getInstance();
    MoviesListContract.Presenter presenter = new MoviesListPresenter(mediator);

    RepositoryContract repository = CatalogRepository.getInstance(context.get());
    MoviesListModel model = new MoviesListModel(repository);

    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    view.injectPresenter(presenter);

  }


}

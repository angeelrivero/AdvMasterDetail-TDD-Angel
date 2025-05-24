package es.ulpgc.eite.da.advmasterdetail.movies;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class MoviesListScreen {

  public static void configure(MoviesListContract.View view) {

    WeakReference<FragmentActivity> context =
            new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = AppMediator.getInstance();
    MoviesListContract.Presenter presenter = new MoviesListPresenter(mediator);

    // Usa SIEMPRE la instancia global de CatalogRepository, si la necesitas
    RepositoryContract repository = mediator.getCatalogRepository();
    if (repository == null) {
      repository = CatalogRepository.getInstance(context.get());
      mediator.setCatalogRepository((CatalogRepository) repository);
    }

    MoviesListModel model = new MoviesListModel(repository);
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    view.injectPresenter(presenter);
  }
}

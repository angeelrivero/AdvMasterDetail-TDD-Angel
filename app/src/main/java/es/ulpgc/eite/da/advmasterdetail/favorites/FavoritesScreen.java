package es.ulpgc.eite.da.advmasterdetail.favorites;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;

public class FavoritesScreen {

  public static void configure(FavoritesContract.View view) {

    WeakReference<FragmentActivity> context =
            new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = AppMediator.getInstance();
    FavoritesContract.Presenter presenter = new FavoritesPresenter(mediator);

    // SIEMPRE el repo global
    FavoriteRepository favoriteRepository = mediator.getFavoriteRepository();
    if (favoriteRepository == null) {
      favoriteRepository = new FavoriteRepository(
              mediator.getDatabase(context.get()).favoriteDao()
      );
      mediator.setFavoriteRepository(favoriteRepository);
    }

    // El repo de pelis igual, solo si hace falta
    CatalogRepository movieRepo = mediator.getCatalogRepository();
    if (movieRepo == null) {
      movieRepo = (CatalogRepository) CatalogRepository.getInstance(context.get());
      mediator.setCatalogRepository(movieRepo);
    }

    FavoritesContract.Model model = new FavoritesModel(favoriteRepository, movieRepo);
    presenter.injectModel(model);
    presenter.injectView(new WeakReference<>(view));
    view.injectPresenter(presenter);
  }
}

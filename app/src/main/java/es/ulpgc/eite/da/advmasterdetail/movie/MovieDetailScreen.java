package es.ulpgc.eite.da.advmasterdetail.movie;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.app.CatalogMediator;


public class MovieDetailScreen {

  public static void configure(MovieDetailContract.View view) {

    WeakReference<FragmentActivity> context =
        new WeakReference<>((FragmentActivity) view);

    CatalogMediator mediator = CatalogMediator.getInstance();
    MovieDetailContract.Presenter presenter=new MovieDetailPresenter(mediator);

    MovieDetailModel model = new MovieDetailModel();

    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    view.injectPresenter(presenter);

  }

}

package es.ulpgc.eite.da.advmasterdetail.movie;

import androidx.fragment.app.FragmentActivity;
import java.lang.ref.WeakReference;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;

public class MovieDetailScreen {

  public static void configure(MovieDetailContract.View view) {

    WeakReference<FragmentActivity> context =
            new WeakReference<>((FragmentActivity) view);

    AppMediator mediator = AppMediator.getInstance();
    MovieDetailContract.Presenter presenter = new MovieDetailPresenter(mediator);

    // Si tu MovieDetailModel necesitara BD, accede SIEMPRE al repo global
    MovieDetailModel model = new MovieDetailModel();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    view.injectPresenter(presenter);
  }
}

package es.ulpgc.eite.da.advmasterdetail.movie;

import java.lang.ref.WeakReference;

public interface MovieDetailContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayMovieDetailData(MovieDetailViewModel viewModel); // cambiado
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    void fetchMovieDetailData();

    void toggleFavorite();

    void onCreateCalled();
    void onRecreateCalled();
    void onPauseCalled();

    void onResumeCalled();
  }

  interface Model {
    // Por ahora vacío, podrías añadir funciones si necesitas actualizar favoritos, por ejemplo
  }
}

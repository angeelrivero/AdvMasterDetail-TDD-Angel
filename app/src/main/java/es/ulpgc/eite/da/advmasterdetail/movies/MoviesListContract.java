package es.ulpgc.eite.da.advmasterdetail.movies;

import java.lang.ref.WeakReference;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public interface MoviesListContract {

  interface View {
    void injectPresenter(Presenter presenter);

    // Mostrar los datos en pantalla
    void displayMovieListData(MoviesListViewModel viewModel);

    // Navegar al detalle
    void navigateToMovieDetailScreen();
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    // Cargar los datos desde el modelo
    void fetchMovieListData();

    // Guardar el item seleccionado para ir al detalle
    void selectedMovieData(MovieItem item);

    // Ciclo de vida
    void onCreateCalled();
    void onRecreateCalled();
    void onPauseCalled();
  }

  interface Model {
    // Pide los datos al repositorio
    void fetchMovieList(RepositoryContract.GetMovieListCallback callback);
  }
}

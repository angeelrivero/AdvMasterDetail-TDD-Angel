package es.ulpgc.eite.da.advmasterdetail.favorites;

import java.lang.ref.WeakReference;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public interface FavoritesContract {

    interface View {
        void injectPresenter(Presenter presenter);

        // Mostrar lista de películas favoritas
        void displayFavoritesData(FavoritesViewModel viewModel);

        // Navegar a detalle de película
        void navigateToMovieDetailScreen();

        void navigateToPreviousScreen();
    }

    interface Presenter {
        void injectView(WeakReference<View> view);
        void injectModel(Model model);

        // Ciclo de vida
        void onCreateCalled();
        void onRecreateCalled();
        void onPauseCalled();
        void onResumeCalled();

        void onBackPressed();

        void onDestroyCalled();

        // Cargar las películas favoritas
        void fetchFavoritesData();

        // Manejar selección de película favorita
        void selectedFavoriteMovieData(MovieItem item);
    }

    interface Model {
        // Pide la lista de películas favoritas para un usuario al repositorio
        void fetchFavorites(int userId, FetchFavoritesCallback callback);

        interface FetchFavoritesCallback {
            void onFavoritesFetched(List<MovieItem> favorites);
        }
    }
}

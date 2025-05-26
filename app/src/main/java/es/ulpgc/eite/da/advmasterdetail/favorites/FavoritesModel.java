package es.ulpgc.eite.da.advmasterdetail.favorites;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.FavoriteItem;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

public class FavoritesModel implements FavoritesContract.Model {

    private static final String TAG = "FavoritesModel";

    private final FavoriteRepository favoriteRepository;
    private final RepositoryContract movieRepository;

    public FavoritesModel(FavoriteRepository favoriteRepository, RepositoryContract movieRepository) {
        this.favoriteRepository = favoriteRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void fetchFavorites(int userId, FetchFavoritesCallback callback) {
        Log.d(TAG, "fetchFavorites for userId: " + userId);

        // Lanzamos TODO en segundo plano:
        new Thread(() -> {
            List<MovieItem> favoriteMovies = new ArrayList<>();
            List<FavoriteItem> favoriteItems = favoriteRepository.getFavoritesForUserSync(userId);
            if (favoriteItems != null && !favoriteItems.isEmpty()) {
                for (FavoriteItem favorite : favoriteItems) {
                    MovieItem movie = movieRepository.getMovieByIdSync(favorite.movieId);
                    if (movie != null) {
                        favoriteMovies.add(movie);
                    }
                }
            }

            // Ahora devolvemos el resultado al hilo principal
            new android.os.Handler(android.os.Looper.getMainLooper()).post(() ->
                    callback.onFavoritesFetched(favoriteMovies)
            );
        }).start();
    }
}

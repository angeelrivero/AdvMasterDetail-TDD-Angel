package es.ulpgc.eite.da.advmasterdetail.data;

import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import es.ulpgc.eite.da.advmasterdetail.database.FavoriteDao;

// Repositorio de favoritos, encargado de las operaciones asíncronas sobre la tabla de favoritos
public class FavoriteRepository {

    private final FavoriteDao favoriteDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public FavoriteRepository(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    // Callback genérico
    public interface Callback<T> {
        void onResult(T result);
    }

    // Añadir favorito (usuario marca una película como favorita)
    public void addFavorite(int userId, int movieId, Callback<Void> callback) {
        executor.execute(() -> {
            favoriteDao.insertFavorite(new FavoriteItem(userId, movieId));
            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onResult(null));
            }
        });
    }

    // Quitar favorito (usuario desmarca una película como favorita)
    public void removeFavorite(int userId, int movieId, Callback<Void> callback) {
        executor.execute(() -> {
            favoriteDao.deleteFavorite(userId, movieId);
            if (callback != null) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onResult(null));
            }
        });
    }

    // Consultar si una película es favorita de un usuario
    public void isFavorite(int userId, int movieId, Callback<Boolean> callback) {
        executor.execute(() -> {
            boolean isFav = favoriteDao.isFavorite(userId, movieId);
            new Handler(Looper.getMainLooper()).post(() -> callback.onResult(isFav));
        });
    }

    // Obtener la lista de favoritos de un usuario
    public void getFavoritesForUser(int userId, Callback<List<FavoriteItem>> callback) {
        executor.execute(() -> {
            List<FavoriteItem> favorites = favoriteDao.getFavoritesForUser(userId);
            new Handler(Looper.getMainLooper()).post(() -> callback.onResult(favorites));
        });
    }
}

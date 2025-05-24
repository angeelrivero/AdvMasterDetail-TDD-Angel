package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.FavoriteItem;

@Dao
public interface FavoriteDao {

    // Insertar un favorito
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertFavorite(FavoriteItem favorite);

    // Eliminar un favorito (usuario-quita-peli)
    @Query("DELETE FROM favorites WHERE userId = :userId AND movieId = :movieId")
    void deleteFavorite(int userId, int movieId);

    // Obtener todos los favoritos de un usuario
    @Query("SELECT * FROM favorites WHERE userId = :userId")
    List<FavoriteItem> getFavoritesForUser(int userId);

    // Comprobar si una película es favorita de un usuario concreto
    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND movieId = :movieId)")
    boolean isFavorite(int userId, int movieId);
}

package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

// Entidad tabla intermedia que representa una relación N:M entre usuarios y películas.
// Cada fila indica que el usuario con userId ha marcado como favorita la película con movieId.

@Entity(
        tableName = "favorites",
        primaryKeys = {"userId", "movieId"},
        foreignKeys = {
                @ForeignKey(
                        entity = UserItem.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE // Si borro el usuario, borro sus favoritos
                ),
                @ForeignKey(
                        entity = MovieItem.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE // Si borro la película, borro los favoritos asociados
                )
        },
        indices = {
                @Index("userId"),
                @Index("movieId")
        }
)
public class FavoriteItem {
    public int userId;
    public int movieId;

    // Constructor necesario para Room
    public FavoriteItem(int userId, int movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }
}

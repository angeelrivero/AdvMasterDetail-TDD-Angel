package es.ulpgc.eite.da.advmasterdetail.data;

import java.util.List;

public interface RepositoryContract {

  // Callback para saber si la carga inicial del catálogo fue exitosa
  interface FetchCatalogDataCallback {
    void onCatalogDataFetched(boolean error);
  }

  // Callback para devolver la lista de películas
  interface GetMovieListCallback {
    void setMovieList(List<MovieItem> movies);
  }

  // Callback para devolver una película concreta
  interface GetMovieCallback {
    void setMovie(MovieItem movie);
  }

  // Callback para borrar una película
  interface DeleteMovieCallback {
    void onMovieDeleted();
  }

  // Callback para actualizar una película
  interface UpdateMovieCallback {
    void onMovieUpdated();
  }

  // Cargar el catálogo desde JSON (si es primera ejecución)
  void loadCatalog(boolean clearFirst, FetchCatalogDataCallback callback);

  // Obtener todas las películas
  void getMovieList(GetMovieListCallback callback);

  // Obtener una película por ID
  void getMovie(int id, GetMovieCallback callback);

  // Eliminar una película
  void deleteMovie(MovieItem movie, DeleteMovieCallback callback);

  // Actualizar una película
  void updateMovie(MovieItem movie, UpdateMovieCallback callback);
}

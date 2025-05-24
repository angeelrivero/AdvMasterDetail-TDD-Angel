package es.ulpgc.eite.da.advmasterdetail.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.database.CatalogDatabase;
import es.ulpgc.eite.da.advmasterdetail.database.MovieDao;

public class CatalogRepository implements RepositoryContract {

  public static final String TAG = CatalogRepository.class.getSimpleName();
  public static final String DB_FILE = "catalog.db";
  public static final String JSON_FILE = "catalog.json";
  public static final String JSON_ROOT = "movies";
  public static final String PREFS_NAME = "app_prefs";
  public static final String PREF_DB_INITIALIZED = "db_initialized";

  private static CatalogRepository INSTANCE;

  private final CatalogDatabase database;
  private final Context context;

  public static RepositoryContract getInstance(Context context) {
    if (INSTANCE == null) {
      INSTANCE = new CatalogRepository(context);
    }
    return INSTANCE;
  }

  private CatalogRepository(Context context) {
    this.context = context;

    // *** ¡¡USA SIEMPRE EL SINGLETON DEL MEDIATOR!! ***
    database = AppMediator.getInstance().getDatabase(context);

    initializeCatalogIfNeeded();
  }

  /**
   * Método para cargar películas solo la PRIMERA vez que se inicia la app.
   * Uso SharedPreferences para guardar un flag y asegurarme de no borrar la base de datos cada vez.
   */
  private void initializeCatalogIfNeeded() {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    boolean dbInitialized = prefs.getBoolean(PREF_DB_INITIALIZED, false);

    if (!dbInitialized) {
      // Solo la primera vez borro películas y cargo el JSON (usuarios no se tocan)
      Log.d(TAG, "Primera ejecución: cargo películas desde JSON.");
      loadCatalog(true, error -> {
        if (!error) {
          // Guardo el flag para no volver a cargar nunca más
          prefs.edit().putBoolean(PREF_DB_INITIALIZED, true).apply();
          Log.d(TAG, "Películas cargadas y base de datos marcada como inicializada.");
        } else {
          Log.e(TAG, "Error al cargar películas desde JSON en la primera ejecución.");
        }
      });
    }
  }

  // Carga inicial del catálogo desde JSON solo si la base de datos está vacía o si se indica borrar primero (clearFirst)
  @Override
  public void loadCatalog(final boolean clearFirst, final FetchCatalogDataCallback callback) {
    AsyncTask.execute(() -> {
      if (clearFirst) {
        // ¡OJO! Solo limpio la tabla de películas, NO la de usuarios (así no borro cuentas)
        database.movieDao().deleteAllMovies();
      }

      boolean error = false;

      if (getMovieDao().loadMovies().isEmpty()) {
        error = !loadMoviesFromJSON(loadJSONFromAsset());
      }

      if (callback != null) {
        callback.onCatalogDataFetched(error);
      }
    });
  }

  // Obtener la lista completa de películas
  public void getMovieList(final GetMovieListCallback callback) {
    AsyncTask.execute(() -> {
      List<MovieItem> movies = getMovieDao().loadMovies();
      Log.d(TAG, "Películas encontradas en base de datos: " + movies.size());

      if (callback != null) {
        callback.setMovieList(movies);
      }
    });
  }

  // Obtener una película por ID
  @Override
  public void getMovie(final int id, final GetMovieCallback callback) {
    AsyncTask.execute(() -> {
      if (callback != null) {
        callback.setMovie(getMovieDao().loadMovie(id));
      }
    });
  }

  // Eliminar una película
  @Override
  public void deleteMovie(final MovieItem movie, final DeleteMovieCallback callback) {
    AsyncTask.execute(() -> {
      getMovieDao().deleteMovie(movie);
      if (callback != null) callback.onMovieDeleted();
    });
  }

  // Actualizar una película
  @Override
  public void updateMovie(final MovieItem movie, final UpdateMovieCallback callback) {
    AsyncTask.execute(() -> {
      getMovieDao().updateMovie(movie);
      if (callback != null) callback.onMovieUpdated();
    });
  }

  // Acceso al DAO de películas
  private MovieDao getMovieDao() {
    return database.movieDao();
  }

  // Leer el archivo JSON desde assets
  private String loadJSONFromAsset() {
    String json = null;
    try {
      InputStream inputStream = context.getAssets().open(JSON_FILE);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder stringBuilder = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }

      reader.close();
      json = stringBuilder.toString();

    } catch (IOException error) {
      Log.e(TAG, "Error leyendo JSON: " + error);
    }

    return json;
  }

  // Parsear y guardar las películas del JSON en la base de datos
  private boolean loadMoviesFromJSON(String json) {
    Log.d(TAG, "Cargando películas desde JSON...");

    Gson gson = new GsonBuilder().create();

    try {
      JSONObject jsonObject = new JSONObject(json);
      JSONArray jsonArray = jsonObject.getJSONArray(JSON_ROOT);

      Log.d(TAG, "Películas en el JSON: " + jsonArray.length());

      if (jsonArray.length() > 0) {
        List<MovieItem> movies = Arrays.asList(
                gson.fromJson(jsonArray.toString(), MovieItem[].class)
        );

        Log.d(TAG, "Películas parseadas correctamente: " + movies.size());

        for (MovieItem movie : movies) {
          getMovieDao().insertMovie(movie);
          Log.d(TAG, "Insertada: " + movie.title);
        }

        return true;
      }

    } catch (JSONException e) {
      Log.e(TAG, "Error parseando JSON: " + e);
    }

    return false;
  }

  public CatalogDatabase getDatabase() {
    return database;
  }

  public AppMediator getMediator() {
    return AppMediator.getInstance();
  }

  @Override
  public MovieItem getMovieByIdSync(int movieId) {
    return database.movieDao().loadMovie(movieId);
  }

}

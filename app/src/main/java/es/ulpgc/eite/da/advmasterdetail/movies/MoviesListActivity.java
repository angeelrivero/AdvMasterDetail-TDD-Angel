package es.ulpgc.eite.da.advmasterdetail.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.app.AppMediator;
import es.ulpgc.eite.da.advmasterdetail.app.LoginToMovieListState;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.favorites.FavoritesActivity;
import es.ulpgc.eite.da.advmasterdetail.movie.MovieDetailActivity;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View {

  public static final String TAG = "MoviesListActivity";

  private MoviesListContract.Presenter presenter;
  private MoviesListAdapter listAdapter;
  private Button buttonFavorites;  // Botón Favoritos

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_list); // Nos aseguramos de tener este layout
    setTitle(R.string.title_movie_list); // Definimos este string en strings.xml

    MoviesListScreen.configure(this); // Inyección de dependencias MVP

    initMovieListContainer(); // Prepara RecyclerView

    // Vincula botón favoritos y configura estado
    buttonFavorites = findViewById(R.id.button_favorites);

    // Obtén el estado guardado que indica el tipo de login
    LoginToMovieListState loginState = AppMediator.getInstance().getLoginToMovieListState();

    if (loginState != null && loginState.loggedWithAccount) {
      // Usuario con cuenta: activa botón y añade listener para ir a favoritos
      buttonFavorites.setEnabled(true);
      buttonFavorites.setOnClickListener(v -> {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
      });
    } else {
      // Usuario sin cuenta: desactiva botón
      buttonFavorites.setEnabled(false);
    }

    if (savedInstanceState == null) {
      presenter.onCreateCalled();
    } else {
      presenter.onRecreateCalled();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.fetchMovieListData(); // Solicita datos al modelo
  }

  @Override
  protected void onPause() {
    super.onPause();
    presenter.onPauseCalled(); // Guarda estado
  }

  private void initMovieListContainer() {
    // Configura adaptador con callback para clics en elementos
    listAdapter = new MoviesListAdapter(view -> {
      MovieItem item = (MovieItem) view.getTag();
      presenter.selectedMovieData(item); // Informa al presenter del ítem clicado
    });

    RecyclerView recyclerView = findViewById(R.id.movie_recycler); // Asegúrate que existe en el layout
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(listAdapter);
  }

  @Override
  public void displayMovieListData(final MoviesListViewModel viewModel) {
    Log.d(TAG, "displayMovieListData() con " + viewModel.movies.size() + " películas");

    runOnUiThread(() -> {
      listAdapter.setItems(viewModel.movies);
    });
  }

  @Override
  public void navigateToMovieDetailScreen() {
    Intent intent = new Intent(this, MovieDetailActivity.class);
    startActivity(intent); // Navega al detalle
  }

  @Override
  public void injectPresenter(MoviesListContract.Presenter presenter) {
    this.presenter = presenter; // Inyección del Presenter
  }
}

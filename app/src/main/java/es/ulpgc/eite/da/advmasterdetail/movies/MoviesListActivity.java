package es.ulpgc.eite.da.advmasterdetail.movies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.movie.MovieDetailActivity;

public class MoviesListActivity extends AppCompatActivity implements MoviesListContract.View {

  public static final String TAG = "MoviesListActivity";

  private MoviesListContract.Presenter presenter;
  private MoviesListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_list); // Nos aseguramos de tener este layout
    setTitle(R.string.title_movie_list); // Definimos este string en strings.xml

    MoviesListScreen.configure(this); // Inyección de dependencias MVP

    initMovieListContainer(); // Prepara RecyclerView

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
    Log.d(TAG, "displayMovieListData()");
    runOnUiThread(() -> {
      listAdapter.setItems(viewModel.movies); // Muestra las películas en la lista
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

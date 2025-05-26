package es.ulpgc.eite.da.advmasterdetail.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.CatalogRepository;
import es.ulpgc.eite.da.advmasterdetail.data.FavoriteRepository;
import es.ulpgc.eite.da.advmasterdetail.movies.MoviesListAdapter;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;

import java.lang.ref.WeakReference;

public class FavoritesActivity extends AppCompatActivity implements FavoritesContract.View {

    private FavoritesContract.Presenter presenter;
    private MoviesListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito);
        setTitle(R.string.title_favorite_list); // Definimos este string en strings.xml


        recyclerView = findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MoviesListAdapter(view -> {
            presenter.selectedFavoriteMovieData((es.ulpgc.eite.da.advmasterdetail.data.MovieItem) view.getTag());
        });
        recyclerView.setAdapter(adapter);

        RepositoryContract catalogRepository = CatalogRepository.getInstance(getApplicationContext());
        FavoriteRepository favoriteRepository = new FavoriteRepository(catalogRepository.getDatabase().favoriteDao());

        FavoritesContract.Model model = new FavoritesModel(favoriteRepository, catalogRepository);
        FavoritesContract.Presenter pres = new FavoritesPresenter(catalogRepository.getMediator());

        pres.injectModel(model);
        pres.injectView(new WeakReference<>(this));
        injectPresenter(pres);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResumeCalled();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPauseCalled();
    }

    @Override
    public void displayFavoritesData(FavoritesViewModel viewModel) {
        if (viewModel.favorites == null || viewModel.favorites.isEmpty()) {
            Toast.makeText(this, "No tienes películas favoritas aún", Toast.LENGTH_SHORT).show();
            adapter.setItems(java.util.Collections.emptyList());
        } else {
            adapter.setItems(viewModel.favorites);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToMovieDetailScreen() {
        Intent intent = new Intent(this, es.ulpgc.eite.da.advmasterdetail.movie.MovieDetailActivity.class);
        startActivity(intent);
    }

    @Override
    public void injectPresenter(FavoritesContract.Presenter presenter) {
        this.presenter = presenter;
    }
}

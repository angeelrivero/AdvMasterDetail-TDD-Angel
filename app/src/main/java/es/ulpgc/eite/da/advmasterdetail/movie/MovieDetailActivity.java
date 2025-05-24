package es.ulpgc.eite.da.advmasterdetail.movie;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

  private MovieDetailContract.Presenter presenter;

  private TextView movieTitleTextView, movieDescriptionTextView, movieDirectorTextView, movieDurationTextView, movieActoresTextView;
  private ImageButton favoriteButton;
  private ImageView movieImageView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail);
    setTitle(R.string.title_movie_detail);

    MovieDetailScreen.configure(this);

    movieTitleTextView = findViewById(R.id.movie_title);
    movieDescriptionTextView = findViewById(R.id.movie_description);
    movieDirectorTextView = findViewById(R.id.movie_director);
    movieActoresTextView = findViewById(R.id.movie_actors);
    movieDurationTextView = findViewById(R.id.movie_duration);
    movieImageView = findViewById(R.id.movie_image);
    favoriteButton = findViewById(R.id.btn_favorite);

    // *** ACCESO A AppMediator PARA COMPROBAR SI ESTÁ LOGUEADO ***
    boolean canFavorite = es.ulpgc.eite.da.advmasterdetail.app.AppMediator.getInstance()
            .getLoginToMovieListState().loggedWithAccount;

    favoriteButton.setEnabled(canFavorite);  // Esto hace que el botón no sea pulsable
    favoriteButton.setAlpha(canFavorite ? 1.0f : 0.5f); // Visualmente más gris si está desactivado

    favoriteButton.setOnClickListener(v -> {
      android.util.Log.d("FavoriteTest", "Click en botón favorito");
      presenter.toggleFavorite();
    });

    if (savedInstanceState == null) {
      presenter.onCreateCalled();
    } else {
      presenter.onRecreateCalled();
    }
  }


  @Override
  protected void onResume() {
    super.onResume();
    presenter.fetchMovieDetailData();
  }

  @Override
  protected void onPause() {
    super.onPause();
    presenter.onPauseCalled();
  }

  @Override
  public void displayMovieDetailData(MovieDetailViewModel viewModel) {
    MovieItem movie = viewModel.movie;
    if (movie != null) {
      movieTitleTextView.setText("Título: " + movie.title);
      movieDescriptionTextView.setText("Sinopsis: " + movie.description);
      movieDirectorTextView.setText("Director: " + movie.director);
      movieActoresTextView.setText("Actores: " + movie.actors);
      movieDurationTextView.setText("Duración: " + movie.duration + " min");

      loadImageFromURL(movieImageView, movie.posterUrl);
    }

    // Log para ver qué valor está llegando realmente
    android.util.Log.d("FavoriteTest", "isFavorite: " + viewModel.isFavorite);

    // Cambia aquí el nombre del recurso si lo has renombrado
    favoriteButton.setImageResource(viewModel.isFavorite ? R.drawable.ic_star_llena : R.drawable.ic_star_border);
  }

  private void loadImageFromURL(ImageView imageView, String imageUrl){
    Glide.with(imageView.getContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView);
  }

  @Override
  public void injectPresenter(MovieDetailContract.Presenter presenter) {
    this.presenter = presenter;
  }
}


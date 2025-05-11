package es.ulpgc.eite.da.advmasterdetail.movie;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailContract.View {

  public static final String TAG = "MovieDetailActivity";

  private MovieDetailContract.Presenter presenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie_detail); // Asegúrate de tener este layout
    setTitle(R.string.title_movie_detail); // Agrega en strings.xml

    MovieDetailScreen.configure(this);

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
    Log.d(TAG, "displayMovieDetailData");

    MovieItem movie = viewModel.movie;
    if (movie != null) {
      ((TextView) findViewById(R.id.movie_title)).setText("Titulo: " + movie.title);
      ((TextView) findViewById(R.id.movie_description)).setText("Sinopsis: " +movie.description);
      ((TextView) findViewById(R.id.movie_director)).setText("Director: " + movie.director);
      ((TextView) findViewById(R.id.movie_duration)).setText("Duración: " + movie.duration + " min");

      loadImageFromURL((ImageView) findViewById(R.id.movie_image), movie.posterUrl);
    }
  }

  private void loadImageFromURL(ImageView imageView, String imageUrl){
    RequestManager reqManager = Glide.with(imageView.getContext());
    RequestBuilder reqBuilder = reqManager.load(imageUrl);
    RequestOptions reqOptions = new RequestOptions();
    reqOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
    reqBuilder.apply(reqOptions);
    reqBuilder.into(imageView);
  }

  @Override
  public void injectPresenter(MovieDetailContract.Presenter presenter) {
    this.presenter = presenter;
  }
}

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


public class MoviesListActivity
    extends AppCompatActivity implements MoviesListContract.View {

  public static String TAG = "AdvMasterDetail.MoviesListActivity";

  MoviesListContract.Presenter presenter;

  private MoviesListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_list);
    setTitle(R.string.title_product_list);

    // do the setup
    MoviesListScreen.configure(this);

    initProductListContainer();

    // do some work
    if(savedInstanceState == null) {
      presenter.onCreateCalled();

    }else{
      presenter.onRecreateCalled();
    }
  }


  @Override
  protected void onResume() {
    super.onResume();

    // do some work
    presenter.fetchProductListData();
  }

  @Override
  protected void onPause() {
    super.onPause();

    presenter.onPauseCalled();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  private void initProductListContainer() {

    listAdapter = new MoviesListAdapter(view -> {
      MovieItem item = (MovieItem) view.getTag();
      presenter.selectedProductData(item);
    });

    RecyclerView recyclerView = findViewById(R.id.product_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(listAdapter);
  }

  @Override
  public void navigateToProductDetailScreen() {
    Intent intent = new Intent(this, MovieDetailActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  public void displayProductListData(final MoviesListViewModel viewModel) {
    Log.e(TAG, "displayProductListData");

    runOnUiThread(() -> {

      // deal with the data
      listAdapter.setItems(viewModel.products);
    });

  }

  @Override
  public void injectPresenter(MoviesListContract.Presenter presenter) {
    this.presenter = presenter;
  }

}

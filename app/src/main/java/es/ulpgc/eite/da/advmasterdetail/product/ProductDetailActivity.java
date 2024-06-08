package es.ulpgc.eite.da.advmasterdetail.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;
import es.ulpgc.eite.da.advmasterdetail.products.ProductListActivity;


public class ProductDetailActivity
    extends AppCompatActivity implements ProductDetailContract.View {

  public static String TAG = "AdvMasterDetail.ProductDetailActivity";

  ProductDetailContract.Presenter presenter;

  //private ActionBar actionBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_detail);
    setTitle(R.string.title_product_detail);

    /*Toolbar toolbar = findViewById(R.id.detail_toolbar);
    setSupportActionBar(toolbar);

    actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }*/

    // do the setup
    ProductDetailScreen.configure(this);

    // do some work
    //presenter.fetchProductDetailData();

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
    presenter.fetchProductDetailData();
  }

  @Override
  public void displayProductDetailData(ProductDetailViewModel viewModel) {
    Log.e(TAG, "displayProductDetailData");

    // deal with the data
    ProductItem product = viewModel.product;

    if (product != null) {
      /*if (actionBar != null) {
        actionBar.setTitle(product.content);
      }*/

      ((TextView) findViewById(R.id.product_detail)).setText(product.details);
      loadImageFromURL(
          (ImageView) findViewById(R.id.product_image),
          product.picture
      );

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


//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    int id = item.getItemId();
//    if (id == android.R.id.home) {
//      navigateUpTo(new Intent(this, ProductListActivity.class));
//      return true;
//    }
//    return super.onOptionsItemSelected(item);
//  }

  @Override
  public void injectPresenter(ProductDetailContract.Presenter presenter) {
    this.presenter = presenter;
  }
}

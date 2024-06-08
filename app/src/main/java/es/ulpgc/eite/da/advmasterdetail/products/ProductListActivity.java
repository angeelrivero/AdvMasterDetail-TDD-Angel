package es.ulpgc.eite.da.advmasterdetail.products;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.ProductItem;
import es.ulpgc.eite.da.advmasterdetail.product.ProductDetailActivity;


public class ProductListActivity
    extends AppCompatActivity implements ProductListContract.View {

  public static String TAG = "AdvMasterDetail.ProductListActivity";

  ProductListContract.Presenter presenter;

  private ProductListAdapter listAdapter;
  //private ActionBar actionBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_product_list);
    setTitle(R.string.title_product_list);

    /*Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }*/

   /* listAdapter = new ProductListAdapter( new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        ProductItem item = (ProductItem) view.getTag();
        presenter.selectProductListData(item);
      }
    });

    RecyclerView recyclerView = findViewById(R.id.product_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(listAdapter);*/

    // do the setup
    ProductListScreen.configure(this);

    // do some work
    initProductListContainer();
    //presenter.fetchProductListData();

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

  private void initProductListContainer() {

    /*listAdapter = new ProductListAdapter( new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        ProductItem item = (ProductItem) view.getTag();
        presenter.selectProductListData(item);
      }
    });*/

    listAdapter = new ProductListAdapter(view -> {
      ProductItem item = (ProductItem) view.getTag();
      presenter.selectedProductData(item);
    });

    RecyclerView recyclerView = findViewById(R.id.product_recycler);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(listAdapter);
  }

  @Override
  public void navigateToProductDetailScreen() {
    Intent intent = new Intent(this, ProductDetailActivity.class);
    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  @Override
  public void displayProductListData(final ProductListViewModel viewModel) {
    Log.e(TAG, "displayProductListData");

    runOnUiThread(() -> {

      /*
      // deal with the data
      CategoryItem category = viewModel.category;
      if (actionBar != null) {
        actionBar.setTitle(category.content);
      }
      */

      // deal with the data
      listAdapter.setItems(viewModel.products);
    });

    /*runOnUiThread(new Runnable() {

      @Override
      public void run() {

        // deal with the data
        CategoryItem category = viewModel.category;
        *//*if (actionBar != null) {
          actionBar.setTitle(category.content);
        }*//*

        listAdapter.setItems(viewModel.products);
      }
    });*/
  }

//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    int id = item.getItemId();
//    if (id == android.R.id.home) {
//      NavUtils.navigateUpFromSameTask(this);
//      return true;
//    }
//    return super.onOptionsItemSelected(item);
//  }

  @Override
  public void injectPresenter(ProductListContract.Presenter presenter) {
    this.presenter = presenter;
  }

}

package es.ulpgc.eite.da.advmasterdetail.movies;

import android.util.Log;

import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


public class MoviesListModel implements MoviesListContract.Model {

  public static String TAG = "AdvMasterDetail.MoviesListModel";


  private RepositoryContract repository;

  public MoviesListModel(RepositoryContract repository){
    this.repository = repository;
  }

  @Override
  public void fetchProductListData(
          CategoryItem category, RepositoryContract.GetProductListCallback callback) {

    Log.e(TAG, "fetchProductListData()");
    repository.getProductList(category, callback);
  }

}

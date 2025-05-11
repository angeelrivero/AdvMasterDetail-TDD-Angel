package es.ulpgc.eite.da.advmasterdetail.movies;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.data.RepositoryContract;


interface MoviesListContract {

  interface View {
    void injectPresenter(Presenter presenter);

    void displayProductListData(MoviesListViewModel viewModel);

    void navigateToProductDetailScreen();
  }

  interface Presenter {
    void injectView(WeakReference<View> view);
    void injectModel(Model model);

    void fetchProductListData();
    void selectedProductData(MovieItem item);

    void onCreateCalled();

    void onRecreateCalled();

      void onPauseCalled();
  }

  interface Model {

    void fetchProductListData(
            CategoryItem category, RepositoryContract.GetProductListCallback callback);
  }

}
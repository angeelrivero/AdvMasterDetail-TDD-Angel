package es.ulpgc.eite.da.advmasterdetail.app;

import es.ulpgc.eite.da.advmasterdetail.categories.CategoryListState;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;
import es.ulpgc.eite.da.advmasterdetail.movie.MovieDetailState;
import es.ulpgc.eite.da.advmasterdetail.movies.MoviesListState;

public class CatalogMediator {

//  private CategoryListState categoryListState = new CategoryListState();
//  private MoviesListState moviesListState = new MoviesListState();
//  private MovieDetailState movieDetailState = new MovieDetailState();

  private CategoryListState categoryListState;
  private MoviesListState moviesListState;
  private MovieDetailState movieDetailState;
  private CategoryItem category;
  private MovieItem product;


  private static CatalogMediator INSTANCE;

  private CatalogMediator() {

  }

  public static void resetInstance() {
    INSTANCE = null;
  }


  public static CatalogMediator getInstance() {
    if(INSTANCE == null){
      INSTANCE = new CatalogMediator();
    }

    return INSTANCE;
  }


  public CategoryListState getCategoryListState() {
    return categoryListState;
  }

  public MovieDetailState getProductDetailState() {
    return movieDetailState;
  }

  public MoviesListState getProductListState() {
    return moviesListState;
  }

  public MovieItem getProduct() {
    MovieItem item = product;
    //product = null;
    return item;
  }


  public void setProduct(MovieItem item) {
    product = item;
  }

  public void setCategory(CategoryItem item) {
    category = item;
  }

  public CategoryItem getCategory() {
    CategoryItem item = category;
    //category = null;
    return item;
  }

  public void setCategoryListState(CategoryListState state) {
    categoryListState = state;
  }

  public void setProductListState(MoviesListState state) {
    moviesListState =state;

  }

  public void setProductDetailState(MovieDetailState state) {
    movieDetailState =state;
  }
}

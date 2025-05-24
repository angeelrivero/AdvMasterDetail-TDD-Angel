package es.ulpgc.eite.da.advmasterdetail.movies;

import java.util.List;
import java.util.Set;

import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;


// ViewModel que contiene los datos que se mostrarán en la vista (la lista de películas)
public class MoviesListViewModel {
  public List<MovieItem> movies;
  public Set<Integer> favoriteIds; // <- Añade este campo
}


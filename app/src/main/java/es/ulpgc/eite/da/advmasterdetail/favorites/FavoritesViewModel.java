package es.ulpgc.eite.da.advmasterdetail.favorites;

import java.util.List;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;

// ViewModel para mostrar la lista en la vista (RecyclerView)
public class FavoritesViewModel {
  public List<MovieItem> favorites;
  public String data; // Opcional para mensajes o estados simples
}

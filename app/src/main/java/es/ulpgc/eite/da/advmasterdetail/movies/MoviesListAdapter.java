package es.ulpgc.eite.da.advmasterdetail.movies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.MovieItem;


public class MoviesListAdapter
    extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

  private List<MovieItem> itemList;
  private final View.OnClickListener clickListener;


  public MoviesListAdapter(View.OnClickListener listener) {

    itemList = new ArrayList();
    clickListener = listener;
  }


  public void addItem(MovieItem item){
    itemList.add(item);
    notifyDataSetChanged();
  }

  public void addItems(List<MovieItem> items){
    itemList.addAll(items);
    notifyDataSetChanged();
  }

  public void setItems(List<MovieItem> items){
    itemList = items;
    notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_movie, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    MovieItem movie = itemList.get(position);

    holder.titleView.setText(movie.title);
    Glide.with(holder.itemView.getContext())
            .load(movie.posterUrl)
            .into(holder.imageView);

    holder.itemView.setTag(movie);
    holder.itemView.setOnClickListener(clickListener);
  }

  @Override
  public int getItemCount() {
    return itemList.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    final ImageView imageView;
    final TextView titleView;

    ViewHolder(View view) {
      super(view);
      imageView = view.findViewById(R.id.movie_image);
      titleView = view.findViewById(R.id.movie_title);
    }
  }
}

package es.ulpgc.eite.da.advmasterdetail.data;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@Entity(tableName = "categories")
public class CategoryItem {

  @PrimaryKey
  public int id;

  public String content;
  public String details;

  @Ignore
  @SerializedName("products")
  public List<MovieItem> items;

  @Override
  public String toString() {
    return content;
  }
}
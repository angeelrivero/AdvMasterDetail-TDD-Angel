package es.ulpgc.eite.da.advmasterdetail.data;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "movie")

public class MovieItem {
  @PrimaryKey
  public int id;

  public String title;
  public int year;
  public int duration;
  public String director;
  public String description;
  public String posterUrl;

  @Ignore // Le dices a Room que ignore esto
  public List<String> actors;
}

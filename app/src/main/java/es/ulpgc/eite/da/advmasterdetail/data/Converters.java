package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Clase para convertir los actores de la peli
public class Converters {
    @TypeConverter
    public static String fromList(List<String> list) {
        return list == null ? null : String.join(",", list);
    }

    @TypeConverter
    public static List<String> toList(String data) {
        return data == null || data.isEmpty() ? Collections.emptyList() : Arrays.asList(data.split(","));
    }
}

package customer.barcode.barcodewebx;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import customer.barcode.barcodewebx.RoomDatabase.mytable;

public class DataConvertor {



    @TypeConverter
    public static List<mytable> stringToSomeObjectList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<mytable>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<mytable> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}
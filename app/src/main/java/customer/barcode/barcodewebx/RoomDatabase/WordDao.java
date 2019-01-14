package customer.barcode.barcodewebx.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import customer.barcode.barcodewebx.productmodels.Product;

@Dao
public interface WordDao {

    @Insert
    void insert(mytable table);

    @Insert
    void inserthis(historytable table);



    @Query("DELETE FROM product")
    void deleteAll();

    @Query("DELETE FROM product WHERE ID = :id")
    abstract void deleterow(long id);

    @Query("UPDATE product SET pitemn = :value1 WHERE pbar = :bar")
    void updateproduct(long value1 ,long bar);

    @Insert
    void insertProductforlist(Productltable product);

    @Query("SELECT * FROM products_table WHERE barcode = :bar")
    Productltable findProduct(String bar);



    @Delete
    void deleteit(mytable model);

    @Query("SELECT * from product ")
    LiveData<List<mytable>> getAllWords();




    @Query("SELECT * from history ")
    LiveData<List<historytable>> getAllHis();

    @Query("DELETE FROM history")
    void deleteAllHis();


}
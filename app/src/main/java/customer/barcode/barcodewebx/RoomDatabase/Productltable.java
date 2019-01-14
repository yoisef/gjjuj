package customer.barcode.barcodewebx.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import customer.barcode.barcodewebx.productmodels.Category;
import customer.barcode.barcodewebx.productmodels.Company;
import customer.barcode.barcodewebx.productmodels.Image;
import customer.barcode.barcodewebx.productmodels.Unit;

@Entity(tableName = "products_table")
public class Productltable {


    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "barcode")
    private String barcode;

    @ColumnInfo(name = "description")
    private String description;



    @ColumnInfo(name = "imge")
    private String imge;



    public Productltable(String name, String price, String barcode,
                         String description, String imge



                         ) {

        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.description = description;
        this.imge=imge;


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getImge() {
        return imge;
    }

    public void setImge(String imge) {
        this.imge = imge;
    }





}
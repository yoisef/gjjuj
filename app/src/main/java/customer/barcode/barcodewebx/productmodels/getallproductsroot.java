package customer.barcode.barcodewebx.productmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import customer.barcode.barcodewebx.RoomDatabase.Productltable;


public class getallproductsroot {

    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
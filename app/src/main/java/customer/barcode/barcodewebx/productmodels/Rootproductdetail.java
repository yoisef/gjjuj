package customer.barcode.barcodewebx.productmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rootproductdetail {

    @SerializedName("product")
    @Expose
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
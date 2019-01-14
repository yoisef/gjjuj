package customer.barcode.barcodewebx.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

public class Sqlitetable {


    private int id;


    private String name;

    private String price;

    private String barcode;

    private String description;



    private String imge;



    public Sqlitetable(String name, String barcode, String price,
                       String imge, String description



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
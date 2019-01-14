package customer.barcode.barcodewebx;

import java.util.List;

import customer.barcode.barcodewebx.RoomDatabase.mytable;

public class list {

    private List<mytable> listdetails;

    public list(List<mytable> countryLangs) {
        this.listdetails = countryLangs;
    }

    public List<mytable> getListdetails() {
        return listdetails;
    }

    public void setListdetails(List<mytable> listdetails) {
        this.listdetails = listdetails;
    }



}
package customer.barcode.barcodewebx.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "product")
public class mytable {



    @PrimaryKey(autoGenerate = true)
    private int id;



    @ColumnInfo(name = "pname")
    private String pname;
    @ColumnInfo(name = "pbar")
    private String pbar;
    @ColumnInfo(name = "pitemn")
    private Integer pitemn;
    @ColumnInfo(name = "pimg")
    private String pimg;
    @ColumnInfo(name = "pdetail")
    private String pdetail;
    @ColumnInfo(name = "pprice")
    private String pprice;
    @ColumnInfo(name = "pcat")
    private String pcat;






    public mytable(String pname , String pbar, Integer pitemn, String pimg, String pdetail, String pprice, String pcat)
    {
        this.pbar=pbar;
        this.pcat=pcat;
        this.pitemn=pitemn;
        this.pdetail=pdetail;
        this.pimg=pimg;
        this.pprice=pprice;
        this.pname=pname;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPbar() {
        return pbar;
    }

    public void setPbar(String pbar) {
        this.pbar = pbar;
    }
    public Integer getPitemn() {
        return pitemn;
    }

    public void setPitemn(Integer pitemn) {
        this.pitemn = pitemn;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getPdetail() {
        return pdetail;
    }

    public void setPdetail(String pdetail) {
        this.pdetail = pdetail;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPcat() {
        return pcat;
    }

    public void setPcat(String pcat) {
        this.pcat = pcat;
    }


}
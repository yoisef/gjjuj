package customer.barcode.barcodewebx.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.List;


import customer.barcode.barcodewebx.DataConvertor;
import customer.barcode.barcodewebx.list;

@Entity(tableName = "history")
public class historytable {


    @PrimaryKey(autoGenerate = true)
    private int id;


    @ColumnInfo(name = "ornum")
    private Integer ornum;
    @ColumnInfo(name = "ordata")
    private String ordata;




    @TypeConverters(DataConvertor.class)
    public final List<mytable> orlist;



    @ColumnInfo(name = "oramount")
    private String oramount;
    @ColumnInfo(name = "orunits")
    private String orunits;

    public historytable(Integer ornum,String ordata,List<mytable> orlist,String oramount ,String orunits)
    {
        this.oramount=oramount;
        this.ordata=ordata;
        this.ornum=ornum;
        this.orlist=orlist;
        this.orunits=orunits;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getOrnum() {
        return ornum;
    }

    public void setOrnum(Integer ornum) {
        this.ornum = ornum;
    }

    public String getOrdata() {
        return ordata;
    }

    public void setOrdata(String ordata) {
        this.ordata = ordata;
    }

    public String getOramount() {
        return oramount;
    }

    public void setOramount(String oramount) {
        this.oramount = oramount;
    }

    public String getOrunits() {
        return orunits;
    }

    public void setOrunits(String orunits) {
        this.orunits = orunits;
    }
    public List<mytable> getOrlist() {
        return orlist;
    }




}
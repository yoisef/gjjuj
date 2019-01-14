package customer.barcode.barcodewebx;

public class productmodel {

  public   String namemyproduct;
   public String numbermyproduct;
   public String descriptionmyproduct;
   public String imgmyproduct;


    public   String colormyproduct;
  public   String categorymyproduct;
 public String priceproduct;

    public productmodel()
    {

    }

    public productmodel(String n,String nu,String de,String im ,String co ,String cate ,String pricee)
    {
        this.namemyproduct=n;
        this.numbermyproduct=nu;
        this.descriptionmyproduct=de;
        this.imgmyproduct=im;
        this.colormyproduct=co;
        this.categorymyproduct=cate;
        this.priceproduct=pricee;
    }
    public String getNamemyproduct() {
        return namemyproduct;
    }

    public String getNumbermyproduct() {
        return numbermyproduct;
    }

    public String getDescriptionmyproduct() {
        return descriptionmyproduct;
    }

    public String getImgmyproduct() {
        return imgmyproduct;
    }

    public String getColormyproduct() {
        return colormyproduct;
    }

    public String getCategorymyproduct() {
        return categorymyproduct;
    }

    public String getPriceproduct() {
        return priceproduct;
    }


}

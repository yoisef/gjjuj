package customer.barcode.barcodewebx;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Observable;

import customer.barcode.barcodewebx.modelsauth.Roottoken;

import customer.barcode.barcodewebx.productmodels.Rootproductdetail;
import customer.barcode.barcodewebx.productmodels.getallproductsroot;
import customer.barcode.barcodewebx.salemodel.Saleroot;
import customer.barcode.barcodewebx.usermodels.Userroot;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Endpoints {



    @POST("barcode")
    Call<Rootproductdetail> getdetails(@Header("Authorization")String token, @Query("barcode") String barcodeproduct);

    @FormUrlEncoded
    @POST("login")
    Call<Roottoken> signuser(@Header("Content-Type") String content, @Field("email")String email, @Field("password") String string);



    @GET("user")
    Call<Userroot> getuserdata(@Header("Authorization") String auth);

    @GET("product")
    Call<getallproductsroot> getproductdetails(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("retailersale")
    Call<Saleroot> getsalecondition(@Field("barcode") String barcode,@Field("quantity")int qua,@Field("retailer_id")String id,@Field("live") Boolean value);


    @Multipart
    @POST("image")
    Call<ResponseBody> uploadimg(@Part MultipartBody.Part img , @Part("type") RequestBody mtype);

    @FormUrlEncoded
    @POST("appstore")
    Call<ResponseBody> registerstore(@Field("latitude") String lat,@Field("longitude") String longtitude
            ,@Field("address") String addres  ,@Field("image_id") String imgid,@Field("active") boolean active
    ,@Field("city")String city,@Field("close_delivery") String Cdelivery
    ,@Field("close_time") String Ctime,@Field("currency") String currency,@Field("del_charge")String deletecharge,
    @Field("delivery_time") String Dtime,@Field("description")String desc,@Field("email") String email
    ,@Field("name") String nam,@Field("open_delivery") String Odelivery,@Field("open_time") String Otime
     ,@Field("phone") String phone,@Field("place_id")String placeid,@Field("website") String web);


    @FormUrlEncoded
    @POST("appretailer")
    Call<ResponseBody> registerretailer(@Field("store")String id,@Field("name")String adminname,@Field("email")String email
    ,@Field("password") String pass,@Field("mobile") String mob ,@Field("level") String level);


}

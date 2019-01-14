package customer.barcode.barcodewebx.Cashiery;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.List;

import customer.barcode.barcodewebx.Endpoints;
import customer.barcode.barcodewebx.R;
import customer.barcode.barcodewebx.Retrofitclient;
import customer.barcode.barcodewebx.RoomDatabase.Sqlitetable;
import customer.barcode.barcodewebx.RoomDatabase.mytable;
import customer.barcode.barcodewebx.RoomDatabase.productViewmodel;
import customer.barcode.barcodewebx.productdatabase;
import customer.barcode.barcodewebx.productmodels.Rootproductdetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Camera_activity extends AppCompatActivity {
    private BarcodeDetector detector;
    private SurfaceView cameraView;
    private customer.barcode.barcodewebx.CameraSource cameraSource;

    private MediaPlayer ring;
    LinearLayout changelay;

    private productViewmodel mWordViewModel;
    private Switch myswitch;
    String conditionn;
    private String usertoken;
    private SharedPreferences prefs;
    private Call<Rootproductdetail> mcall;
    private boolean add;
    private EditText itemsnum;
    private RelativeLayout layoutitems;
    private List<mytable> orderproducts;
    ImageView productimage,add_items,remove_item;
    TextView namee,unitpricee,barcodeee,total_itemscoast,donecamera,cancelcameraa;
    EditText showitems_number;
    private RelativeLayout detailproduct;
    private productdatabase mydatabase;
    private ProgressBar progresswaitadd;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_activity);



        productimage=findViewById(R.id.imgcam);
        add_items=findViewById(R.id.addcam);
        remove_item=findViewById(R.id.removecam);
        namee=findViewById(R.id.nampcam);
        unitpricee=findViewById(R.id.pricecam);
        barcodeee=findViewById(R.id.barcam);
        total_itemscoast=findViewById(R.id.totalcam);
        showitems_number=findViewById(R.id.itemnumbercam);

        donecamera=findViewById(R.id.donecam);
        cancelcameraa=findViewById(R.id.cancelcam);

       detailproduct=findViewById(R.id.showprodetail);
        progresswaitadd=findViewById(R.id.waitwhileadd);


        prefs = getSharedPreferences("token", Context.MODE_PRIVATE);
        usertoken = prefs.getString("usertoken", "def");
        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);

        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);
        mWordViewModel.getAllWords().observe(Camera_activity.this, new Observer<List<mytable>>() {
            @Override
            public void onChanged(@Nullable final List<mytable> words) {


                orderproducts=words;


            }
        });
        mydatabase=new productdatabase(this);






        ring = MediaPlayer.create(Camera_activity.this, R.raw.notif);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);



        changelay = findViewById(R.id.mydetect);



        myswitch = findViewById(R.id.switchtorch);
        if (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {


            myswitch.post(new Runnable() {
                @Override
                public void run() {

                    myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                            // do something, the isChecked will be
                            if (isChecked) {


                                conditionn = "true";
                                SharedPreferences prefs = getSharedPreferences("condition", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("mycon", conditionn);
                                editor.apply();
                                restart();

                            } else {

                                conditionn = "false";
                                SharedPreferences prefs = getSharedPreferences("condition", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("mycon", conditionn);
                                editor.apply();
                                restart();

                            }


                        }
                    });


                }
            });

        } else {
            myswitch.setVisibility(View.GONE);
        }






        donecamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                String barcode=sharedPreferences.getString("bar","11");





             //   progresswaitadd.setVisibility(View.VISIBLE);
               checkifproductexsist(showitems_number.getText().toString(),barcode);


                Handler mhandl=new Handler();
                mhandl.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        detailproduct.setVisibility(View.GONE);
                        progresswaitadd.setVisibility(View.GONE);
                        resumecamera();

                    }
                },2000);





            }
        });

        add_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(showitems_number.getText().toString());
                int currentnum=num+1;
                showitems_number.setText(String.valueOf(currentnum));
                Double Uprice=Double.parseDouble(unitpricee.getText().toString());

                Double totalpriceP=currentnum*Uprice;
                total_itemscoast.setText(String.valueOf(totalpriceP));
                //mWordViewModel.updateproduct(Long.parseLong(showitems_number.getText().toString()),Long.parseLong(current.getPbar()));
            }
        });

        remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num=Integer.parseInt(showitems_number.getText().toString());
                if (num <= 1) {
                    showitems_number.setText(String.valueOf(1));

                } else {
                    int cunum=num-1;
                    showitems_number.setText(String.valueOf(cunum));
                    Double Uprice=Double.parseDouble(unitpricee.getText().toString());
                    Double totalp=Uprice*cunum;
                    total_itemscoast.setText(String.valueOf(totalp));
                  //  mWordViewModel.updateproduct(Long.parseLong(showitems_number.getText().toString()),Long.parseLong(current.getPbar()));

                }
            }
        });

        cancelcameraa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailproduct.setVisibility(View.GONE);
                resumecamera();

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("condition", Context.MODE_PRIVATE);
        String conde = prefs.getString("mycon", "false");

        if (conde.equals("true")) {
            //myswitch.setOnCheckedChangeListener (null);
            myswitch.setChecked(true);
            instalcamerawithflash();
        } else {
            instalcamerawirhoutflash();
        }






         
    }


    public void instalcamerawithflash() {

        detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(0)
                        .build();
        if (!detector.isOperational()) {
            //  txtView.setText("Could not set up the detector!");
            Toast.makeText(Camera_activity.this, "Could not set up the detector!", Toast.LENGTH_LONG).show();

            return;
        }


        cameraSource = new customer.barcode.barcodewebx.CameraSource
                .Builder(Camera_activity.this, detector)
                .setRequestedPreviewSize(640, 480)
                .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                .setscanmode(Camera.Parameters.SCENE_MODE_BARCODE)
                .setFlashMode(Camera.Parameters.FLASH_MODE_TORCH)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {


                    if (ActivityCompat.checkSelfPermission(Camera_activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());


                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
                //  cameraSource.release();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();


                if (barcodes.size() != 0) {
                    changelay.post(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {
                                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("bar", barcodes.valueAt(0).displayValue);
                                editor.apply();
                                getproductdetails(barcodes.valueAt(0).displayValue);
                                ring.start();
                                cameraSource.stop();
                            }


                        }


                    });


                }
            }
        });


    }

    public void instalcamerawirhoutflash() {

        detector =
                new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(0)
                        .build();
        if (!detector.isOperational()) {
            //  txtView.setText("Could not set up the detector!");
            Toast.makeText(Camera_activity.this, "Could not set up the detector!", Toast.LENGTH_LONG).show();

            return;
        }


        cameraSource = new customer.barcode.barcodewebx.CameraSource
                .Builder(Camera_activity.this, detector)
                .setRequestedPreviewSize(640, 480)
                .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                .setscanmode(Camera.Parameters.SCENE_MODE_BARCODE)
                .build();


        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {


                    if (ActivityCompat.checkSelfPermission(Camera_activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());

                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
                //         cameraSource.release();
            }
        });


        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();


                if (barcodes.size() != 0) {
                    changelay.post(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (this) {
                                SharedPreferences sharedPreferences = getSharedPreferences("productbar", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("bar", barcodes.valueAt(0).displayValue);
                                editor.apply();
                                getproductdetails(barcodes.valueAt(0).displayValue);
                                ring.start();
                                cameraSource.stop();
                            }


                        }
                    });


                }


            }


        });


    }


    private void loginwithbarcode(final String barcodedata, final int itemsnum) {


        Retrofitclient myretro = Retrofitclient.getInstance();
        Retrofit retrofitt = myretro.getretro();


        final Endpoints myendpoints = retrofitt.create(Endpoints.class);

        mcall = myendpoints.getdetails("Bearer " + usertoken, barcodedata);
        mcall.enqueue(new Callback<Rootproductdetail>() {
            @Override
            public void onResponse(Call<Rootproductdetail> call, Response<Rootproductdetail> response) {

                if (response.isSuccessful()) {
                    mytable MainTable;




                    if (response.body().getProduct() != null) {

                        final String pronam, prodbar, prodimg, broddetail, brodprice, prodcat;
                        pronam = response.body().getProduct().getName();
                        prodbar = response.body().getProduct().getBarcode();
                        prodimg = response.body().getProduct().getImage().getUrl();
                        broddetail = response.body().getProduct().getDescription();
                        brodprice = response.body().getProduct().getPrice();
                        prodcat = response.body().getProduct().getCategory().getName();
                        MainTable = new mytable(pronam, prodbar, itemsnum, prodimg, broddetail, brodprice, prodcat);
                        mWordViewModel.insert(MainTable);





                    } else {
                        Toast.makeText(Camera_activity.this, "" + getResources().getString(R.string.notrecorded), Toast.LENGTH_LONG).show();
                        resumecamera();

                    }
                }
                else {
                    Toast.makeText(Camera_activity.this,""+getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                    resumecamera();


                }


            }

            @Override
            public void onFailure(Call<Rootproductdetail> call, Throwable t) {

                Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcodedata);

                if (mytable!=null)
                {
                    mWordViewModel.insert(new mytable(mytable.getName(),mytable.getBarcode(),Integer.parseInt(showitems_number.getText().toString()),mytable.getImge(),mytable.getDescription(),mytable.getPrice(),null));

                }
                else
                {
                    Toast.makeText(Camera_activity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                    resumecamera();
                }

                        }

                });


    }

            

     private void getproductdetails(final String barcodedata)
     {
         Retrofitclient myretro = Retrofitclient.getInstance();
         Retrofit retrofitt = myretro.getretro();


         final Endpoints myendpoints = retrofitt.create(Endpoints.class);

         mcall = myendpoints.getdetails("Bearer " + usertoken, barcodedata);
       mcall.enqueue(new Callback<Rootproductdetail>() {
           @Override
           public void onResponse(Call<Rootproductdetail> call, Response<Rootproductdetail> response) {



               if(response.isSuccessful())
               {

                   if (response.body().getProduct()!=null)
                   {
                       final String pronam, prodbar, prodimg, broddetail, brodprice, prodcat;
                       pronam = response.body().getProduct().getName();
                       prodbar = response.body().getProduct().getBarcode();
                       prodimg = response.body().getProduct().getImage().getUrl();
                       broddetail = response.body().getProduct().getDescription();
                       brodprice = response.body().getProduct().getPrice();
                       prodcat = response.body().getProduct().getCategory().getName();

                       Glide.with(Camera_activity.this)
                               .load(prodimg)
                               .into(productimage);
                       namee.setText(pronam);
                       barcodeee.setText(prodbar);
                       unitpricee.setText(brodprice);
                       int curnumunit=Integer.parseInt(showitems_number.getText().toString());
                       final Double priceunit=Double.parseDouble(brodprice);
                       total_itemscoast.setText(String .valueOf(priceunit*curnumunit));
                       detailproduct.setVisibility(View.VISIBLE);
                      showitems_number.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                           @Override
                           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                               if (actionId== EditorInfo.IME_ACTION_DONE)
                               {
                                 Double val=  Integer.parseInt(showitems_number.getText().toString())*priceunit;
                                   total_itemscoast.setText(String.valueOf(val));
                                   return true;
                               }
                               return false;
                           }
                       });
                   }
                   else
                   {
                       Toast.makeText(Camera_activity.this,""+getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                       resumecamera();
                   }

               }
               else
               {
                   Toast.makeText(Camera_activity.this,""+getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                   resumecamera();

               }
           }

           @Override
           public void onFailure(Call<Rootproductdetail> call, Throwable t) {

               Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcodedata);
               String name,pricee,imagee;
               if(mytable!=null)
               {
                   imagee=  mytable.getImge();
                   pricee= mytable.getPrice();
                   name=   mytable.getName();

                   Glide.with(Camera_activity.this)
                           .load(imagee)
                           .into(productimage);
                   namee.setText(name);
                   barcodeee.setText(barcodedata);
                   unitpricee.setText(pricee);
                   int curnumunit=Integer.parseInt(showitems_number.getText().toString());
                   Double priceunit=Double.parseDouble(pricee);
                   total_itemscoast.setText(String .valueOf(priceunit*curnumunit));
                   detailproduct.setVisibility(View.VISIBLE);
               }
               else
               {
                   Toast.makeText(Camera_activity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                   resumecamera();
               }




           }
       });
     }

    private void resumecamera() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            cameraSource.start(cameraView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void checkifproductexsist(String numitems,String barcod)
    {
        boolean mycondition=true;


        // Update the cached copy of the words in the adapter.
        int i;
        if (orderproducts.size() != 0) {


            for (i = 0; i < orderproducts.size(); i++) {
                if (barcod.trim().equals(orderproducts.get(i).getPbar().trim())) {


                    mytable current = orderproducts.get(i);
                    // int totalitems = current.getPitemn() + Integer.parseInt(numitems);

                    mWordViewModel.updateproduct(current.getPitemn()+Integer.parseInt(numitems),Long.parseLong(barcod));
                    mycondition=false;
                }

            }
            if (mycondition)
            {
                loginwithbarcode(barcod,Integer.parseInt(numitems));



            }



        }
        else {
            loginwithbarcode(barcod,Integer.parseInt(showitems_number.getText().toString()));

        }

    }


    public void restart ()
    {
        this.recreate();
    }



    @Override
    protected void onDestroy () {
        super.onDestroy();
                // for ActivityCompat#requestPermissions for more details.
                cameraSource.stop();
                cameraSource.release();



    }
}















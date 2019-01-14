package customer.barcode.barcodewebx.Cashiery;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import customer.barcode.barcodewebx.Endpoints;
import customer.barcode.barcodewebx.R;
import customer.barcode.barcodewebx.Retrofitclient;
import customer.barcode.barcodewebx.RoomDatabase.Sqlitetable;
import customer.barcode.barcodewebx.RoomDatabase.historytable;
import customer.barcode.barcodewebx.RoomDatabase.mytable;
import customer.barcode.barcodewebx.RoomDatabase.productViewmodel;

import customer.barcode.barcodewebx.productdatabase;
import customer.barcode.barcodewebx.productmodels.Rootproductdetail;
import customer.barcode.barcodewebx.productmodels.getallproductsroot;
import customer.barcode.barcodewebx.salemodel.Saleroot;
import customer.barcode.barcodewebx.usermodels.Userroot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    private RecyclerView myrecycle;
    private ImageView scan, barcodimg;
    private Toolbar mytoolbar;
    private Button enterbarcode;
    private android.app.AlertDialog.Builder builder, builder1;
    private android.app.AlertDialog alertDialog, alertDialog1;
    private Call<Rootproductdetail> mcall;
    private Call<Saleroot> salecall;
    private Call<Userroot> usercall;
    private Call<getallproductsroot> callproducts;
    private Recycleadapter mAdapter;
    private TextView pricetotal;
    private TextView paylinear;
    private productViewmodel mWordViewModel;
    private SharedPreferences prefs,tablepref;
    private SharedPreferences.Editor myeditor,tableeditor;
    private String usertoken;
    private ProgressBar payprpgressbarr;
    private List<mytable> myproducts;
    private productdatabase mydatabase;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mytoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menumain);
        getSupportActionBar().setDisplayShowTitleEnabled(false);







        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight

                        switch (menuItem.getItemId()) {
                            case R.id.signout: {
                                SharedPreferences prefs = getSharedPreferences("token", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.clear().apply();
                                startActivity(new Intent(MainActivity.this, loginactivity.class));
                                finish();
                                break;
                            }

                            case R.id.sales:{
                                mDrawerLayout.closeDrawers();

                                startActivity(new Intent(MainActivity.this,sales_history.class));

                                break;
                            }

                            case R.id.readbar:{
                                mDrawerLayout.closeDrawers();

                                startActivity(new Intent(MainActivity.this,Camera_activity.class));

                                break;
                            }
                            case R.id.appknow:{

                                break;
                            }

                            case R.id.about:{

                                break;
                            }

                            case R.id.enter_bar:{
                                mDrawerLayout.closeDrawers();

                                enterbar_operation();
                                break;

                            }


                            default:
                                menuItem.setChecked(true);
                                // close drawer when item is tapped
                                mDrawerLayout.closeDrawers();
                                break;
                        }
                        return true;


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here


                    }
                });





        mydatabase=new productdatabase(this);
      //  mydatabase.getallproducts(this);

      tablepref=getSharedPreferences("tablep",Context.MODE_PRIVATE);
        tableeditor=tablepref.edit();



        myproducts=new ArrayList<>();

        prefs = getSharedPreferences("token", Context.MODE_PRIVATE);
     usertoken=prefs.getString("usertoken","def");
        getretailerid();
       getallproducts();



        // request camera permission

        // intilize Ui objects
        pricetotal = findViewById(R.id.totalpricec);
        paylinear = findViewById(R.id.paylayout);
     //   enterbarcode = findViewById(R.id.barcodenumber);

        myrecycle = findViewById(R.id.productrecycle);
        myrecycle.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        myrecycle.setLayoutManager(linearLayoutManager);
        myrecycle.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new Recycleadapter(this);
        myrecycle.setAdapter(mAdapter);


        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<List<mytable>>() {
            @Override
            public void onChanged(@Nullable final List<mytable> words) {

                mAdapter.setWords(words);
                myproducts=words;
                Double allcoast=0.0;

                // Update the cached copy of the words in the adapter.

                for(int i=0;i<words.size();i++)
                {

                   Double unitprice=Double.parseDouble(words.get(i).getPprice());
                   int quantity=words.get(i).getPitemn();
                   Double productcoast=unitprice*quantity;
                   allcoast=allcoast+productcoast;

                }
                pricetotal.setText(String.valueOf(allcoast));

            }



        });







        //  getSupportActionBar().setCustomView(R.layout.cutom_action_bar);




        barcodimg = findViewById(R.id.aboutus);

        barcodimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Camera_activity.class));
            }
        });






        paylinear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView okk, canceel;

                builder1 = new android.app.AlertDialog.Builder(MainActivity.this);

                View myview = LayoutInflater.from(MainActivity.this.getApplicationContext()).inflate(R.layout.payconfirmation, null);
                okk = myview.findViewById(R.id.okkpay);
                canceel = myview.findViewById(R.id.cancellpay);
                builder1.setView(myview);
                alertDialog1 = builder1.create();
                alertDialog1.show();
                okk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int totalorderitems = 0;
                        Double totalordercoast= 0.0;
                        int i;


                        for (i = 0; i < myproducts.size(); i++) {

                            String currentproduct = myproducts.get(i).getPbar();
                            Double currentcoast = Double.parseDouble(myproducts.get(i).getPprice());
                            int items = myproducts.get(i).getPitemn();
                            totalorderitems = totalorderitems + items;
                            Double totalproduct=currentcoast*items;
                            totalordercoast = totalordercoast + totalproduct;

                            if (myproducts.size() == 0) {

                            } else {
                                makesaleorder(currentproduct,items);
                            }


                        }

                        Locale locale = new Locale("ar", "KW");
                        SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MMMM-yy");
                        Date currDate = new Date();


                        String formattedDate = sdf.format(currDate);





                        historytable myhis = new historytable(1,formattedDate,myproducts, String.valueOf(totalordercoast), String.valueOf(totalorderitems));
                        mWordViewModel.inserthis(myhis);

                        alertDialog1.cancel();

                    }
                });
                canceel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.cancel();
                    }
                });

            }
        });










    }













    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void loginwithenternumber(final String barcode) {


        Retrofitclient myretro=Retrofitclient.getInstance();
        Retrofit retrofittok=  myretro.getretro();
        final Endpoints myendpoints = retrofittok.create(Endpoints.class);

        mcall = myendpoints.getdetails("Bearer "+usertoken,barcode);
        mcall.enqueue(new Callback<Rootproductdetail>() {
            @Override
            public void onResponse(Call<Rootproductdetail> call, Response<Rootproductdetail> response) {

                if (response.isSuccessful()) {

                    if (response.body().getProduct()!=null) {

                        String pronam, prodbar, prodimg, broddetail, brodprice, prodcat;
                        pronam = response.body().getProduct().getName();
                        prodbar = response.body().getProduct().getBarcode();
                        prodimg = response.body().getProduct().getImage().getUrl();
                        broddetail = response.body().getProduct().getDescription();
                        brodprice = response.body().getProduct().getPrice();

                        prodcat = response.body().getProduct().getCategory().getName();
                        mytable word = new mytable(pronam, prodbar,Integer.parseInt("1"), prodimg, broddetail, brodprice, prodcat);
                        mWordViewModel.insert(word);
                        // myrecycle.scrollToPosition(myrecycle.getAdapter().getItemCount() - 1);


                    }
                    else
                    {
                       Toast.makeText(MainActivity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
                    }

                } else {


                }


            }

            @Override
            public void onFailure(Call<Rootproductdetail> call, Throwable t) {



            Sqlitetable mytable= mydatabase.getdataforrowinproduct(barcode);

            if (mytable!=null)
            {
                mWordViewModel.insert(new mytable(mytable.getName(),mytable.getBarcode(),Integer.parseInt("1"),mytable.getImge(),mytable.getDescription(),mytable.getPrice(),null));

            }
            else
            {
              Toast.makeText(MainActivity.this,getResources().getString(R.string.notrecorded),Toast.LENGTH_LONG).show();
            }










            }
        });





    }


    private void makesaleorder(final String barcodedata,int itemsnumber)
    {
       String retailerid= getSharedPreferences("retailerid",Context.MODE_PRIVATE).getString("id","n");
        Retrofitclient myretro=Retrofitclient.getInstance();
      Retrofit retrofit=  myretro.getretro();

        final Endpoints myendpoints = retrofit.create(Endpoints.class);

        salecall = myendpoints.getsalecondition(barcodedata, itemsnumber,retailerid,true);
        salecall.enqueue(new Callback<Saleroot>() {
            @Override
            public void onResponse(Call<Saleroot> call, Response<Saleroot> response) {

                if (response.isSuccessful())
                {


                    if(response.body()!=null)
                    {
                        mWordViewModel.deleteallproduct();
                        Toast.makeText(MainActivity.this,"Successful Payment ",Toast.LENGTH_LONG).show();



                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Something Wrong",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Saleroot> call, Throwable t) {

                Toast.makeText(MainActivity.this,"Connection Failed",Toast.LENGTH_LONG).show();

            }
        });



    }


    private void checkifproductexsist(String barcod)
    {
        boolean mycondition=true;


        // Update the cached copy of the words in the adapter.
        int i;
        if (myproducts.size() != 0) {


            for (i = 0; i < myproducts.size(); i++) {
                if (barcod.trim().equals(myproducts.get(i).getPbar().trim())) {


                    mytable current = myproducts.get(i);
                  // int totalitems = current.getPitemn() + Integer.parseInt(numitems);

                    mWordViewModel.updateproduct(current.getPitemn()+1,Long.parseLong(barcod));
                    mycondition=false;
                }

            }
            if (mycondition)
            {
               loginwithenternumber(barcod);


            }



        }
        else{
            loginwithenternumber(barcod);
        }

    }

    private void getretailerid()
    {
        Retrofitclient myretro=Retrofitclient.getInstance();
        Retrofit retrofittok=  myretro.getretro();
        final Endpoints myendpoints = retrofittok.create(Endpoints.class);
        usercall=myendpoints.getuserdata("Bearer "+usertoken);
        usercall.enqueue(new Callback<Userroot>() {
            @Override
            public void onResponse(Call<Userroot> call, Response<Userroot> response) {

                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        String retailerid=response.body().getAuth().getId();
                        SharedPreferences preferences=getSharedPreferences("retailerid",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("id",retailerid);
                        editor.apply();
                        Toast.makeText(MainActivity.this,"id="+retailerid,Toast.LENGTH_LONG).show();

                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this,"problem with authentication",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<Userroot> call, Throwable t) {


            }
        });
    }

    public void getallproducts()
    {

       // String usertoken = getSharedPreferences("token", Context.MODE_PRIVATE).getString("usertoken","def");


        Retrofitclient myretro=Retrofitclient.getInstance();
        Retrofit retrofittok=  myretro.getretro();
        final Endpoints myendpoints = retrofittok.create(Endpoints.class);

        callproducts=myendpoints.getproductdetails("Bearer "+usertoken);
        callproducts.enqueue(new Callback<getallproductsroot>() {
            @Override
            public void onResponse(Call<getallproductsroot> call, Response<getallproductsroot> response) {

                if (response.isSuccessful())

                {


                    int sizee=response.body().getProducts().size();

                    try {
                        for (int i=0;i<sizee;i++)
                        {

                            String barcode= response.body().getProducts().get(i).getBarcode();
                            String img=response.body().getProducts().get(i).getImage().getUrl();
                            String price=response.body().getProducts().get(i).getPrice();
                            String name=response.body().getProducts().get(i).getName();
                            String desc=response.body().getProducts().get(i).getDescription();
                            mydatabase.insertdatalistproducts(name,barcode,price,img,desc);


                        }

                } catch (Exception e) {
                    Log.w("Exception:", e);
                } finally {
                }





                }
                else
                {
                    Toast.makeText(MainActivity.this,"null",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<getallproductsroot> call, Throwable t) {

                Toast.makeText(MainActivity.this,"failed",Toast.LENGTH_LONG).show();


            }
        });

    }

    public void enterbar_operation()
    {
        final EditText myedit,numedit;
        TextView ok, cancel;
        ImageView incre,decre;


        builder = new android.app.AlertDialog.Builder(MainActivity.this);

        View myview = LayoutInflater.from(MainActivity.this.getApplicationContext()).inflate(R.layout.layoutenterbar, null);

        myedit = myview.findViewById(R.id.barcodedittext);
        ok = myview.findViewById(R.id.okk);
        cancel = myview.findViewById(R.id.cancell);
        builder.setView(myview);
        alertDialog = builder.create();
        alertDialog.show();



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String barcodee=myedit.getText().toString();



               checkifproductexsist(barcodee);








                alertDialog.cancel();
            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();

            }
        });

    }

        }

































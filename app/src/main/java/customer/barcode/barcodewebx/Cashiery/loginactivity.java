package customer.barcode.barcodewebx.Cashiery;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.hbb20.CountryCodePicker;

import customer.barcode.barcodewebx.AboutApp;
import customer.barcode.barcodewebx.Endpoints;
import customer.barcode.barcodewebx.R;
import customer.barcode.barcodewebx.modelsauth.Roottoken;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginactivity extends AppCompatActivity {

    private TextView signintext,sign_up;
    private Call<Roottoken> mcall;
    private EditText log_phone,log_pass;
    private ProgressBar signprogress;
    private CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        requestPermission();

        ccp = findViewById(R.id.logcodepicker);
       log_phone=findViewById(R.id.logphone);
        log_pass=findViewById(R.id.logpass);
        signintext=findViewById(R.id.signinbutton);
        signprogress=findViewById(R.id.loginprogressbar);
        sign_up=findViewById(R.id.retailer_new);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this,Choose_Store.class));
            }
        });



        signintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               boolean result= validesignin();
               if (result)
               {
                   signprogress.setVisibility(View.VISIBLE);
               }



            }
        });
    }


  private boolean validesignin()
  {

      String code=ccp.getSelectedCountryCodeWithPlus();


      String retailerphone,retailerpass;
      retailerphone=log_phone.getText().toString().trim();
      StringBuilder phone=new StringBuilder(retailerphone);
      if (!phone.toString().equals(""))
      {
          Character charSequence=phone.charAt(0);
          if(charSequence=='0')
          {
              phone.deleteCharAt(0);
          }
      }

      String phoneresult=phone.toString();

      String full_number=code+phoneresult;
      retailerpass=log_pass.getText().toString().trim();

      if (retailerphone.isEmpty())
      {
          log_phone.setError(getResources().getString(R.string.emailV));
          log_phone.requestFocus();
          return false;
      }


     if(retailerpass.isEmpty())
      {
          log_pass.setError(getResources().getString(R.string.passV));
          log_pass.requestFocus();
          return false;
      }




      signin(full_number,retailerpass);

      return true;
  }

    public void signin(String email ,String password)
    {

        OkHttpClient.Builder builderr = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builderr.addInterceptor(loggingInterceptor);


        Retrofit retrofitt = new Retrofit.Builder()
                .baseUrl("https://www.werpx.net/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(builderr.build())
                .build();

        final Endpoints myendpoints = retrofitt.create(Endpoints.class);

        mcall = myendpoints.signuser("application/x-www-form-urlencoded",email,password);
        mcall.enqueue(new Callback<Roottoken>() {
            @Override
            public void onResponse(Call<Roottoken> call, Response<Roottoken> response) {
                if (response.isSuccessful())
                {
                    signprogress.setVisibility(View.GONE);
                   String thetoken= response.body().getData().getToken();
                    SharedPreferences prefs = getSharedPreferences("token", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putString("usertoken",thetoken);
                    editor.apply();
                    Toast.makeText(loginactivity.this,"Successful login",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(loginactivity.this,MainActivity.class));
                    finish();
                }
                else
                {
                    signprogress.setVisibility(View.GONE);
                    Toast.makeText(loginactivity.this,"Wrong email or password",Toast.LENGTH_LONG).show();



                }
            }

            @Override
            public void onFailure(Call<Roottoken> call, Throwable t) {
                signprogress.setVisibility(View.GONE);
                Toast.makeText(loginactivity.this,"Connection Failed",Toast.LENGTH_LONG).show();


            }
        });


    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();

                    // main logic
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            showMessageOKCancel("You need to allow access permissions",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermission();
                                            }
                                        }
                                    });
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(loginactivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}

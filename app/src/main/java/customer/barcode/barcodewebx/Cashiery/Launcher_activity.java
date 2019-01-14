package customer.barcode.barcodewebx.Cashiery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import customer.barcode.barcodewebx.R;
import customer.barcode.barcodewebx.RoomDatabase.productViewmodel;

public class Launcher_activity extends AppCompatActivity {


    private productViewmodel mWordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_activity);

        mWordViewModel = ViewModelProviders.of(this).get(productViewmodel.class);




        Handler myhandler=new Handler();
        myhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences("token", Context.MODE_PRIVATE);
                String condition= prefs.getString("usertoken","def");




                choosebeginingactivity(condition);
                finish();

            }
        },5000);
    }




    public void choosebeginingactivity(String condition)
    {



       if (condition.equals("def"))
       {
           startActivity(new Intent(Launcher_activity.this,loginactivity.class));
       }
       else
       {
           startActivity(new Intent(Launcher_activity.this,MainActivity.class));
       }

    }
}

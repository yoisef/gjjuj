package customer.barcode.barcodewebx.Cashiery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import customer.barcode.barcodewebx.MapsActivity;
import customer.barcode.barcodewebx.R;
import customer.barcode.barcodewebx.Storeinfo;

public class Choose_Store extends AppCompatActivity {


    TextView addstore,havesto;
   LinearLayout enterstoreid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);


        havesto=findViewById(R.id.havestore);
        addstore=findViewById(R.id.newstoree);


        havesto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent=new Intent(Choose_Store.this, MapsActivity.class);
                myintent.putExtra("havestore",1);

                startActivity(myintent);

            }
        });

        addstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Choose_Store.this,Storeinfo.class));
            }
        });
    }
}

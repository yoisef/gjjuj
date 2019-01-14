package customer.barcode.barcodewebx;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private TextView renam,remobile,storetxtvalidation;
    private EditText retemail,retapass,retailer_retype_pass;
    private Button register;
    private SearchView Search_Store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        storetxtvalidation=findViewById(R.id.storeidtext);
        renam=findViewById(R.id.Name_retailer);
        remobile=findViewById(R.id.Phone_retailer);
        register=findViewById(R.id.registerr);
        retapass=findViewById(R.id.Pass_retailer);
        retailer_retype_pass=findViewById(R.id.repass_retailer);

        Search_Store=findViewById(R.id.searchstore);

        renam.requestFocus();

        Search_Store.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                storetxtvalidation.setError(null);
                return false;
            }
        });





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validteretailer();

            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void validteretailer()
    {
        String storid,nam,mob,pas1,pas2;

        storid=Search_Store.getQuery().toString().trim();
        nam=renam.getText().toString().trim();
        pas1=retapass.getText().toString().trim();
        pas2=retailer_retype_pass.getText().toString().trim();
        mob=remobile.getText().toString().trim();


        if (storid.isEmpty())
        {
            storetxtvalidation.setError(getResources().getString(R.string.storeidV));
            storetxtvalidation.requestFocus();
            return;
        }
        if (nam.isEmpty())
        {
            renam.setError(getResources().getString(R.string.namV));
            renam.requestFocus();
            return;
        }
        if (mob.isEmpty())
        {
            remobile.setError(getResources().getString(R.string.mobileV));
            remobile.requestFocus();
            return;
        }


        if (pas1.isEmpty())
        {
            retapass.setError(getResources().getString(R.string.passV));
            retapass.requestFocus();

            return;
        }
        if (pas1.length()<6)
        {
            retapass.setError(getResources().getString(R.string.minumV));
            retapass.requestFocus();
            return;
        }
        if (pas2.isEmpty())
        {
            retailer_retype_pass.setError(getResources().getString(R.string.passRV));
            retailer_retype_pass.requestFocus();

            return;
        }
        if (!pas2.equals(pas1))
        {
            retailer_retype_pass.setError(getResources().getString(R.string.passRV));
            retailer_retype_pass.requestFocus();
            return;
        }


    }

}

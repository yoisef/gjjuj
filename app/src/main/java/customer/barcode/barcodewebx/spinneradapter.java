package customer.barcode.barcodewebx;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class spinneradapter extends ArrayAdapter<CharSequence> {


    Context con;
    List<CharSequence> levels;

    public spinneradapter(@NonNull Context context, int resource, @NonNull List<CharSequence> objects) {
        super(context, resource, objects);

        this.con=context;
        this.levels=objects;

    }







    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        } else {
            return true;
        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row=convertView;
        if (row==null)
        {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           TextView text= (TextView) inflater.inflate(android.R.layout.simple_spinner_item,parent,false);

           text.setText(levels.get(position));

        }




      return super.getView(position,convertView,parent);

    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }



}

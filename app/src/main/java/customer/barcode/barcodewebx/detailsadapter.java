package customer.barcode.barcodewebx;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import customer.barcode.barcodewebx.RoomDatabase.mytable;

public class detailsadapter extends RecyclerView.Adapter<detailsadapter.viewholder> {

    public List<mytable> details;
    public Context context;

    public detailsadapter(List<mytable> listdetails,Context con)
    {
        this.details=listdetails;
        this.context=con;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myview= LayoutInflater.from(context).inflate(R.layout.rowrecycledetails,parent,false);
        viewholder viewholder=new viewholder(myview);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        holder.namee.setText(details.get(position).getPname());
        holder.unitprice.setText(details.get(position).getPprice());
        holder.itemsnumber.setText(String.valueOf(details.get(position).getPitemn()));
        holder.barcodeee.setText(details.get(position).getPbar());

        Double total=Double.parseDouble(details.get(position).getPprice())*details.get(position).getPitemn();
        holder.total_itemscoast.setText(String.valueOf(total));

        GlideApp.with(context)
                .load(details.get(position).getPimg())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productimage);

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        ImageView productimage;

        TextView namee,unitprice,barcodeee,total_itemscoast,itemsnumber;


        public viewholder(View itemView) {
            super(itemView);

            productimage=itemView.findViewById(R.id.productimgd);
            namee=itemView.findViewById(R.id.nameproductd);
            unitprice=itemView.findViewById(R.id.unitpricecd);
            barcodeee=itemView.findViewById(R.id.numberproductd);
            total_itemscoast=itemView.findViewById(R.id.totalitemscd);
            itemsnumber=itemView.findViewById(R.id.numitemsd);



        }
    }
}

package customer.barcode.barcodewebx.Cashiery;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import customer.barcode.barcodewebx.R;
import customer.barcode.barcodewebx.RoomDatabase.historytable;
import customer.barcode.barcodewebx.RoomDatabase.productViewmodel;
import customer.barcode.barcodewebx.detailsadapter;

public class salesAdapter extends RecyclerView.Adapter<salesAdapter.viewholder> {

    private   Context con;
    private productViewmodel mHistoryViewModel;




    private final LayoutInflater mInflater;
    private List<historytable> mHistory; // Cached copy of words

    salesAdapter(Context context) {
        this.con=context;
        mInflater = LayoutInflater.from(context);

        mHistory=new ArrayList<>();


        mHistoryViewModel = ViewModelProviders.of((FragmentActivity) context).get(productViewmodel.class);

    }



    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.rowhistory, parent, false);
        return new salesAdapter.viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder holder, int position) {


        if (mHistory!=null)
        {


            holder.myrecycle.setLayoutManager(new LinearLayoutManager(con));
            holder.myrecycle.setAdapter(new detailsadapter(mHistory.get(position).getOrlist(),con));
            holder.myrecycle.scheduleLayoutAnimation();

            historytable mytable=mHistory.get(position);
            holder.orderid.setText(String.valueOf(position+1));
            holder.ordereshowdetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textcond=holder.ordereshowdetails.getText().toString();

                    if (textcond.equalsIgnoreCase("عرض التفاصيل")||textcond.equalsIgnoreCase("Show Details"))
                    {
                        holder.ordereshowdetails.setText(con.getResources().getString(R.string.hidedetails));
                        holder.myrecycle.setVisibility(View.VISIBLE);


                    }
                    else {
                        holder.ordereshowdetails.setText(con.getResources().getString(R.string.showdetails));
                        holder.myrecycle.setVisibility(View.GONE);


                    }







                }
            });

            holder.oredramount.setText(mytable.getOramount());

            holder.orderunits.setText(mytable.getOrunits());


            holder.orderdata.setText(mytable.getOrdata());
        }



    }

    void setHistory(List<historytable> words) {
        mHistory = words;
        notifyDataSetChanged();
    }

    void sethistoryfilter(List<historytable> table)
    {
        mHistory=table;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView orderdata,orderid,oredramount,orderunits,ordereshowdetails,teste;
        RecyclerView myrecycle;


        public viewholder(View itemView) {
            super(itemView);

            orderdata=itemView.findViewById(R.id.txt_orderDateTime);
            orderid=itemView.findViewById(R.id.id_order);
            oredramount=itemView.findViewById(R.id.totalordercoast);
            orderunits=itemView.findViewById(R.id.txt_orderQuantity);
            ordereshowdetails=itemView.findViewById(R.id.orderdetails);
            myrecycle=itemView.findViewById(R.id.order_list);


        }
    }
}

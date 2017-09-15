package co.kpham.ilovezappos;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import co.kpham.ilovezappos.data.fileClient;

/**
 * Created by Kito Pham on 9/9/2017.
 */

public class OrderBookAdaptor extends RecyclerView.Adapter<OrderBookAdaptor.ViewHolder> {

    public String dataset;
    private Context mContext;

    public OrderBookAdaptor(String s){
        dataset = s;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderbook_layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Sets the values of the repeating table entry items to specific values
    //Assigns column values
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        TextView bidText = holder.bid;
        TextView amountText= holder.amount;
        TextView valueText = holder.value;
        LinearLayout row = holder.row;
        List<List<String>> bids = BitStampSingleton.getData(dataset);
        double bidValue = Math.round(Double.parseDouble(bids.get(position).get(0)) * 100.0) / 100.0;
        double amountValue = Double.parseDouble(bids.get(position).get(1));
        bidText.setText(Double.toString(bidValue));
        amountText.setText(Double.toString(amountValue));
        valueText.setText(Double.toString(Math.round(bidValue * amountValue * 100.0)/100.0));
        if (position % 2 == 1) {
            row.setBackgroundColor(Color.parseColor("#D0ECFD"));
        } else {
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    //Returns the size of the table
    //outputs 0 if theres is no size, outputs 30 as maximum size
    //modify to change table size
    public int getItemCount(){
        try {
            if (BitStampSingleton.getData(dataset).size() >= 30){
                return 30;
            } else {
                return BitStampSingleton.getData(dataset).size();
            }
        } catch(NullPointerException e) {
            Log.d("OrderBookAdaptor", "getItemCount: no items");
            return 0;
        }
    }

    /*
    public void updateAnswers(BitCoinPOJO response){
        BitStampSingleton.orderBookResult = response;
    }
    */

    //Class that assigns item elements to variables for recyclerView
    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView bid;
        public TextView amount;
        public TextView value;
        public LinearLayout row;

        public ViewHolder(View itemView){
            super(itemView);
            bid = (TextView) itemView.findViewById(R.id.bidText);
            amount = (TextView) itemView.findViewById(R.id.amountText);
            value = (TextView) itemView.findViewById(R.id.valueText);
            row = (LinearLayout) itemView.findViewById(R.id.row);
        }

    }

}

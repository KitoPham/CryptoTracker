package co.kpham.ilovezappos;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        TextView bidText = holder.bid;
        TextView amountText= holder.amount;
        TextView valueText = holder.value;
        List<List<String>> bids = BitStampSingleton.getData(dataset);
        double bidValue = Double.parseDouble(bids.get(position).get(0));
        double amountValue = Double.parseDouble(bids.get(position).get(1));
        bidText.setText(bids.get(position).get(0));
        amountText.setText(bids.get(position).get(1));
        valueText.setText(Double.toString(bidValue * amountValue));
    }


    public int getItemCount(){
        try {
            if (BitStampSingleton.getData(dataset).size() >= 20){
                return 20;
            } else {
                return BitStampSingleton.getData(dataset).size();
            }
        } catch(NullPointerException e) {
            Log.d("OrderBookAdaptor", "getItemCount: error to display");
            return 0;
        }
    }

    public void updateAnswers(BitCoinPOJO response){
        BitStampSingleton.orderBookResult = response;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView bid;
        public TextView amount;
        public TextView value;

        public ViewHolder(View itemView){
            super(itemView);
            bid = (TextView) itemView.findViewById(R.id.bidText);
            amount = (TextView) itemView.findViewById(R.id.amountText);
            value = (TextView) itemView.findViewById(R.id.valueText);
        }

    }

}

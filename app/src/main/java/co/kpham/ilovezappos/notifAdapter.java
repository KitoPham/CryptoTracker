package co.kpham.ilovezappos;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Kito Pham on 9/14/2017.
 */


//Fills up the notification table with past notifications stored in a local file
public class notifAdapter extends RecyclerView.Adapter<notifAdapter.ViewHolder>{

    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public TextView notifText;


        public ViewHolder(View itemView){
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.Time);
            notifText = (TextView) itemView.findViewById(R.id.notifText);
        }

    }

    public notifAdapter(Context context){
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_layout,parent,false);
        notifAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    //Sets value of each table cell of the notification table
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        TextView date = viewHolder.date;
        TextView notifText =viewHolder.notifText;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        date.setText(BitStampSingleton.notifications.get(i).get(0));
        notifText.setText(BitStampSingleton.notifications.get(i).get(1));
    }


    @Override
    public int getItemCount() {
        return BitStampSingleton.notifications.size();
    }
}

package co.kpham.ilovezappos;

import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import co.kpham.ilovezappos.POJOS.BitCoinPOJO;
import co.kpham.ilovezappos.POJOS.PricePOJO;
import co.kpham.ilovezappos.POJOS.TickerPOJO;

/**
 * Created by Kito Pham on 9/10/2017.
 */

public class BitStampSingleton {
    private static BitStampSingleton singleton;
    public static BitCoinPOJO orderBookResult = null;
    public static List<PricePOJO> priceResult = null;
    public static TickerPOJO currentPrice;
    public static List<List<String>> notifications = new ArrayList<List<String>>();


    public static BitStampSingleton getInstance(){
        return singleton;
    }

    @Nullable
    public static List<List<String>> getData(String dataset){
        try {
            if (dataset.equals("Bids")) {
                return orderBookResult.getBids();
            } else {
                return orderBookResult.getAsks();
            }
        } catch(NullPointerException e) {
            Log.d("BitStampSingleton", "getBids: error no bids loaded");
            return null;
        }
    }


    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }
}

package co.kpham.ilovezappos;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import co.kpham.ilovezappos.POJOS.BitCoinPOJO;
import co.kpham.ilovezappos.POJOS.PricePOJO;

/**
 * Created by Kito Pham on 9/10/2017.
 */

public class BitStampSingleton {
    private static BitStampSingleton singleton;
    public static BitCoinPOJO orderBookResult = null;
    public static List<PricePOJO> priceResult = null;


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

    public static void saveResponse(BitCoinPOJO response){


    }
}

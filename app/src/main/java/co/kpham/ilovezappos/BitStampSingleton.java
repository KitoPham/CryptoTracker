package co.kpham.ilovezappos;

import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by Kito Pham on 9/10/2017.
 */

public class BitStampSingleton {
    private static BitStampSingleton singleton;
    public static BitCoinPOJO orderBookResult = null;

    public static BitStampSingleton getInstance(){
        return singleton;
    }

    @Nullable
    public static List<List<String>> getBids(){
        try {
            return orderBookResult.getBids();
        } catch(NullPointerException e) {
            Log.d("BitStampSingleton", "getBids: error no bids loaded");
            return null;
        }
    }
}

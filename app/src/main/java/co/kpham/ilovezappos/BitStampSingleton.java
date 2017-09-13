package co.kpham.ilovezappos;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
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

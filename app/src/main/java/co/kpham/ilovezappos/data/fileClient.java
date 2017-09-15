package co.kpham.ilovezappos.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;


import co.kpham.ilovezappos.POJOS.BitCoinPOJO;
import co.kpham.ilovezappos.BitStampSingleton;
import co.kpham.ilovezappos.POJOS.PricePOJO;
import co.kpham.ilovezappos.POJOS.PricesList;

public class fileClient {

    public static List<PricePOJO> priceslist;
    public static void writeFile(Object object, Context context, String filename) {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.writeValue(new File(context.getFilesDir()+filename+".json"), object);
            Log.d("fileClient", "writeFile:" + context.getFilesDir());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readFile(Context context, String type){
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            if (type.equals("OrderBook")) {
                BitStampSingleton.orderBookResult = mapper.readValue(new File(context.getFilesDir() +"response.json"), BitCoinPOJO.class);
            } else if (type.equals("Price")){
                BitStampSingleton.priceResult = mapper.readValue(new File(context.getFilesDir() +"prices.json"), new TypeReference<List<PricePOJO>>(){});
                //Log.d("FileClient", "readPrice: " + BitStampSingleton.priceResult);
            }
            //Log.d("fileClient", "readAsk:" + BitStampSingleton.orderBookResult.getAsks());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
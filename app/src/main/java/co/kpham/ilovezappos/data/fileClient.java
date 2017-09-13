package co.kpham.ilovezappos.data;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.*;


import co.kpham.ilovezappos.BitCoinPOJO;
import co.kpham.ilovezappos.BitStampSingleton;

public class fileClient {
    public static void writeFile(BitCoinPOJO object, Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.writeValue(new File(context.getFilesDir()+"response.json"), object);
            Log.d("fileClient", "writeFile " + context.getFilesDir());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void readFile(Context context){
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            BitStampSingleton.orderBookResult =  mapper.readValue(new File(context.getFilesDir()+"response.json"), BitCoinPOJO.class);
            Log.d("fileClient", "readFile:" + BitStampSingleton.orderBookResult.getAsks());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
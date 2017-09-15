package co.kpham.ilovezappos;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.kpham.ilovezappos.POJOS.PricePOJO;
import co.kpham.ilovezappos.data.APIService;
import co.kpham.ilovezappos.data.APIUtil;
import co.kpham.ilovezappos.data.fileClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.util.Date.parse;

/**
 * Created by Kito Pham on 9/12/2017.
 */

public class graphFragment extends Fragment {

    APIService mService;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.graph_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = APIUtil.getAPIService();
        renderGraph();
        loadPrices(getView().getContext());

    }

    public void renderGraph(){
        try {
            LineChart chart = (LineChart) getView().findViewById(R.id.chart);
            fileClient.readFile(getView().getContext(), "Price");
            List<PricePOJO> prices = BitStampSingleton.priceResult;
            List<Entry> entries = new ArrayList<Entry>();

            for (PricePOJO data : prices) {
                entries.add(new Entry(Float.parseFloat(data.getDate()), Float.parseFloat(data.getPrice())));
            }

            LineDataSet dataSet = new LineDataSet(entries, "Graph"); // add entries to dataset
            dataSet.setColor(Color.YELLOW);
            dataSet.setValueTextColor(Color.BLACK); // styling, ...

            LineData lineData = new LineData(dataSet);
            chart.setData(lineData);
            chart.invalidate(); // refresh
        } catch (NullPointerException e){
            //do nothing
        }
    }

    public void loadPrices(final Context context) {
        mService.getPrice().enqueue(new Callback<List<PricePOJO>>(){
            @Override
            public void onResponse(Call<List<PricePOJO>> call, Response<List<PricePOJO>> response) {

                if(response.isSuccessful()) {
                    Log.d("Process", "loadPrices: posts loaded from API");
                    fileClient.writeFile(response.body(), context, "prices");
                    Log.d("Process", "loadPrices: writing to file");
                    renderGraph();
                }else {
                    int statusCode  = response.code();
                    Log.d("Process", "onResponse: " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<List<PricePOJO>> call, Throwable t) {
                Log.d("Process:graph", "error loading from API");
            }
        });
    }
}

package co.kpham.ilovezappos;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import co.kpham.ilovezappos.POJOS.BitCoinPOJO;
import co.kpham.ilovezappos.data.APIService;
import co.kpham.ilovezappos.data.APIUtil;
import co.kpham.ilovezappos.data.fileClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Kito Pham on 9/12/2017.
 */

public class orderBookFragment extends Fragment {

    private OrderBookAdaptor bidBookAdapter;
    private OrderBookAdaptor askBookAdapter;
    private RecyclerView bidRecyclerView;
    private RecyclerView askRecyclerView;
    private APIService mService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        return inflater.inflate(
                R.layout.orderbook_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mService = APIUtil.getAPIService();
        bidBookAdapter = new OrderBookAdaptor("Bids");
        askBookAdapter = new OrderBookAdaptor("Asks");
        renderTable();
        loadOrderBook(getView().getContext());
    }

    public void renderTable(){
        try {
            Log.d("Process", "renderTable: starting to read file");
            fileClient.readFile(getView().getContext(), "OrderBook");
            Log.d("Process", "renderTable: file read");

            bidRecyclerView = (RecyclerView) getView().findViewById(R.id.bidRecycleView);
            RecyclerView.LayoutManager bidLayoutManager = new LinearLayoutManager(getView().getContext());
            bidRecyclerView.setLayoutManager(bidLayoutManager);
            bidRecyclerView.setAdapter(bidBookAdapter);
            bidRecyclerView.setHasFixedSize(true);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getView().getContext(), DividerItemDecoration.VERTICAL);
            bidRecyclerView.addItemDecoration(itemDecoration);

            askRecyclerView = (RecyclerView) getView().findViewById(R.id.askRecycleView);
            RecyclerView.LayoutManager askLayoutManager = new LinearLayoutManager(getView().getContext());
            askRecyclerView.setLayoutManager(askLayoutManager);
            askRecyclerView.setAdapter(askBookAdapter);
            askRecyclerView.setHasFixedSize(true);
            askRecyclerView.addItemDecoration(itemDecoration);
            getView().findViewById(R.id.tableLayout).setVisibility(View.VISIBLE);
        } catch (NullPointerException e){
            Log.d("Process:", "error with rendering table");
        }
    }
    public void loadOrderBook(final Context context) {
        mService.getBids().enqueue(new Callback<BitCoinPOJO>() {
            @Override
            public void onResponse(Call<BitCoinPOJO> call, Response<BitCoinPOJO> response) {

                if(response.isSuccessful()) {
                    Log.d("Process", "loadOrderBook: posts loaded from API");
                    fileClient.writeFile(response.body(), context, "response");
                    Log.d("Process", "loadOrderBook: writing to file");
                    renderTable();
                }else {
                    int statusCode  = response.code();
                    Log.d("Process", "onResponse: " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<BitCoinPOJO> call, Throwable t) {
                Log.d("Process:orderBook", "error loading from API");
            }
        });
    }
}

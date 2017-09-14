package co.kpham.ilovezappos.data;

/**
 * Created by Kito Pham on 9/9/2017.
 */

import java.util.List;

import co.kpham.ilovezappos.POJOS.BitCoinPOJO;
import co.kpham.ilovezappos.POJOS.PricePOJO;
import co.kpham.ilovezappos.POJOS.TickerPOJO;
import retrofit2.Call;
import retrofit2.http.GET;


public interface APIService {

    @GET("order_book/btcusd")
    Call<BitCoinPOJO> getBids();

    @GET("ticker/btcusd")
    Call<TickerPOJO> getTicker();

    @GET("transactions/btcusd")
    Call<List<PricePOJO>>getPrice();

    //@GET("order_book/btcusd")
    //Call<BitCoinPOJO> getAnswers(@Path("/btcusd") String bid);
}

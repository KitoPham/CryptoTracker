package co.kpham.ilovezappos.data;

/**
 * Created by Kito Pham on 9/9/2017.
 */
import android.util.Log;

import java.util.List;

import co.kpham.ilovezappos.BitCoinPOJO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface APIService {

    @GET("order_book/btcusd")
    Call<BitCoinPOJO> getBids();

    //@GET("order_book/btcusd")
    //Call<BitCoinPOJO> getAnswers(@Path("/btcusd") String bid);
}

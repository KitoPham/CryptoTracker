package co.kpham.ilovezappos.POJOS;

import java.util.List;

/**
 * Created by Kito Pham on 9/14/2017.
 */

public class PricesList {
   public List<PricePOJO> prices;
    PricesList(List<PricePOJO> list){
        prices = list;
    }

    public List<PricePOJO> getPrices(){
        return prices;
    }
}

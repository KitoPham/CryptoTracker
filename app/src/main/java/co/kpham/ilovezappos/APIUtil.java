package co.kpham.ilovezappos;

/**
 * Created by Kito Pham on 9/9/2017.
 */
public class APIUtil {

    public static final String BASE_URL = "https://www.bitstamp.net/api/v2/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
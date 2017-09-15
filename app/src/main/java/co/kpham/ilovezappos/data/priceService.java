package co.kpham.ilovezappos.data;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import co.kpham.ilovezappos.BitStampSingleton;
import co.kpham.ilovezappos.MainActivity;
import co.kpham.ilovezappos.POJOS.TickerPOJO;
import co.kpham.ilovezappos.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kito Pham on 9/14/2017.
 */

public class priceService extends IntentService {

    Handler mHandler;
    SharedPreferences prefs;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public priceService() {
        super("priceService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        prefs = getSharedPreferences("co.kpham.ilovezappos", Context.MODE_PRIVATE);
        APIService mService = APIUtil.getAPIService();
        Log.d("notifTest", "onStartJob: notif calling api");
        mService.getTicker().enqueue(new Callback<TickerPOJO>() {
            @Override
            public void onResponse(Call<TickerPOJO> call, Response<TickerPOJO> response) {

                if(response.isSuccessful()) {
                    BitStampSingleton.currentPrice = response.body();
                    Log.d("notifTest", "onResponse: current price setting " + prefs.getString("price", "4000"));
                    Log.d("notifTest", "onresponse success: current price received " + BitStampSingleton.currentPrice.getLast());
                    if(Double.parseDouble(prefs.getString("price", "4000")) > Double.parseDouble(BitStampSingleton.currentPrice.getLast())){
                        sendNotif();
                        List<String> notificationpair = new ArrayList<String>();
                        notificationpair.add(0, BitStampSingleton.getDate(Long.parseLong(BitStampSingleton.currentPrice.getTimestamp())));
                        notificationpair.add(1,"BitCoin price reached below " + prefs.getString("price", "4000") + " at " + BitStampSingleton.currentPrice.getLast());
                        BitStampSingleton.notifications.add(BitStampSingleton.notifications.size(), notificationpair);
                        fileClient.writeFile(BitStampSingleton.notifications, getApplicationContext(), "notifs");
                    }
                }else {
                    int statusCode  = response.code();
                    Log.d("Process", "onResponse: " + statusCode);
                    // handle request errors depending on status code
                }
            }
            @Override
            public void onFailure(Call<TickerPOJO> call, Throwable t) {
                Log.d("Process:service", "error loading from API");
            }
        });
    }

    public void sendNotif(){
        Log.d("notifTest", "sendNotif: notification shouldve sent");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("ILoveZappos")
                .setContentText("BitCoin Price reached below " + prefs.getString("price","4000") + "$ at a value of " + BitStampSingleton.currentPrice.getLast()+"$");
        //NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        //String[] events = new String[6];

        // Sets a title for the Inbox in expanded layout
        //inboxStyle.setBigContentTitle("BitCoin Notifications:");

        // Moves events into the expanded layout
        //for (int i=0; i < events.length; i++) {
          //  inboxStyle.addLine(events[i]);
        //}

        // Moves the expanded layout object into the notification object.
        //mBuilder.setStyle(inboxStyle);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // mNotificationId is a unique integer your app uses to identify the
        // notification. For example, to cancel the notification, you can pass its ID
        // number to NotificationManager.cancel().
        int mNotificationId = 1;
        mNotificationManager.notify(mNotificationId, mBuilder.build());
    }

}

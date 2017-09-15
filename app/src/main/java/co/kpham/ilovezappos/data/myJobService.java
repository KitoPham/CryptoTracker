package co.kpham.ilovezappos.data;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.ProgressBar;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import co.kpham.ilovezappos.BitStampSingleton;
import co.kpham.ilovezappos.MainActivity;
import co.kpham.ilovezappos.POJOS.BitCoinPOJO;
import co.kpham.ilovezappos.POJOS.TickerPOJO;
import co.kpham.ilovezappos.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

public class myJobService extends JobService {

    SharedPreferences prefs = this.getSharedPreferences("co.kpham.ilovezappos", Context.MODE_PRIVATE);
    @Override

    public boolean onStartJob(final JobParameters job) {
        APIService mService = APIUtil.getAPIService();
        Log.d("notifTest", "onStartJob: notif calling api");
        mService.getTicker().enqueue(new Callback<TickerPOJO>() {
            @Override
            public void onResponse(Call<TickerPOJO> call, Response<TickerPOJO> response) {

                if(response.isSuccessful()) {
                    BitStampSingleton.currentPrice = response.body();
                    Log.d("notifTest", "onResponse: current price setting " + prefs.getString("price", "DEFAULT"));
                    Log.d("notifTest", "onresponse success: current price received " + BitStampSingleton.currentPrice.getLast());
                    if(Double.parseDouble(prefs.getString("price", "DEFAULT")) > Double.parseDouble(BitStampSingleton.currentPrice.getLast())){
                        sendNotif(job);
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

        return true; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return true; // Answers the question: "Should this job be retried?"
    }



    public void sendNotif(JobParameters parameters){
        Log.d("notifTest", "sendNotif: notification shouldve sent");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle("ILoveZappos")
                .setContentText("BitCoin Price reached below " + "" + "at a value of" + BitStampSingleton.currentPrice.getLast());
        NotificationCompat.InboxStyle inboxStyle =
                new NotificationCompat.InboxStyle();
        String[] events = new String[6];

        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle("BitCoin Notifications:");

        // Moves events into the expanded layout
        for (int i=0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        // Moves the expanded layout object into the notification object.
        mBuilder.setStyle(inboxStyle);

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
        jobFinished(parameters, false);
    }
}
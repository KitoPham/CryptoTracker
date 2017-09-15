package co.kpham.ilovezappos.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Kito Pham on 9/14/2017.
 */

public class priceReceiver extends WakefulBroadcastReceiver {

    public priceReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("notifTest", "onReceive: notification service started");
        Intent service = new Intent(context, priceService.class);
        context.startService(service);

    }

    public void startReceiver(Context context){
        Log.d("notifTest", "startReceiver: notification receiver started");
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, priceReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR, alarmIntent);

    }

}

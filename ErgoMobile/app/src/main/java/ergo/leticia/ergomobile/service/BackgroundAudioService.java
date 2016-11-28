package ergo.leticia.ergomobile.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import ergo.leticia.ergomobile.R;
import ergo.leticia.ergomobile.activity.TesteAudioActivity;

public class BackgroundAudioService extends Service {
    private NotificationManager mNM;
    Bundle b;
    Intent notificationIntent;
    private final IBinder mBinder = new LocalBinder();


    public class LocalBinder extends Binder {
        BackgroundAudioService getService() {

            return BackgroundAudioService.this;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, TesteAudioActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        NotificationCompat.Builder b = new NotificationCompat.Builder(this);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("ErgoMobile")
                .setContentTitle("ErgoMobile")
                .setContentText("Para finalizar o teste clique aqui")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(TesteAudioActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(notificationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        b.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        int mId = 1;
        mNotificationManager.notify(mId, b.build());


        /*newtext = "BackGroundApp Service Running";

        Notification notification = new Notification(R.drawable.ic_launcher, newtext,System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(BackgroundService.this, 0, new Intent(BackgroundService.this,   BackgroundAppExample.class), 0);
        notification.setLatestEventInfo(BackgroundService.this,"BackgroundAppExample", newtext, contentIntent);
        mNM.notify(R.string.local_service_started, notification);
        notificationIntent = new Intent(this, BackgroundAppExample.class);
        showNotification();*/
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void onDestroy() {
        mNM.cancel(R.string.local_service_started);
        stopSelf();
    }

    /*private void showNotification() {
        CharSequence text = getText(R.string.local_service_started);

        Notification notification = new Notification(R.drawable.ic_launcher, text, System.currentTimeMillis());
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, BackgroundAppExample.class), 0);
        notification.setLatestEventInfo(this, "BackgroundAppExample",newtext, contentIntent);
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        mNM.notify(R.string.local_service_started, notification);
    }*/
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
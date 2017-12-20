package edu.ktu.cinemind.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.pages.reminder;


public class myService extends Service {

    Context context;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.context=this;

        YourTask(context,intent);

        return Service.START_STICKY;
    }

    private void YourTask(Context context,Intent intent){
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent=new Intent(context,reminder.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        /*Bundle extras= intent.getExtras();
        String movieName=extras.getString("extraMovieName");
        String dayLeft=extras.getString("extraLeftMovie");*/

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setContentTitle("Movie you set reminder is only a few days away.")
                .setContentText("We suggest you to check your reminder list.")
                /*.setContentTitle(movieName)
                .setContentText(dayLeft)*/
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.cinemind_test_logo_withouttext);
        notificationManager.notify(100,builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}

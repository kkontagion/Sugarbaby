package com.kkontagion.sugarbaby;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.kkontagion.sugarbaby.fragments.FoodFragment;
import com.kkontagion.sugarbaby.fragments.JournalFragment;
import com.kkontagion.sugarbaby.fragments.MeasurementsFragment;
import com.kkontagion.sugarbaby.fragments.MedsFragment;
import com.kkontagion.sugarbaby.fragments.MonsterFragment;
import com.kkontagion.sugarbaby.objects.MedicineFake;

public class MainActivity extends AppCompatActivity implements TriggerListener {
    public static final String TAG = "MainActivity";

    public static final String CHANNEL_ID_MED = "medication_channel";
    public static final String CHANNEL_ID_RATE = "rating_channel";
    public static final String CHANNEL_ID_APPT = "appointment_channel";


//    private int[] fragments = new int[] {
//            R.id.navigation_home, R.id.navigation_meds,
//            R.id.navigation_shots, R.id.navigation_food,
//            R.id.navigation_journal
//    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            String tag = "";
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    frag = MonsterFragment.newInstance();
                    tag = "monster";
                    break;
                case R.id.navigation_meds:
                    frag = MedsFragment.newInstance();
                    tag = "meds";
                    break;
                case R.id.navigation_shots:
                    frag = MeasurementsFragment.newInstance();
                    tag = "measurements";
                    break;
                case R.id.navigation_food:
                    frag = FoodFragment.newInstance();
                    tag = "food";
                    break;
                case R.id.navigation_journal:
                    frag = JournalFragment.newInstance();
                    tag = "journal";
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.content, frag, tag).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, MonsterFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void triggerPlsMedicate(MedicineFake med) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, CHANNEL_ID_MED)
                .setSmallIcon(R.mipmap.ic_meds)
                .setContentTitle(getString(R.string.notif_meds_title))
                .setContentText(getString(R.string.notif_meds, med.getName()));
        Intent open = new Intent(this, MainActivity.class);
        open.putExtra("tag", "meds");
        open.setAction("Done!");

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your app to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(open);

        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pi)
                .setVibrate(new long[] { 0, 500, 100, 500, 100 })
                .setLights(Color.RED, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat nManager = NotificationManagerCompat.from(getApplicationContext());
        nManager.notify(69, nBuilder.build());
    }

    @Override
    public void triggerHowYouDoin() {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, CHANNEL_ID_RATE)
                .setSmallIcon(R.mipmap.ic_heart)
                .setContentTitle(getString(R.string.notif_rate_title))
                .setContentText(getString(R.string.notif_rate));
        Intent open = new Intent(this, MainActivity.class);
        open.putExtra("tag", "journal");
        open.setAction("Done!");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(open);

        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pi)
                .setVibrate(new long[] { 0, 500, 100, 500, 100 })
                .setLights(Color.RED, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat nManager = NotificationManagerCompat.from(getApplicationContext());
        nManager.notify(70, nBuilder.build());
    }

    @Override
    public void triggerPlsSeeDoc(String[] details) {
        String out = getString(R.string.notif_appt, details[2], details[0], details[1]);
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, CHANNEL_ID_APPT)
                .setSmallIcon(R.mipmap.ic_doc)
                .setContentTitle(getString(R.string.notif_appt_title))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(out))
                .setContentText(out);
        Intent open = new Intent(this, MainActivity.class);
        open.putExtra("tag", "journal");
        open.setAction("Done!");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(open);

        PendingIntent pi = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pi)
                .setVibrate(new long[] { 0, 500, 100, 500, 100 })
                .setLights(Color.RED, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        NotificationManagerCompat nManager = NotificationManagerCompat.from(getApplicationContext());
        nManager.notify(71, nBuilder.build());
    }

    private void createNotifChannel() {
        NotificationManagerCompat nmanager = NotificationManagerCompat.from(getApplicationContext());
//        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, getString(R.string.notif_meds), NotificationManagerCompat.IMPORTANCE_HIGH);
    }
}

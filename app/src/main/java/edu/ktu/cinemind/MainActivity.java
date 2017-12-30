package edu.ktu.cinemind;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ktu.cinemind.adapters.PagerAdapter;
import edu.ktu.cinemind.objects.movieObj;
import edu.ktu.cinemind.objects.movieToSave;
import edu.ktu.cinemind.pages.favorites;
import edu.ktu.cinemind.pages.loginScreen;
import edu.ktu.cinemind.pages.reminder;
import edu.ktu.cinemind.pages.searchMovie;
import edu.ktu.cinemind.pages.soonpage;
import edu.ktu.cinemind.pages.watchlist;
import edu.ktu.cinemind.requestOperators.customListMoviesRequestOperator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,customListMoviesRequestOperator.RequestOperatorListener{


    private FirebaseAuth firebaseAuth;

    private Context context=this;
    private TextView loginedUser;
    String moviesText=new String();

    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Intent alarmIntent;

    public static List<movieObj> jsonMoviesNotification = new ArrayList<>();
    private List<movieObj> publications=new ArrayList<>();
    private DatabaseReference dbReminder;
    private movieToSave reminderMovies =new movieToSave();

    private boolean canUpdateNotification=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, loginScreen.class));
            finish();
        }

        dbReminder = FirebaseDatabase.getInstance().getReference("Reminder");

        FirebaseUser user=firebaseAuth.getCurrentUser();

        String welcomeTxt="Welcome "+user.getEmail();

        Toast.makeText(this,welcomeTxt, Toast.LENGTH_LONG);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Genres"));
        tabLayout.addTab(tabLayout.newTab().setText("Soon"));
        tabLayout.addTab(tabLayout.newTab().setText("Most Anticipated"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getText().equals("Soon")){
                    setTitle("CINEMIND");
                }
                else{
                    setTitle(tab.getText().toString().toUpperCase());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);

        loginedUser = (TextView) header.findViewById(R.id.loginedUserEmail);

        loginedUser.setText(user.getEmail());

        jsonMoviesNotification.clear();
    }

    private void createNotification(String moviesText){
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.cinemind_test_logo_withouttext)
                //.setTicker("Hearty365")
                .setContentTitle("Movies you set alarm are approaching")
                .setContentText("To view expand the notification.")
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Will Release");
        bigTextStyle.bigText(moviesText);

        b.setStyle(bigTextStyle);
        notificationManager.notify(1, b.build());
    }

    @Override
    protected void onResume() {
        super.onResume();


        dbReminder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersSnapShot : dataSnapshot.getChildren()){
                    if(usersSnapShot.getValue(movieToSave.class).getUser_id().equals(firebaseAuth.getCurrentUser().getUid()) && usersSnapShot.hasChild("movie_id")){
                        reminderMovies =usersSnapShot.getValue(movieToSave.class);
                        sendRequest(reminderMovies.getMovie_id());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void sendRequest(List<Integer> movieIds){
        customListMoviesRequestOperator.movieIds=movieIds;
        customListMoviesRequestOperator ro= new customListMoviesRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchbutton, menu);

        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.toolbar) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.search_button:
                Intent myIntent=new Intent(this,searchMovie.class);
                startActivity(myIntent);
                break;

            case R.id.action_sort:
                soonpage.sortSoonPageMovies();
                //Toast.makeText(context,"Movies are sorted according to release date.",Toast.LENGTH_SHORT);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Intent newAct = new Intent();


        switch (id){
            case R.id.nav_soon:
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_watchlist:
                newAct = new Intent(getApplicationContext(), watchlist.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(newAct);
                break;
            case R.id.nav_favorites:
                newAct = new Intent(getApplicationContext(), favorites.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(newAct);
                break;
            case R.id.nav_reminder:
                newAct = new Intent(getApplicationContext(), reminder.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(newAct);
                break;
            case R.id.nav_share:
                Intent myIntent=new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareTitle="Download now the Cinemind Movie Reminder from the Google Play\nhttps://play.google.com/store/apps/details?id=edu.ktu.cinemind";
                myIntent.putExtra(Intent.EXTRA_TEXT,shareTitle);
                startActivity(Intent.createChooser(myIntent,"Share the Cinemind"));
                break;
            case R.id.nav_logout:
                firebaseAuth.signOut();
                startActivity(new Intent(this, loginScreen.class));
                finish();
                break;

        }

        return true;
    }

    public void updatePublication(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(!publications.isEmpty()){
                    findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
                    for(int i=0;i<publications.size();i++){
                        //if(publications.get(i).getDayLeft()>=0){
                            jsonMoviesNotification.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                        //}
                    }
                    if(canUpdateNotification){
                        canUpdateNotification=false;
                        for(movieObj movie:jsonMoviesNotification) {
                            String[] releaseDateArray = movie.getRelease_date().split("-");

                            int year = Integer.parseInt(releaseDateArray[0]);
                            int month = Integer.parseInt(releaseDateArray[1]);
                            int day = Integer.parseInt(releaseDateArray[2]);

                            Date today = new Date();
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(today);

                            //Date release = new GregorianCalendar(year, month, day).getTime();
                            Calendar cal2 = Calendar.getInstance();
                            cal2.set(year, month - 1, day, 0, 0);

                            long msDiff =cal2.getTimeInMillis()-  cal.getInstance().getTimeInMillis();
                            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);

                            if(daysDiff>1){
                                moviesText+=movie.getTitle()+" ("+daysDiff+" days left)\n";
                            }
                            else if(daysDiff==0 && (cal2.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH)==1)){
                                moviesText+=movie.getTitle()+" (Releasing tomorrow)\n";
                            }
                            else if(daysDiff==0){
                                moviesText+=movie.getTitle()+" (Releasing today)\n";
                            }

                            if(moviesText.length()>0){
                                createNotification(moviesText);
                            }
                        }
                    }
                    }
                }
            }
        );
    }

    @Override
    public void success(List<movieObj> publications) {
        this.publications=publications;
        updatePublication();
    }

    @Override
    public void failed(int responseCode){
        this.publications=null;
        updatePublication();
    }
}
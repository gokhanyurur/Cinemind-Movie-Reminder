package edu.ktu.cinemind.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
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

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.requestOperators.customListMoviesRequestOperator;
import edu.ktu.cinemind.adapters.movieListAdapter;
import edu.ktu.cinemind.objects.movieObj;
import edu.ktu.cinemind.objects.movieToSave;


public class reminder extends AppCompatActivity implements customListMoviesRequestOperator.RequestOperatorListener {

    ListView reminderLv;
    private movieListAdapter reminderAdapter;

    public static List<movieObj> jsonMoviesReminder = new ArrayList<>();
    public static boolean clickedFromReminder;

    private DatabaseReference dbReminder;
    private FirebaseAuth firebaseAuth;
    private movieToSave reminderMovies =new movieToSave();
    private List<movieObj> publications=new ArrayList<>();

    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Reminder");

        setContentView(R.layout.reminderdesign);
        findViewById(R.id.noMovieSignRem).setVisibility(View.GONE);

        context=this;

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        jsonMoviesReminder.clear();

        reminderLv = (ListView) findViewById(R.id.reminderLv);

        firebaseAuth=FirebaseAuth.getInstance();
        dbReminder = FirebaseDatabase.getInstance().getReference("Reminder");

        reminderLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMoviesReminder.get(i).getId());
            }
        });

        reminderAdapter =new movieListAdapter(this, jsonMoviesReminder);
        reminderLv.setAdapter(reminderAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonMoviesReminder.clear();
        reminderLv.invalidateViews();

        findViewById(R.id.noMovieSignRem).setVisibility(View.VISIBLE);

        dbReminder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersSnapShot : dataSnapshot.getChildren()){
                    if(usersSnapShot.getValue(movieToSave.class).getUser_id().equals(firebaseAuth.getCurrentUser().getUid()) && usersSnapShot.hasChild("movie_id")){
                        reminderMovies =usersSnapShot.getValue(movieToSave.class);
                        sendRequest(reminderMovies.getMovie_id());
                    }
                }
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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

    private void goToMovieDetails(int movieId){
        clickedFromReminder =true;
        soonpage.clickedMovie=movieId;
        Intent newAct = new Intent(this,movieDetails.class);
        startActivity(newAct);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updatePublication(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(!publications.isEmpty() && publications!=null){
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++){

                        String[] releaseDateArray = publications.get(i).getRelease_date().split("-");

                        int year = Integer.parseInt(releaseDateArray[0]);
                        int month = Integer.parseInt(releaseDateArray[1]);
                        int day = Integer.parseInt(releaseDateArray[2]);

                        Date today = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(today);

                        Calendar cal2 = Calendar.getInstance();
                        cal2.set(year, month - 1, day, 0, 0);

                        long msDiff =cal2.getTimeInMillis()-  cal.getInstance().getTimeInMillis();
                        long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
                        daysDiff+=1;

                       if(daysDiff>0){
                           jsonMoviesReminder.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                           findViewById(R.id.noMovieSignRem).setVisibility(View.GONE);
                       }
                    }
                    reminderLv.invalidateViews();
                }
                else{
                    findViewById(R.id.noMovieSignRem).setVisibility(View.VISIBLE);
                }
            }
        });

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

package edu.ktu.cinemind.pages;

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
import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.requestOperators.customListMoviesRequestOperator;
import edu.ktu.cinemind.adapter.MovieListAdapter;
import edu.ktu.cinemind.entity.movieObj;
import edu.ktu.cinemind.entity.movieToSave;


public class watchlist extends AppCompatActivity implements customListMoviesRequestOperator.RequestOperatorListener {

    ListView WatchlistLv;
    private MovieListAdapter watchlistAdapter;

    public static List<movieObj> jsonMoviesWatchlist = new ArrayList<>();
    public static boolean clickedFromWatchlist;

    private DatabaseReference dbWatchlist;
    private FirebaseAuth firebaseAuth;
    private movieToSave watchlistMovie =new movieToSave();
    private List<movieObj> publications=new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Watchlist");

        setContentView(R.layout.watchlistdesign);
        findViewById(R.id.noMovieSignWL).setVisibility(View.GONE);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        jsonMoviesWatchlist.clear();

        WatchlistLv = (ListView) findViewById(R.id.watchlistLv);

        firebaseAuth=FirebaseAuth.getInstance();
        dbWatchlist = FirebaseDatabase.getInstance().getReference("Watchlist");



        WatchlistLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMoviesWatchlist.get(i).getId());
            }
        });

        watchlistAdapter =new MovieListAdapter(this, jsonMoviesWatchlist);
        WatchlistLv.setAdapter(watchlistAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonMoviesWatchlist.clear();
        WatchlistLv.invalidateViews();

        dbWatchlist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersSnapShot : dataSnapshot.getChildren()){
                    if(usersSnapShot.getValue(movieToSave.class).getUser_id().equals(firebaseAuth.getCurrentUser().getUid()) && usersSnapShot.hasChild("movie_id")){
                        watchlistMovie =usersSnapShot.getValue(movieToSave.class);
                        sendRequest(watchlistMovie.getMovie_id());
                    }
                    else{
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        findViewById(R.id.noMovieSignWL).setVisibility(View.VISIBLE);
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

    private void goToMovieDetails(int movieId){
        clickedFromWatchlist =true;
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
                    findViewById(R.id.noMovieSignWL).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++) {
                        jsonMoviesWatchlist.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(), publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                    }
                    WatchlistLv.invalidateViews();
                }else{
                    findViewById(R.id.noMovieSignWL).setVisibility(View.VISIBLE);
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

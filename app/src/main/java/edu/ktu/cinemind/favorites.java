package edu.ktu.cinemind;

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


public class favorites extends AppCompatActivity implements customListMoviesRequestOperator.RequestOperatorListener {

    ListView favoritesLv;
    private movieListAdapter favoritesAdapter;

    public static List<movieObj> jsonMoviesFavs = new ArrayList<>();
    public static boolean clickedFromFavs;

    private DatabaseReference dbFavorites;
    private FirebaseAuth firebaseAuth;
    private movieToSave favoriteMovies=new movieToSave();
    private List<movieObj> publications=new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My Favorites");

        setContentView(R.layout.favoritesdesign);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        jsonMoviesFavs.clear();

        favoritesLv= (ListView) findViewById(R.id.favoritesLv);

        firebaseAuth=FirebaseAuth.getInstance();
        dbFavorites= FirebaseDatabase.getInstance().getReference("Favorites");


       /* dbFavorites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersSnapShot : dataSnapshot.getChildren()){
                    if(usersSnapShot.getValue(movieToSave.class).getUser_id().equals(firebaseAuth.getCurrentUser().getUid()) && usersSnapShot.hasChild("movie_id")){
                        favoriteMovies=usersSnapShot.getValue(movieToSave.class);
                        sendRequest(favoriteMovies.getMovie_id());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        favoritesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMoviesFavs.get(i).getId());
            }
        });

        favoritesAdapter=new movieListAdapter(this, jsonMoviesFavs);
        favoritesLv.setAdapter(favoritesAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        jsonMoviesFavs.clear();
        favoritesLv.invalidateViews();

        dbFavorites.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot usersSnapShot : dataSnapshot.getChildren()){
                    if(usersSnapShot.getValue(movieToSave.class).getUser_id().equals(firebaseAuth.getCurrentUser().getUid()) && usersSnapShot.hasChild("movie_id")){
                        favoriteMovies=usersSnapShot.getValue(movieToSave.class);
                        sendRequest(favoriteMovies.getMovie_id());
                    }
                    else{
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        findViewById(R.id.noMovieSignFav).setVisibility(View.VISIBLE);
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
        clickedFromFavs=true;
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
                if(!publications.isEmpty()){
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    findViewById(R.id.noMovieSignFav).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++){
                        jsonMoviesFavs.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                    }
                    favoritesLv.invalidateViews();
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

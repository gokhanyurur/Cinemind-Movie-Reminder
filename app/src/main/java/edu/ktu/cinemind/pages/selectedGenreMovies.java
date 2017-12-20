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

import java.util.ArrayList;
import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.adapters.movieListAdapterMainPage;
import edu.ktu.cinemind.objects.movieObj;
import edu.ktu.cinemind.requestOperators.movieRequestOperator;


public class selectedGenreMovies extends AppCompatActivity implements movieRequestOperator.RequestOperatorListener {

    private ListView moviesLv;
    private List<movieObj> publications = new ArrayList<>();
    private movieListAdapterMainPage soonAdapter;
    public static List<movieObj> jsonMovies = new ArrayList<>();

    public static boolean clickedFromSGM;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.selectedgenremoviesdesign);

        jsonMovies.clear();

        moviesLv=(ListView) findViewById(R.id.genreMoviesList);

        soonAdapter = new movieListAdapterMainPage(this, jsonMovies);
        moviesLv.setAdapter(soonAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        setTitle(genreslist.clickedGenreText.toUpperCase());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sendRequest();
        moviesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMovies.get(i).getId());

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToMovieDetails(int movieId){
        clickedFromSGM=true;
        soonpage.clickedMovie=movieId;
        Intent newAct = new Intent(this,movieDetails.class);
        startActivity(newAct);
    }

    private void sendRequest(){
        movieRequestOperator.urlToRequest="https://api.themoviedb.org/3/genre/"+genreslist.clickedGenre+"/movies?api_key=a092bd16da64915723b2521295da3254&language=en-US&include_adult=false&sort_by=created_at.desc";
        movieRequestOperator ro= new movieRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    public void updatePublication(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(!publications.isEmpty()){
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++) {
                        if (!publications.get(i).getPoster_path().equals("null")) {
                            jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(), publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                        }
                    }
                    moviesLv.invalidateViews();
                }
                else{
                    // do nothing
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

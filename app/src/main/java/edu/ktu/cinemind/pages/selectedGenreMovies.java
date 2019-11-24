package edu.ktu.cinemind.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.adapter.MovieListAdapterMainPage;
import edu.ktu.cinemind.entity.movieObj;
import edu.ktu.cinemind.requestOperators.movieRequestOperator;


public class selectedGenreMovies extends AppCompatActivity implements movieRequestOperator.RequestOperatorListener {

    private ListView moviesLv;
    private List<movieObj> publications = new ArrayList<>();
    private MovieListAdapterMainPage soonAdapter;
    public static List<movieObj> jsonMovies = new ArrayList<>();

    public static boolean clickedFromSGM;

    private List<movieObj> nextPageJsonMovies = new ArrayList<>();
    public static boolean sorted=false,loadingMore=false;
    private int queryCurrentPage =1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.selectedgenremoviesdesign);

        jsonMovies.clear();

        moviesLv=(ListView) findViewById(R.id.genreMoviesList);

        soonAdapter = new MovieListAdapterMainPage(this, jsonMovies);
        moviesLv.setAdapter(soonAdapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        setTitle(genreslist.clickedGenreText.toUpperCase());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //sendRequest();
        moviesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMovies.get(i).getId());

            }
        });

        moviesLv.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !loadingMore){
                    loadingMore=true;
                    queryCurrentPage++;
                    sendRequest(String.valueOf(queryCurrentPage));
                }
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

    private void sendRequest(String page){
        movieRequestOperator.urlToRequest="https://api.themoviedb.org/3/genre/"+genreslist.clickedGenre+"/movies?api_key=a092bd16da64915723b2521295da3254&sort_by=created_at.desc&page="+page;
        movieRequestOperator ro= new movieRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    public void addListItemToAdapter(List<movieObj> list){
        jsonMovies.addAll(list);
        moviesLv.invalidateViews();
        soonAdapter.notifyDataSetChanged();
        nextPageJsonMovies.removeAll(nextPageJsonMovies);
        publications.removeAll(publications);
        loadingMore=false;
    }

    public void updatePublication(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(publications!=null && !publications.isEmpty()){
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++) {
                        if (!publications.get(i).getPoster_path().equals("null")) {
                            jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(), publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                        }
                    }
                    //moviesLv.invalidateViews();
                    addListItemToAdapter(nextPageJsonMovies);
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

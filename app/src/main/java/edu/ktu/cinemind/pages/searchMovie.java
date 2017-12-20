package edu.ktu.cinemind.pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.adapters.movieListAdapterMainPage;
import edu.ktu.cinemind.requestOperators.movieRequestOperator;
import edu.ktu.cinemind.objects.movieObj;


public class searchMovie extends AppCompatActivity implements movieRequestOperator.RequestOperatorListener {

    ListView searchLv;
    private movieListAdapterMainPage searchListAdapter;

    public static List<movieObj> jsonMoviesSearchMovie = new ArrayList<>();
    public static boolean clickedFromSearch;

    private FirebaseAuth firebaseAuth;
    //private movieToSave watchlistMovie =new movieToSave();
    private List<movieObj> publications=new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Search Movie");

        setContentView(R.layout.searchmoviedesign);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        jsonMoviesSearchMovie.clear();

        searchLv = (ListView) findViewById(R.id.searchLv);

        firebaseAuth=FirebaseAuth.getInstance();



        searchLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMoviesSearchMovie.get(i).getId());
            }
        });

        searchListAdapter =new movieListAdapterMainPage(this, jsonMoviesSearchMovie);
        searchLv.setAdapter(searchListAdapter);


    }

   /* @Override
    protected void onResume() {
        super.onResume();

        jsonMoviesSearchMovie.clear();
        searchLv.invalidateViews();
    }*/

    private void sendRequest(String searchViewText){
        movieRequestOperator.urlToRequest="https://api.themoviedb.org/3/search/movie?api_key=a092bd16da64915723b2521295da3254&query="+searchViewText;
        movieRequestOperator ro= new movieRequestOperator();
        ro.setListener(this);
        ro.start();

    }



    private void goToMovieDetails(int movieId){
        clickedFromSearch =true;
        soonpage.clickedMovie=movieId;
        Intent newAct = new Intent(this,movieDetails.class);
        startActivity(newAct);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        if(query.length()>0){
                            jsonMoviesSearchMovie.clear();
                            sendRequest(query);
                        }
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        if(newText.length()>0){
                            jsonMoviesSearchMovie.clear();
                            sendRequest(newText);
                        }
                        return false;
                    }
                }
        );
        return true;
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
                //if(!publications.isEmpty()){
                if(publications.size()>0){
                    //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++) {
                        jsonMoviesSearchMovie.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date(), publications.get(i).getPoster_path(), publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                    }
                    searchLv.invalidateViews();
                }
                else{
                    jsonMoviesSearchMovie.clear();
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
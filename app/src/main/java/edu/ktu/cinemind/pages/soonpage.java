package edu.ktu.cinemind.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ktu.cinemind.R;
import edu.ktu.cinemind.adapter.MovieListAdapterMainPage;
import edu.ktu.cinemind.entity.movieObj;
import edu.ktu.cinemind.requestOperators.movieRequestOperator;


public class soonpage extends android.support.v4.app.Fragment implements movieRequestOperator.RequestOperatorListener {

    private static ListView moviesLv;
    private MovieListAdapterMainPage soonAdapter;
    private List<movieObj> publications = new ArrayList<>();

    public static int clickedMovie;
    public static List<movieObj> jsonMovies = new ArrayList<>();

    private List<movieObj> nextPageJsonMovies = new ArrayList<>();

    Context thiscontext;

    public static boolean sorted=false,loadingMore=false;

    private int queryCurrentPage =2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.soonpagedesign, container, false);

        thiscontext=container.getContext();

        jsonMovies.clear();
        nextPageJsonMovies.clear();
        publications.clear();

        moviesLv =(ListView) rootview.findViewById(R.id.soonListView);
        soonAdapter = new MovieListAdapterMainPage(rootview.getContext(), jsonMovies);
        moviesLv.setAdapter(soonAdapter);

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

        return rootview;
    }

    public void addListItemToAdapter(List<movieObj> list){
        jsonMovies.addAll(list);
        moviesLv.invalidateViews();
        soonAdapter.notifyDataSetChanged();
        nextPageJsonMovies.removeAll(nextPageJsonMovies);
        publications.removeAll(publications);
        loadingMore=false;
    }

    private void goToMovieDetails(int movieId){
        clickedMovie=movieId;
        System.out.println(clickedMovie+" is clicked");
        Intent newAct = new Intent(getActivity(),movieDetails.class);
        startActivity(newAct);
    }
    private void sendRequest(String page){
        movieRequestOperator.urlToRequest="https://api.themoviedb.org/3/movie/upcoming?api_key=a092bd16da64915723b2521295da3254&sort_by=release_date.asc&page="+page;
        movieRequestOperator ro= new movieRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    public void updatePublication(){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                if(publications!=null && !publications.isEmpty()){
                    getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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

                        publications.get(i).setDayLeft((int)daysDiff);

                        if(daysDiff>1){
                            nextPageJsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date()+" ("+publications.get(i).getDayLeft()+" days left)", publications.get(i).getDayLeft() ,publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));

                        }
                        else if(daysDiff==0 && (cal2.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH)==1)){
                            daysDiff=cal2.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH);
                            nextPageJsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date()+" (Tomorrow)", (int)daysDiff, publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                        }
                        else if(daysDiff==0){
                            nextPageJsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date()+" (Today)" , publications.get(i).getDayLeft(), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                        }

                    }
                    addListItemToAdapter(nextPageJsonMovies);
                }
                else{
                    // do nothing
                }
            }
        });

    }

    public static void sortSoonPageMovies(){
        jsonMovies=sortMoviesList(jsonMovies);
        moviesLv.invalidateViews();
    }

    private static List<movieObj> sortMoviesList(List<movieObj> movieList){

        for(int i=0;i<movieList.size();i++){
            for(int j=i+1;j<movieList.size();j++){
                if(movieList.get(i).getDayLeft()>movieList.get(j).getDayLeft()){
                    movieObj tempObj=movieList.get(i);
                    movieList.set(i,movieList.get(j));
                    movieList.set(j,tempObj);
                }
            }
        }

        return movieList;
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

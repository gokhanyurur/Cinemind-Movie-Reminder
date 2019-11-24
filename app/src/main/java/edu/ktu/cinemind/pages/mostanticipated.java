package edu.ktu.cinemind.pages;

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


public class mostanticipated extends android.support.v4.app.Fragment implements movieRequestOperator.RequestOperatorListener {

    private ListView mostAntiListView;
    private MovieListAdapterMainPage mostAntiAdapter;
    private List<movieObj> publicationsMA = new ArrayList<>();

    public static List<movieObj> jsonMoviesMA = new ArrayList<>();

    private List<movieObj> nextPageJsonMovies = new ArrayList<>();

    public static boolean clickedFromMA;

    public static boolean loadingMore=false;

    private int queryCurrentPage =1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mostantipagedesign, container, false);

        mostAntiListView=(ListView) rootView.findViewById(R.id.mostAntListView);
        mostAntiAdapter=new MovieListAdapterMainPage(rootView.getContext(), jsonMoviesMA);
        mostAntiListView.setAdapter(mostAntiAdapter);

        jsonMoviesMA.clear();
        //sendRequest();


        mostAntiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMoviesMA.get(i).getId());

            }
        });

        mostAntiListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if((lastInScreen == totalItemCount) && !loadingMore && jsonMoviesMA.size()<5){
                    loadingMore=true;
                    queryCurrentPage++;
                    sendRequest(String.valueOf(queryCurrentPage));
                }
            }
        });

        return rootView;
    }

    private void goToMovieDetails(int movieId){
        clickedFromMA=true;
        soonpage.clickedMovie=movieId;
        Intent newAct = new Intent(getActivity(),movieDetails.class);
        startActivity(newAct);
    }
    private void sendRequest(String page){
        movieRequestOperator.urlToRequest="https://api.themoviedb.org/3/movie/upcoming?api_key=a092bd16da64915723b2521295da3254&sort_by=popularity.desc&page="+page;
        movieRequestOperator ro= new movieRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    public void addListItemToAdapter(List<movieObj> list){
        jsonMoviesMA.addAll(list);
        mostAntiListView.invalidateViews();
        mostAntiAdapter.notifyDataSetChanged();
        nextPageJsonMovies.removeAll(nextPageJsonMovies);
        publicationsMA.removeAll(publicationsMA);
        loadingMore=false;
    }

    public void updatePublication(){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //System.out.println(publicationsMA.get(1));
                if(publicationsMA!=null && !publicationsMA.isEmpty()){
                    getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    for(int i=0;i<publicationsMA.size();i++) {

                        String[] releaseDateArray = publicationsMA.get(i).getRelease_date().split("-");

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

                        publicationsMA.get(i).setDayLeft((int)daysDiff);

                        if (daysDiff>1) {
                            nextPageJsonMovies.add(new movieObj(publicationsMA.get(i).getId(), publicationsMA.get(i).getTitle(),publicationsMA.get(i).getRelease_date()+" ("+publicationsMA.get(i).getDayLeft()+" days left)", publicationsMA.get(i).getPoster_path(), publicationsMA.get(i).getBackdrop_path(), publicationsMA.get(i).getOverview(), publicationsMA.get(i).getVote_average()));
                        }else if (daysDiff==0 && (cal2.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH)==1)) {
                            daysDiff=cal2.get(Calendar.DAY_OF_MONTH)-cal.get(Calendar.DAY_OF_MONTH);
                            nextPageJsonMovies.add(new movieObj(publicationsMA.get(i).getId(), publicationsMA.get(i).getTitle(),publicationsMA.get(i).getRelease_date()+" (Tomorrow)", (int)daysDiff, publicationsMA.get(i).getPoster_path(), publicationsMA.get(i).getBackdrop_path(), publicationsMA.get(i).getOverview(), publicationsMA.get(i).getVote_average()));
                        }else if (daysDiff==0) {
                            nextPageJsonMovies.add(new movieObj(publicationsMA.get(i).getId(), publicationsMA.get(i).getTitle(), publicationsMA.get(i).getRelease_date()+" (Today)" , publicationsMA.get(i).getDayLeft(), publicationsMA.get(i).getPoster_path(), publicationsMA.get(i).getBackdrop_path(), publicationsMA.get(i).getOverview(), publicationsMA.get(i).getVote_average()));
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


    @Override
    public void success(List<movieObj> publications) {
        this.publicationsMA =publications;
        updatePublication();
    }

    @Override
    public void failed(int responseCode){
        this.publicationsMA =null;
        updatePublication();
    }
}

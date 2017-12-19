package edu.ktu.cinemind;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class soonpage extends android.support.v4.app.Fragment implements movieRequestOperator.RequestOperatorListener {

    private static ListView moviesLv;
    private movieListAdapterMainPage soonAdapter;
    private List<movieObj> publications = new ArrayList<>();

    public static int clickedMovie;
    public static List<movieObj> jsonMovies = new ArrayList<>();

    Context thiscontext;

    public static boolean sorted=false;

    private int queryStartPage=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview= inflater.inflate(R.layout.soonpagedesign, container, false);

        thiscontext=container.getContext();


        jsonMovies.clear();


        moviesLv =(ListView) rootview.findViewById(R.id.soonListView);
        soonAdapter = new movieListAdapterMainPage(rootview.getContext(), jsonMovies);
        moviesLv.setAdapter(soonAdapter);

        sendRequest(String.valueOf(queryStartPage));

        /*for(int i=2;i<=13;i++){
            queryStartPage++;
            if(queryStartPage<=13){
                sendRequest(String.valueOf(queryStartPage));
            }
            sendRequest(String.valueOf(i));
        }*/

        moviesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //System.out.println(jsonMoviesMA.get(i).getId()+" is clicked");
                goToMovieDetails(jsonMovies.get(i).getId());

            }
        });

        /*SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(thiscontext);
        String text = app_preferences.getString("")*/

        //loadingPanel=(RelativeLayout) rootview.findViewById(R.id.loadingPanel);


        return rootview;
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
                //System.out.println(publications.get(1));
                if(!publications.isEmpty()){
                    getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                    for(int i=0;i<publications.size();i++){

                        String[] releaseDateArray = publications.get(i).getRelease_date().split("-");

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
                        daysDiff+=1;

                        publications.get(i).setDayLeft((int)daysDiff);

                        if(!publications.get(i).getPoster_path().equals("null") && daysDiff>1){
                            //jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), (publications.get(i).getRelease_date()+(" ("+daysDiff+" days left)")), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                            jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date()+" ("+publications.get(i).getDayLeft()+" days left)", publications.get(i).getDayLeft() ,publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));

                        }
                        else if(!publications.get(i).getPoster_path().equals("null") && daysDiff==1){
                            //jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), (publications.get(i).getRelease_date()+(" (Tomorrow)")), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                            jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date()+" (Releasing tomorrow)", publications.get(i).getDayLeft(), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                        }
                        else if(!publications.get(i).getPoster_path().equals("null") && daysDiff==0){
                            //jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), (publications.get(i).getRelease_date()+" (Releasing today)"), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
                            jsonMovies.add(new movieObj(publications.get(i).getId(), publications.get(i).getTitle(), publications.get(i).getRelease_date()+" (Releasing today)" , publications.get(i).getDayLeft(), publications.get(i).getPoster_path(),publications.get(i).getBackdrop_path(), publications.get(i).getOverview(), publications.get(i).getVote_average()));
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

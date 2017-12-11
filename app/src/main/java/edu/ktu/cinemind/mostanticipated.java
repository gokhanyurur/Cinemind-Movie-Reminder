package edu.ktu.cinemind;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class mostanticipated extends android.support.v4.app.Fragment implements  movieRequestOperator.RequestOperatorListener{

    private ListView mostAntiListView;
    private movieListAdapter mostAntiAdapter;
    private List<movieObj> publicationsMA = new ArrayList<>();

    public static List<movieObj> jsonMoviesMA = new ArrayList<>();

    public static boolean clickedFromMA;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mostantipagedesign, container, false);

        mostAntiListView=(ListView) rootView.findViewById(R.id.mostAntListView);
        mostAntiAdapter=new movieListAdapter(rootView.getContext(), jsonMoviesMA);
        mostAntiListView.setAdapter(mostAntiAdapter);

        jsonMoviesMA.clear();
        sendRequest();


        mostAntiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToMovieDetails(jsonMoviesMA.get(i).getId());

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
    private void sendRequest(){
        movieRequestOperator.urlToRequest="https://api.themoviedb.org/3/movie/upcoming?api_key=a092bd16da64915723b2521295da3254&sort_by=popularity.desc&page=1";
        movieRequestOperator ro= new movieRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    public void updatePublication(){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //System.out.println(publicationsMA.get(1));
                if(!publicationsMA.isEmpty()){
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
                        daysDiff+=1;

                        if (!publicationsMA.get(i).getPoster_path().equals("null") && daysDiff>1) {
                            jsonMoviesMA.add(new movieObj(publicationsMA.get(i).getId(), publicationsMA.get(i).getTitle(), (daysDiff+" days left"), publicationsMA.get(i).getPoster_path(), publicationsMA.get(i).getBackdrop_path(), publicationsMA.get(i).getOverview(), publicationsMA.get(i).getVote_average()));
                        }else if (!publicationsMA.get(i).getPoster_path().equals("null") && daysDiff==1) {
                            jsonMoviesMA.add(new movieObj(publicationsMA.get(i).getId(), publicationsMA.get(i).getTitle(), (daysDiff+" day left"), publicationsMA.get(i).getPoster_path(), publicationsMA.get(i).getBackdrop_path(), publicationsMA.get(i).getOverview(), publicationsMA.get(i).getVote_average()));
                        }else if (!publicationsMA.get(i).getPoster_path().equals("null") && daysDiff==0) {
                            jsonMoviesMA.add(new movieObj(publicationsMA.get(i).getId(), publicationsMA.get(i).getTitle(), "Releasing today.", publicationsMA.get(i).getPoster_path(), publicationsMA.get(i).getBackdrop_path(), publicationsMA.get(i).getOverview(), publicationsMA.get(i).getVote_average()));
                        }
                    }
                    mostAntiListView.invalidateViews();
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

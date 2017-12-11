package edu.ktu.cinemind;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class genreslist extends android.support.v4.app.Fragment implements genresRequestOperator.RequestOperatorListener{

    private ListView genresLv;
    private genreListAdapter genresAdapter;
    public static List<genreObj> genres = new ArrayList<>();
    public static int clickedGenre;
    public static String clickedGenreText;

    private List<genreObj> publicationsGenre = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.genrepagedesign, container, false);

        genres.clear();

        genresLv =(ListView) rootView.findViewById(R.id.genresListView);


        genresAdapter = new genreListAdapter(rootView.getContext(), genres);
        genresLv.setAdapter(genresAdapter);

        sendRequest();

        genresLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                goToGenreMovies(genres.get(i).getId(), genres.get(i).getTitle());

            }
        });
        return rootView;
    }

    private void goToGenreMovies(int genreId, String genreText){
        clickedGenre=genreId;
        clickedGenreText=genreText;
        System.out.println(clickedGenre+" genre is clicked");
        Intent newAct = new Intent(getActivity(),selectedGenreMovies.class);
        startActivity(newAct);
    }

    private void sendRequest(){
        genresRequestOperator.urlToRequest="https://api.themoviedb.org/3/genre/movie/list?api_key=a092bd16da64915723b2521295da3254&language=en-US";
        genresRequestOperator ro= new genresRequestOperator();
        ro.setListener(this);
        ro.start();

    }

    public void updatePublication(){
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run(){
                //System.out.println(publications.get(1));
                if(!publicationsGenre.isEmpty()){
                    getActivity().findViewById(R.id.loadingPanelGenreList).setVisibility(View.GONE);
                    for(int i=0;i<publicationsGenre.size();i++){
                        if(publicationsGenre.get(i).getTitle().equals("Action")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_action));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Adventure")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_adventures_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Animation")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_alien_animationfilled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Documentary")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_documentary_filled_50));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Comedy")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_comedy_2));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Crime")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_crime_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Drama")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_drama_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Family")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icon_family));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Fantasy")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_dragon_fantasy));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("History")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_museum_filled_50));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Horror")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_horror));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Music")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_musical_notes));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Mystery")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_detective_filled_50));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Romance")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_romance_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Science Fiction")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_sci_fi_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("TV Movie")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_retro_tv_filled_50));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Thriller")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_thriller_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("War")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_explosion_filled));
                        }
                        else if(publicationsGenre.get(i).getTitle().equals("Western")){
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_western_50));
                        }

                        else{
                            genres.add(new genreObj(publicationsGenre.get(i).getId(), publicationsGenre.get(i).getTitle(), R.drawable.icons8_adventures_filled));
                        }
                    }
                    genresLv.invalidateViews();
                }
                else{
                    // do nothing
                }
            }
        });

    }


    @Override
    public void success(List<genreObj> publications) {
        this.publicationsGenre=publications;
        updatePublication();
    }

    @Override
    public void failed(int responseCode){
        this.publicationsGenre=null;
        updatePublication();
    }


}

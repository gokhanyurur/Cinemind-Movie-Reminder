package edu.ktu.cinemind;

import java.util.ArrayList;
import java.util.List;

public class movieObj {
    private int vote_average,vote_count,id,length;
    private double popularity;
    private String title,poster_path,original_language,original_title,backdrop_path,overview;
    private String release_date;
    private boolean adult,video;
    private List<genreObj> genres=new ArrayList<>();
    private List<cast> castList=new ArrayList<>();
    private List<crew> crewList=new ArrayList<>();
    private List<video> videoList=new ArrayList<>();
    private List<image> imageList=new ArrayList<>();
    private String status;

    public movieObj(){

    }

    public movieObj(int id,String title,String release_date,String poster_path){
        this.id=id;
        this.title=title;
        this.release_date=release_date;
        this.poster_path=poster_path;
    }


    public movieObj(int id,String title,String release_date, String poster_path, String backdrop_path, String overview, int vote_average){
        this.id=id;
        this.title=title;
        this.release_date=release_date;
        this.poster_path=poster_path;
        this.backdrop_path=backdrop_path;
        this.overview=overview;
        this.vote_average=vote_average;
    }


    public movieObj(int vote_count, int id, boolean video,int vote_average, String title, double popularity, String poster_path, String original_language, String original_title,List<genreObj> genres,String backdrop_path, boolean adult,String overview,String release_date){
        this.vote_count=vote_count;
        this.id=id;
        this.video=video;
        this.vote_average=vote_average;
        this.title=title;
        this.popularity=popularity;
        this.poster_path=poster_path;
        this.original_language=original_language;
        this.original_title=original_title;
        this.genres=genres;
        this.backdrop_path=backdrop_path;
        this.adult=adult;
        this.overview=overview;
        this.release_date=release_date;
    }

    public int getVote_count(){
        return vote_count;
    }
    public void setVote_count(int voteCount){
        this.vote_count=vote_count;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public boolean getVideo(){
        return video;
    }
    public void setVideo(boolean video){
        this.video=video;
    }

    public int getVote_average(){
        return vote_average;
    }
    public void setVote_average(int vote_average){
        this.vote_average=vote_average;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public double getPopularity(){
        return popularity;
    }
    public void setPopularity(double popularity){
        this.popularity=popularity;
    }

    public String getPoster_path(){
        return poster_path;
    }
    public void setPoster_path(String poster_path){
        this.poster_path=poster_path;
    }

    public String getOriginal_language(){
        return original_language;
    }
    public void setOriginal_language(String original_language){
        this.original_language=original_language;
    }

    public String getOriginal_title(){
        return original_title;
    }
    public void setOriginal_title(String original_title){
        this.original_title=original_title;
    }

    public List<genreObj> getGenres(){
        return genres;
    }
    public void setGenres(List<genreObj> genres){
        this.genres=genres;
    }

    public String getBackdrop_path(){
        return backdrop_path;
    }
    public void setBackdrop_path(String backdrop_path){
        this.backdrop_path=backdrop_path;
    }

    public boolean getAdult(){
        return adult;
    }
    public void setAdult(boolean adult){
        this.adult=adult;
    }

    public String getOverview(){
        return overview;
    }
    public void setOverview(String overview){
        this.overview=overview;
    }

    public String getRelease_date(){
        return release_date;
    }
    public void setRelease_date(String release_date){
        this.release_date=release_date;
    }

    public int getLength(){
        return length;
    }
    public void setLength(int length){
        this.length=length;
    }

    public List<cast> getCastList(){
        return castList;
    }
    public void setCastList(List<cast> castList){
        this.castList=castList;
    }

    public List<crew> getCrewList()    {
        return crewList;
    }
    public void setCrewList(List<crew> crewList){
        this.crewList=crewList;
    }

    public List<video> getVideoList(){
        return videoList;
    }
    public void setVideoList(List<video> videoList){
        this.videoList=videoList;
    }

    public List<image> getImagesList(){
        return imageList;
    }
    public void setImageList(List<image> imageList){
        this.imageList=imageList;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }

    public static movieObj getMovieByID(List<movieObj> movies, int id){
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).getId()==id){
                return movies.get(i);
            }
        }
        return null;
    }


}

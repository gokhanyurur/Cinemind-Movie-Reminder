package edu.ktu.cinemind.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Movie.
 */
public class Movie {

    /**
     * The Vote count.
     */
    private int voteCount;

    /**
     * The Id.
     */
    private int id;

    /**
     * The Length.
     */
    private int length;

    /**
     * The Day left.
     */
    private int dayLeft;

    /**
     * The Popularity.
     */
    private double popularity;

    /**
     * The Vote average.
     */
    private double voteAverage;

    /**
     * The Title.
     */
    private String title;

    /**
     * The Poster path.
     */
    private String posterPath;

    /**
     * The Original language.
     */
    private String originalLanguage;

    /**
     * The Original title.
     */
    private String originalTitle;

    /**
     * The Backdrop path.
     */
    private String backdropPath;

    /**
     * The Overview.
     */
    private String overview;
    /**
     * The Release date.
     */
    private String releaseDate;

    /**
     * The For adult.
     */
    private boolean forAdult;

    /**
     * The Has Video.
     */
    private boolean hasVideo;

    /**
     * The Genres.
     */
    private List<Genre> genres = new ArrayList<>();

    /**
     * The Cast list.
     */
    private List<Cast> castList = new ArrayList<>();

    /**
     * The Crew list.
     */
    private List<Crew> crewList=new ArrayList<>();

    /**
     * The Video list.
     */
    private List<Video> videoList=new ArrayList<>();

    /**
     * The Image list.
     */
    private List<Image> imageList=new ArrayList<>();

    /**
     * The Status.
     */
    private String status;

    /**
     * Instantiates a new Movie.
     */
    public Movie(){

    }

    /**
     * Instantiates a new Movie.
     *
     * @param id           the id
     * @param title        the title
     * @param releaseDate the release date
     * @param posterPath  the poster path
     */
    public Movie(int id, String title, String releaseDate, String posterPath){
        this.id=id;
        this.title=title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
    }


    /**
     * Instantiates a new Movie.
     *
     * @param id            the id
     * @param title         the title
     * @param releaseDate  the release date
     * @param dayLeft       the day left
     * @param posterPath   the poster path
     * @param backdropPath the backdrop path
     * @param overview      the overview
     * @param voteAverage  the vote average
     */
    public Movie(int id, String title, String releaseDate, int dayLeft , String posterPath, String backdropPath, String overview, double voteAverage){
        this.id=id;
        this.title=title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview=overview;
        this.voteAverage = voteAverage;
        this.dayLeft=dayLeft;
    }

    /**
     * Instantiates a new Movie.
     *
     * @param id            the id
     * @param title         the title
     * @param releaseDate  the release date
     * @param posterPath   the poster path
     * @param backdropPath the backdrop path
     * @param overview      the overview
     * @param voteAverage  the vote average
     */
    public Movie(int id, String title, String releaseDate, String posterPath, String backdropPath, String overview, double voteAverage){
        this.id=id;
        this.title=title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview=overview;
        this.voteAverage = voteAverage;
    }


    /**
     * Instantiates a new Movie.
     *
     * @param voteCount        the vote count
     * @param id                the id
     * @param hasVideo          the has Video
     * @param voteAverage      the vote average
     * @param title             the title
     * @param popularity        the popularity
     * @param posterPath       the poster path
     * @param originalLanguage the original language
     * @param originalTitle    the original title
     * @param genres            the genres
     * @param backdropPath     the backdrop path
     * @param forAdult          the for adult
     * @param overview          the overview
     * @param releaseDate      the release date
     */
    public Movie(int voteCount, int id, boolean hasVideo, double voteAverage, String title, double popularity, String posterPath, String originalLanguage, String originalTitle, List<Genre> genres, String backdropPath, boolean forAdult, String overview, String releaseDate){
        this.voteCount = voteCount;
        this.id=id;
        this.hasVideo = hasVideo;
        this.voteAverage = voteAverage;
        this.title=title;
        this.popularity=popularity;
        this.posterPath = posterPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.genres=genres;
        this.backdropPath = backdropPath;
        this.forAdult = forAdult;
        this.overview=overview;
        this.releaseDate = releaseDate;
    }

    /**
     * Get vote count int.
     *
     * @return the int
     */
    public int getVoteCount(){
        return voteCount;
    }

    /**
     * Set vote count.
     *
     * @param voteCount the vote count
     */
    public void setVoteCount(int voteCount){
        this.voteCount = voteCount;
    }

    /**
     * Get id int.
     *
     * @return the int
     */
    public int getId(){
        return id;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Get has Video boolean.
     *
     * @return the boolean
     */
    public boolean getHasVideo(){
        return hasVideo;
    }

    /**
     * Set has Video.
     *
     * @param hasVideo the has Video
     */
    public void setHasVideo(boolean hasVideo){
        this.hasVideo = hasVideo;
    }

    /**
     * Get vote average double.
     *
     * @return the double
     */
    public double getVoteAverage(){
        return voteAverage;
    }

    /**
     * Set vote average.
     *
     * @param voteAverage the vote average
     */
    public void setVoteAverage(double voteAverage){
        this.voteAverage = voteAverage;
    }

    /**
     * Get title string.
     *
     * @return the string
     */
    public String getTitle(){
        return title;
    }

    /**
     * Set title.
     *
     * @param title the title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Get popularity double.
     *
     * @return the double
     */
    public double getPopularity(){
        return popularity;
    }

    /**
     * Set popularity.
     *
     * @param popularity the popularity
     */
    public void setPopularity(double popularity){
        this.popularity = popularity;
    }

    /**
     * Get poster path string.
     *
     * @return the string
     */
    public String getPosterPath(){
        return posterPath;
    }

    /**
     * Set poster path.
     *
     * @param posterPath the poster path
     */
    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    /**
     * Get original language string.
     *
     * @return the string
     */
    public String getOriginalLanguage(){
        return originalLanguage;
    }

    /**
     * Set original language.
     *
     * @param originalLanguage the original language
     */
    public void setOriginalLanguage(String originalLanguage){
        this.originalLanguage = originalLanguage;
    }

    /**
     * Get original title string.
     *
     * @return the string
     */
    public String getOriginalTitle(){
        return originalTitle;
    }

    /**
     * Set original title.
     *
     * @param originalTitle the original title
     */
    public void setOriginalTitle(String originalTitle){
        this.originalTitle = originalTitle;
    }

    /**
     * Get genres list.
     *
     * @return the list
     */
    public List<Genre> getGenres(){
        return genres;
    }

    /**
     * Set genres.
     *
     * @param genres the genres
     */
    public void setGenres(List<Genre> genres){
        this.genres=genres;
    }

    /**
     * Get backdrop path string.
     *
     * @return the string
     */
    public String getBackdropPath(){
        return backdropPath;
    }

    /**
     * Set backdrop path.
     *
     * @param backdropPath the backdrop path
     */
    public void setBackdropPath(String backdropPath){
        this.backdropPath = backdropPath;
    }

    /**
     * Get for adult boolean.
     *
     * @return the boolean
     */
    public boolean getForAdult(){
        return forAdult;
    }

    /**
     * Set for adult.
     *
     * @param forAdult the for adult
     */
    public void setForAdult(boolean forAdult){
        this.forAdult = forAdult;
    }

    /**
     * Get overview string.
     *
     * @return the string
     */
    public String getOverview(){
        return overview;
    }

    /**
     * Set overview.
     *
     * @param overview the overview
     */
    public void setOverview(String overview){
        this.overview=overview;
    }

    /**
     * Get release date string.
     *
     * @return the string
     */
    public String getReleaseDate(){
        return releaseDate;
    }

    /**
     * Set release date.
     *
     * @param releaseDate the release date
     */
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    /**
     * Get length int.
     *
     * @return the int
     */
    public int getLength(){
        return length;
    }

    /**
     * Set length.
     *
     * @param length the length
     */
    public void setLength(int length){
        this.length=length;
    }

    /**
     * Get cast list list.
     *
     * @return the list
     */
    public List<Cast> getCastList(){
        return castList;
    }

    /**
     * Set cast list.
     *
     * @param castList the cast list
     */
    public void setCastList(List<Cast> castList){
        this.castList=castList;
    }

    /**
     * Gets crew list.
     *
     * @return the crew list
     */
    public List<Crew> getCrewList()    {
        return crewList;
    }

    /**
     * Set crew list.
     *
     * @param crewList the crew list
     */
    public void setCrewList(List<Crew> crewList){
        this.crewList=crewList;
    }

    /**
     * Get Video list list.
     *
     * @return the list
     */
    public List<Video> getVideoList(){
        return videoList;
    }

    /**
     * Set Video list.
     *
     * @param videoList the Video list
     */
    public void setVideoList(List<Video> videoList){
        this.videoList=videoList;
    }

    /**
     * Get images list list.
     *
     * @return the list
     */
    public List<Image> getImagesList(){
        return imageList;
    }

    /**
     * Set image list.
     *
     * @param imageList the image list
     */
    public void setImageList(List<Image> imageList){
        this.imageList=imageList;
    }

    /**
     * Set day left.
     *
     * @param dayLeft the day left
     */
    public void setDayLeft(int dayLeft){
        this.dayLeft=dayLeft;
    }

    /**
     * Get day left int.
     *
     * @return the int
     */
    public int getDayLeft(){
        return dayLeft;
    }

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus(){
        return status;
    }

    /**
     * Set status.
     *
     * @param status the status
     */
    public void setStatus(String status){
        this.status=status;
    }

    /** TODO move this to its service.
     * Gets movie by movie id.
     *
     * @param movies the movies
     * @param id     the id
     * @return the movie
     */
    public static Movie getMovieByID(List<Movie> movies, int id){
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).getId()==id){
                return movies.get(i);
            }
        }
        return null;
    }


}

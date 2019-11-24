package edu.ktu.cinemind.requestOperators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.ktu.cinemind.entity.Cast;
import edu.ktu.cinemind.entity.Crew;
import edu.ktu.cinemind.entity.Genre;
import edu.ktu.cinemind.entity.Image;
import edu.ktu.cinemind.entity.Movie;
import edu.ktu.cinemind.entity.Video;

public class movieDetailsRequestOperator extends Thread {

    public static String apiKey = "a092bd16da64915723b2521295da3254";
    public static String urlToRequest;

    public interface RequestOperatorListener{
        void success(Movie publication);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    public Movie publication;

    public void setListener(RequestOperatorListener listener) {
        this.listener = listener;
    }

    @Override
    public void run(){
        super.run();
        try{
            publication=request(urlToRequest);
            if(publication != null)
                success(publication);
            else
                failed(responseCode);
        }catch (IOException e){
            failed(-1);
        } catch (JSONException e){
            failed(-2);
        }
    }

    private Movie request(String url) throws IOException, JSONException {

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        responseCode = con.getResponseCode();

        InputStreamReader streamReader;


        if(responseCode == 200){
            streamReader = new InputStreamReader(con.getInputStream());
        }else{
            streamReader = new InputStreamReader(con.getErrorStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();

        if(responseCode == 200){
            return parsingJsonObject(response.toString());
        }
        else
            return null;
    }

    public Movie parsingJsonObject(String response) throws JSONException{

        JSONObject object=new JSONObject(response);

        Movie post = new Movie();

        post.setId(object.optInt("id"));

        post.setOverview(object.getString("overview"));

        post.setStatus(object.getString("status"));

        post.setPosterPath(object.getString("poster_path"));

        post.setBackdropPath(object.getString("backdrop_path"));

        post.setTitle(object.getString("title"));

        post.setReleaseDate(object.getString("release_date"));

        post.setLength(object.optInt("runtime"));

        JSONArray genresJsonArray =object.getJSONArray("genres");
        List<Genre> genres =new ArrayList<>();
        for (int i = 0; i < genresJsonArray.length(); i++) {
            JSONObject jsonobject = genresJsonArray.getJSONObject(i);
            Genre postcp=parseGenresArray(jsonobject.toString());
            genres.add(postcp);
        }
        post.setGenres(genres);

        //set Cast
        JSONArray castJsonArray=getJsonCast(object.getString("credits"));
        List<Cast> castList =new ArrayList<>();
        for (int i = 0; i < castJsonArray.length(); i++) {
            JSONObject jsonobject = castJsonArray.getJSONObject(i);
            Cast postcp=parseCastArray(jsonobject.toString());
            castList.add(postcp);
        }
        post.setCastList(castList);

        //set Crew
        JSONArray crewJsonArray=getJsonCrew(object.getString("credits"));
        List<Crew> crewList =new ArrayList<>();
        for (int i = 0; i < crewJsonArray.length(); i++) {
            JSONObject jsonobject = crewJsonArray.getJSONObject(i);
            Crew postcp=parseCrewArray(jsonobject.toString());
            crewList.add(postcp);
        }
        post.setCrewList(crewList);

        //set videos
        JSONArray videosJsonArray=getJsonVideos(object.getString("videos"));
        List<Video> videoList =new ArrayList<>();
        for (int i = 0; i < videosJsonArray.length(); i++) {
            JSONObject jsonobject = videosJsonArray.getJSONObject(i);
            Video postcp=parseVideosArray(jsonobject.toString());
            videoList.add(postcp);
        }
        post.setVideoList(videoList);

        //set Images backdrops
        if (object.has("images")) {
            JSONArray imagesJsonArray = getJsonImages(object.getString("images"));
            List<Image> imageList =new ArrayList<>();
            for (int i = 0; i < imagesJsonArray.length(); i++) {
                JSONObject jsonobject = imagesJsonArray.getJSONObject(i);
                Image postcp=parseImagesArray(jsonobject.toString());
                imageList.add(postcp);
            }
            post.setImageList(imageList);
        }


        return post;
    }

    private JSONArray getJsonCast(String credits) throws JSONException{
        JSONObject creditsObj=new JSONObject(credits);
        JSONArray castArray=creditsObj.getJSONArray("cast");
        return castArray;
    }

    private Cast parseCastArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        Cast person= new Cast();

        person.setId(object.optInt("id"));
        person.setName(object.getString("name"));
        person.setCharacter(object.getString("character"));
        person.setImagePath(object.getString("profile_path"));

        return person;

    }



    private JSONArray getJsonCrew(String credits) throws JSONException{
        JSONObject creditsObj=new JSONObject(credits);
        JSONArray crewArray=creditsObj.getJSONArray("crew");
        return crewArray;
    }

    private JSONArray getJsonVideos(String videos) throws JSONException{
        JSONObject videosObj=new JSONObject(videos);
        JSONArray videosArray=videosObj.getJSONArray("results");
        return videosArray;
    }

    private Video parseVideosArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        Video movieVideo= new Video();

        movieVideo.setId(object.getString("id"));
        movieVideo.setKey(object.getString("key"));
        movieVideo.setName(object.getString("name"));
        movieVideo.setSite(object.getString("site"));
        movieVideo.setSize(object.optInt("size"));
        movieVideo.setType(object.getString("type"));

        return movieVideo;

    }

    private JSONArray getJsonImages(String images) throws JSONException{
        JSONObject imagesObj=new JSONObject(images);
        JSONArray imagesArray=imagesObj.getJSONArray("backdrops");
        return imagesArray;
    }

    private Image parseImagesArray(String response) throws  JSONException{

        JSONObject object=new JSONObject(response);
        Image movieImage= new Image();

        movieImage.setAspectRatio(object.getLong("aspect_ratio"));
        movieImage.setFilePath(object.getString("file_path"));
        movieImage.setWidth(object.optInt("width"));
        movieImage.setHeight(object.optInt("height"));

        return movieImage;

    }

    private Crew parseCrewArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        Crew person= new Crew();

        person.setId(object.optInt("id"));
        person.setName(object.getString("name"));
        person.setJob(object.getString("job"));
        person.setImagePath(object.getString("profile_path"));

        return person;

    }

    private Genre parseGenresArray(String response) throws JSONException{
        JSONObject object=new JSONObject(response);
        Genre genre= new Genre();

        genre.setId(object.optInt("id"));
        genre.setTitle(object.getString("name"));

        return genre;
    }

    private void failed(int code){
        if(listener!=null)
            listener.failed(code);
    }

    private void success(Movie publication){
        if(listener!= null)
            listener.success(publication);
    }

}

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

import edu.ktu.cinemind.objects.cast;
import edu.ktu.cinemind.objects.crew;
import edu.ktu.cinemind.objects.genreObj;
import edu.ktu.cinemind.objects.image;
import edu.ktu.cinemind.objects.movieObj;
import edu.ktu.cinemind.objects.video;

public class movieDetailsRequestOperator extends Thread {

    public static String apiKey = "a092bd16da64915723b2521295da3254";
    public static String urlToRequest;

    public interface RequestOperatorListener{
        void success(movieObj publication);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    public movieObj publication;

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

    private movieObj request(String url) throws IOException, JSONException {

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        responseCode = con.getResponseCode();
        //System.out.println("Response Code: "+ responseCode);

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

    public movieObj parsingJsonObject(String response) throws JSONException{

        JSONObject object=new JSONObject(response);

        movieObj post = new movieObj();

        post.setId(object.optInt("id"));

        post.setOverview(object.getString("overview"));

        post.setStatus(object.getString("status"));

        post.setPoster_path(object.getString("poster_path"));

        post.setBackdrop_path(object.getString("backdrop_path"));

        post.setTitle(object.getString("title"));

        post.setRelease_date(object.getString("release_date"));

        post.setLength(object.getInt("runtime"));

        JSONArray genresJsonArray =object.getJSONArray("genres");
        List<genreObj> genres =new ArrayList<>();
        for (int i = 0; i < genresJsonArray.length(); i++) {
            JSONObject jsonobject = genresJsonArray.getJSONObject(i);
            genreObj postcp=parseGenresArray(jsonobject.toString());
            genres.add(postcp);
        }
        post.setGenres(genres);

        //set cast
        JSONArray castJsonArray=getJsonCast(object.getString("credits"));
        List<cast> castList =new ArrayList<>();
        for (int i = 0; i < castJsonArray.length(); i++) {
            JSONObject jsonobject = castJsonArray.getJSONObject(i);
            cast postcp=parseCastArray(jsonobject.toString());
            castList.add(postcp);
        }
        post.setCastList(castList);

        //set crew
        JSONArray crewJsonArray=getJsonCrew(object.getString("credits"));
        List<crew> crewList =new ArrayList<>();
        for (int i = 0; i < crewJsonArray.length(); i++) {
            JSONObject jsonobject = crewJsonArray.getJSONObject(i);
            crew postcp=parseCrewArray(jsonobject.toString());
            crewList.add(postcp);
        }
        post.setCrewList(crewList);

        //set videos
        JSONArray videosJsonArray=getJsonVideos(object.getString("videos"));
        List<video> videoList =new ArrayList<>();
        for (int i = 0; i < videosJsonArray.length(); i++) {
            JSONObject jsonobject = videosJsonArray.getJSONObject(i);
            video postcp=parseVideosArray(jsonobject.toString());
            videoList.add(postcp);
        }
        post.setVideoList(videoList);

        //set image
        /*JSONArray imagesJsonArray=getJsonImages(object.getString("images"));
        List<image> imageList =new ArrayList<>();
        for (int i = 0; i < imagesJsonArray.length(); i++) {
            JSONObject jsonobject = imagesJsonArray.getJSONObject(i);
            image postcp=parseImagesArray(jsonobject.toString());
            imageList.add(postcp);
        }
        post.setImageList(imageList);*/

        return post;
    }

    private JSONArray getJsonCast(String credits) throws JSONException{
        JSONObject creditsObj=new JSONObject(credits);
        JSONArray castArray=creditsObj.getJSONArray("cast");
        return castArray;
    }

    private cast parseCastArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        cast person= new cast();

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

    private video parseVideosArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        video movieVideo= new video();

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

    private image parseImagesArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        image movieImage= new image();

        movieImage.setAspectRatio(object.getLong("aspect_ratio"));
        movieImage.setFilePath(object.getString("file_path"));
        movieImage.setWidth(object.optInt("width"));
        movieImage.setHeight(object.optInt("height"));

        return movieImage;

    }

    private crew parseCrewArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        crew person= new crew();

        person.setId(object.optInt("id"));
        person.setName(object.getString("name"));
        person.setJob(object.getString("job"));
        person.setImagePath(object.getString("profile_path"));

        return person;

    }

    private genreObj parseGenresArray(String response) throws JSONException{
        JSONObject object=new JSONObject(response);
        genreObj genre= new genreObj();

        genre.setId(object.optInt("id"));
        genre.setTitle(object.getString("name"));

        return genre;
    }

    private void failed(int code){
        if(listener!=null)
            listener.failed(code);
    }

    private void success(movieObj publication){
        if(listener!= null)
            listener.success(publication);
    }

}

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
import edu.ktu.cinemind.entity.genreObj;
import edu.ktu.cinemind.entity.movieObj;

public class customListMoviesRequestOperator extends Thread {

    public static String apiKey = "a092bd16da64915723b2521295da3254";
    public static String urlToRequest;
    public static List<Integer> movieIds;

    public interface RequestOperatorListener{
        void success(List<movieObj> publications);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    public List<movieObj> publications=new ArrayList<>();

    public void setListener(RequestOperatorListener listener) {
        this.listener = listener;
    }

    @Override
    public void run(){
        super.run();
        try{
            publications=request(movieIds);
            if(!publications.isEmpty())
                success(publications);
            else
                failed(responseCode);
        }catch (IOException e){
            failed(-1);
        } catch (JSONException e){
            failed(-2);
        }
    }

    private List<movieObj> request(List<Integer> movieIds) throws IOException, JSONException {

        List<movieObj> resultList= new ArrayList<>();

        for(int i=0;i<movieIds.size();i++){

            String url ="https://api.themoviedb.org/3/movie/"+movieIds.get(i)+"?api_key=a092bd16da64915723b2521295da3254&append_to_response=credits,videos,image";

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
                resultList.add(parsingJsonObject(response.toString()));
            }
            else
                return null;
        }

        return resultList;
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

        //set image

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
    private Crew parseCrewArray(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        Crew person= new Crew();

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

    private void success(List<movieObj> publications){
        if(listener!= null)
            listener.success(publications);
    }

}
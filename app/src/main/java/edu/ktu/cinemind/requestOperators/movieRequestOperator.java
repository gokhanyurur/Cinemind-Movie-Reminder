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

import edu.ktu.cinemind.entity.Movie;

public class movieRequestOperator extends Thread {

    public static String apiKey = "a092bd16da64915723b2521295da3254";
    public static String urlToRequest;
    //public static int maximumPage;

    public interface RequestOperatorListener{
        void success(List<Movie> publications);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    public List<Movie> publications=new ArrayList<>();

    public void setListener(RequestOperatorListener listener) {
        this.listener = listener;
    }

    @Override
    public void run(){
        super.run();
        try{
            //maximumPage=requestMaxPage(urlToRequest);
            publications=request(urlToRequest);
            if(publications.size()>0)
                success(publications);
            else
                failed(responseCode);
        }catch (IOException e){
            failed(-1);
        } catch (JSONException e){
            failed(-2);
        }
    }

    public static int requestMaxPage(String url) throws IOException, JSONException {

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");

        int responseCode = con.getResponseCode();
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
            return new movieRequestOperator().getMaximumPage(response.toString());
            //return getMaximumPage(response.toString());
        }
        else
            return 0;
    }

    public int getMaximumPage(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        int maxpage = object.optInt("total_pages");
        return maxpage;

    }

    private List<Movie> request(String url) throws IOException, JSONException {

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
            return getResultJsonObj(response.toString());
        }
        else
            return null;
    }

    public List<Movie> getResultJsonObj(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        JSONArray jsonArray =object.getJSONArray("results");

        List<Movie> publicationsCp = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = jsonArray.getJSONObject(i);
            Movie postcp=parsingJsonObject(jsonobject.toString());
            publicationsCp.add(postcp);
        }

        return publicationsCp;

    }

    public Movie parsingJsonObject(String response) throws JSONException{

        JSONObject object=new JSONObject(response);
        Movie post = new Movie();

        // TODO add check before request by the key.
        post.setId(object.optInt("id", 0));

        post.setTitle(object.getString("title"));

        if (object.has("release_date")) {
            post.setReleaseDate(object.getString("release_date"));
        }

        post.setPosterPath(object.getString("poster_path"));

        post.setBackdropPath(object.getString("backdrop_path"));

        post.setOverview(object.getString("overview"));

        post.setVoteAverage(object.getDouble("vote_average"));

        return post;
    }

    private void failed(int code){
        if(listener!=null)
            listener.failed(code);
    }

    private void success(List<Movie> publications){
        if(listener!= null)
            listener.success(publications);
    }

}

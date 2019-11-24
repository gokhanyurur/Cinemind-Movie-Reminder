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

import edu.ktu.cinemind.entity.genreObj;

public class genresRequestOperator extends Thread {

    public static String apiKey = "a092bd16da64915723b2521295da3254";
    public static String urlToRequest;

    public interface RequestOperatorListener{
        void success(List<genreObj> publications);
        void failed(int responseCode);
    }

    private RequestOperatorListener listener;
    private int responseCode;
    public List<genreObj> publications=new ArrayList<>();

    public void setListener(RequestOperatorListener listener) {
        this.listener = listener;
    }

    @Override
    public void run(){
        super.run();
        try{
            publications=request(urlToRequest);
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

    private List<genreObj> request(String url) throws IOException, JSONException {

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
           return getResultJsonObj(response.toString());
        }
        else
            return null;
    }

    public List<genreObj> getResultJsonObj(String response) throws  JSONException{
        JSONObject object=new JSONObject(response);
        JSONArray jsonArray =object.getJSONArray("genres");

        List<genreObj> publicationsCp = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonobject = jsonArray.getJSONObject(i);
            genreObj postcp=parsingJsonObject(jsonobject.toString());
            publicationsCp.add(postcp);
        }

        return publicationsCp;

    }

    public genreObj parsingJsonObject(String response) throws JSONException{

        //attempts to create a json object of achieving a response
        JSONObject object=new JSONObject(response);
        genreObj post = new genreObj();

        post.setId(object.optInt("id", 0));

        post.setTitle(object.getString("name"));

        return post;
    }

    private void failed(int code){
        if(listener!=null)
            listener.failed(code);
    }

    private void success(List<genreObj> publications){
        if(listener!= null)
            listener.success(publications);
    }

}

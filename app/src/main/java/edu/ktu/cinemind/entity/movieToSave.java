package edu.ktu.cinemind.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class movieToSave {
    public String user_id;
    public List<Integer> movie_id=new ArrayList<>();

    public movieToSave(){

    }

    public movieToSave(String user_id, List<Integer> movie_id){
        this.user_id=user_id;
        this.movie_id=movie_id;
    }

    public void setUser_id(String user_id){
        this.user_id=user_id;
    }
    public String getUser_id(){
        return user_id;
    }

    public void setMovie_id(List<Integer> movie_id){
        this.movie_id=movie_id;
    }
    public List<Integer> getMovie_id(){
        return movie_id;
    }

    public static boolean hasContain(List<Integer> movie_ids,int id){
        for(int i=0;i<movie_ids.size();i++){
            if(movie_ids.get(i)==id){
                return true;
            }
        }
        return false;
    }

    public static List<Integer> removeItem(List<Integer> movie_ids, int movie_id){

        /*for(int i=0;i<movie_ids.size();i++){
            if(movie_ids.get(i)==movie_id){
                movie_ids.remove(i);
            }
        }*/

        Iterator<Integer> iter = movie_ids.iterator();
        while (iter.hasNext())
        {
            int id = iter.next();
            if(id==movie_id)
            {
                iter.remove();
            }
        }

        return movie_ids;
    }

}

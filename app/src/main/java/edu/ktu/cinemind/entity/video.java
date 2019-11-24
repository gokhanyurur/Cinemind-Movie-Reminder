package edu.ktu.cinemind.entity;

public class video {

    private String id,iso_639_1,iso_3166_1,key,name,site,type;
    private int size;

    public video(){

    }

    public video(String id,String key,String name,String site,int size,String type){
        this.id=id;
        this.key=key;
        this.name=name;
        this.site=site;
        this.size=size;
        this.type=type;
    }

    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    public void setIso_639_1(String iso_639_1){
        this.iso_639_1=iso_639_1;
    }
    public String getIso_639_1(){
        return iso_639_1;
    }

    public void setIso_3166_1(String iso_3166_1){
        this.iso_3166_1=iso_3166_1;
    }
    public String getIso_3166_1(){
        return iso_3166_1;
    }

    public void setKey(String key){
        this.key=key;
    }
    public String getKey(){
        return key;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }

    public void setSite(String site){
        this.site=site;
    }
    public String getSite(){
        return site;
    }

    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }

    public void setSize(int size){
        this.size=size;
    }
    public int getSize(){
        return size;
    }
}

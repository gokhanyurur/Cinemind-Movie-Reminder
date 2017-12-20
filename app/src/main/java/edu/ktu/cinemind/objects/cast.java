package edu.ktu.cinemind.objects;

public class cast {
    private int cast_id;
    private String character;
    private String credit_id;
    private int gender;
    private int id;
    private String name;
    private int order;
    private String imagePath;

    public cast(){

    }
    public cast(int id,String name, String imagePath, String character){
        this.id=id;
        this.name = name;
        this.imagePath =imagePath;
        this.character = character;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getImagePath(){
        return imagePath;
    }
    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public String getCharacter(){
        return character;
    }
    public void setCharacter(String character){
        this.character = character;
    }


}

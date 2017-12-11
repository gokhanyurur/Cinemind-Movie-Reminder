package edu.ktu.cinemind;


public class genreObj {
    private String title;
    private int imageId,id;

    public genreObj(){

    }

    public genreObj(int id, String title){
        this.id=id;
        this.title=title;
    }

    public genreObj(int id, String title, int imageId){
        this.id=id;
        this.title=title;
        this.imageId=imageId;
    }

    public genreObj(String title, int imageId){
        this.title=title;
        this.imageId=imageId;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    public int getImageId(){
        return imageId;
    }
    public void setImageId(int imageId){
        this.imageId=imageId;
    }


}

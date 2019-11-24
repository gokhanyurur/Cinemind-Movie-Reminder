package edu.ktu.cinemind.entity;

public class image {
    private float aspect_ratio;
    private String file_path;
    private int height,width;


    public image(){

    }
    public image(float aspect_ratio, String file_path,int width,int height){
        this.aspect_ratio=aspect_ratio;
        this.file_path = file_path;
        this.width =width;
        this.height =height;
    }
    public image(String file_path){
        this.file_path = file_path;
    }

    public float getAspectRatio(){
        return aspect_ratio;
    }
    public void setAspectRatio(float aspect_ratio){
        this.aspect_ratio=aspect_ratio;
    }
    
    public String getFilePath(){
        return file_path;
    }
    public void setFilePath(String file_path){
        this.file_path = file_path;
    }

    public int getWidth(){
        return width;
    }
    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return height;
    }
    public void setHeight(int height){
        this.height = height;
    }


}

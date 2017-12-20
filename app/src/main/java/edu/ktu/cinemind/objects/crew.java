package edu.ktu.cinemind.objects;

public class crew {
    private String credit_id;
    private String department;
    private int gender;
    private int id;
    private String job;
    private String name;
    private String imagePath;

    public crew(){

    }
    public crew(int id, String name, String imageId,String department, String job){
        this.id=id;
        this.name = name;
        this.imagePath =imageId;
        this.department=department;
        this.job = job;
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

    public String getDepartment(){
        return department;
    }
    public void setDepartment(String department){
        this.department = department;
    }

    public String getJob(){
        return job;
    }
    public void setJob(String job){
        this.job=job;
    }


}

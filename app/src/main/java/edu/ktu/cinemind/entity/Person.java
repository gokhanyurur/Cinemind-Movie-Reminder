package edu.ktu.cinemind.entity;

/**
 * Super class for Crew and Cast entities.
 */
public class Person {

    /**
     * Id of the person.
     */
    private int id;

    /**
     * Name of the person.
     */
    private String name;

    /**
     * Image path of the person.
     */
    private String imagePath;

    /**
     * Credit id of the person.
     */
    private String credit_id;

    /**
     * Gender of the person.
     */
    private int gender;

    /**
     * @return id.
     */
    public int getId(){
        return id;
    }

    /**
     * @param id to set.
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * @return name of the person.
     */
    public String getName(){
        return name;
    }

    /**
     * @param name to set.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return image path of the person.
     */
    public String getImagePath(){
        return imagePath;
    }

    /**
     * @param imagePath to set.
     */
    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    /**
     * Constructor for person.
     */
    public Person (){
        super();
    }
}

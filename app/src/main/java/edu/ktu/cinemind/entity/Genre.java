package edu.ktu.cinemind.entity;


/**
 * The type Genre.
 */
public class Genre {

    /**
     * The Title.
     */
    private String title;

    /**
     * The Image id.
     */
    private int imageId;

    /**
     * The Id.
     */
    private int id;

    /**
     * Instantiates a new Genre.
     */
    public Genre(){

    }

    /**
     * Instantiates a new Genre.
     *
     * @param id    the id
     * @param title the title
     */
    public Genre(int id, String title){
        this.id=id;
        this.title=title;
    }

    /**
     * Instantiates a new Genre.
     *
     * @param id      the id
     * @param title   the title
     * @param imageId the Image id
     */
    public Genre(int id, String title, int imageId){
        this.id=id;
        this.title=title;
        this.imageId=imageId;
    }

    /**
     * Instantiates a new Genre.
     *
     * @param title   the title
     * @param imageId the Image id
     */
    public Genre(String title, int imageId){
        this.title=title;
        this.imageId=imageId;
    }

    /**
     * Get id int.
     *
     * @return the int
     */
    public int getId(){
        return id;
    }


    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(int id){
        this.id=id;
    }

    /**
     * Get title string.
     *
     * @return the string
     */
    public String getTitle(){
        return title;
    }

    /**
     * Set title.
     *
     * @param title the title
     */
    public void setTitle(String title){
        this.title=title;
    }

    /**
     * Get Image id int.
     *
     * @return the int
     */
    public int getImageId(){
        return imageId;
    }

    /**
     * Set Image id.
     *
     * @param imageId the Image id
     */
    public void setImageId(int imageId){
        this.imageId=imageId;
    }


}

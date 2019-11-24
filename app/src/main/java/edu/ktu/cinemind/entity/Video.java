package edu.ktu.cinemind.entity;

/**
 * The type Video.
 */
public class Video {

    /**
     * The Id.
     */
    private String id;

    /**
     * The Iso 639 1.
     */
    private String iso_639_1;

    /**
     * The Iso 3166 1.
     */
    private String iso_3166_1;

    /**
     * The Key.
     */
    private String key;

    /**
     * The Name.
     */
    private String name;

    /**
     * The Site.
     */
    private String site;

    /**
     * The Type.
     */
    private String type;

    /**
     * The Size.
     */
    private int size;

    /**
     * Instantiates a new Video.
     */
    public Video(){

    }

    /**
     * Instantiates a new Video.
     *
     * @param id   the id
     * @param key  the key
     * @param name the name
     * @param site the site
     * @param size the size
     * @param type the type
     */
    public Video(String id, String key, String name, String site, int size, String type){
        this.id=id;
        this.key=key;
        this.name=name;
        this.site=site;
        this.size=size;
        this.type=type;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(String id){
        this.id=id;
    }

    /**
     * Get id string.
     *
     * @return the string
     */
    public String getId(){
        return id;
    }

    /**
     * Set iso 639 1.
     *
     * @param iso_639_1 the iso 639 1
     */
    public void setIso_639_1(String iso_639_1){
        this.iso_639_1=iso_639_1;
    }

    /**
     * Get iso 639 1 string.
     *
     * @return the string
     */
    public String getIso_639_1(){
        return iso_639_1;
    }

    /**
     * Set iso 3166 1.
     *
     * @param iso_3166_1 the iso 3166 1
     */
    public void setIso_3166_1(String iso_3166_1){
        this.iso_3166_1=iso_3166_1;
    }

    /**
     * Get iso 3166 1 string.
     *
     * @return the string
     */
    public String getIso_3166_1(){
        return iso_3166_1;
    }

    /**
     * Set key.
     *
     * @param key the key
     */
    public void setKey(String key){
        this.key=key;
    }

    /**
     * Get key string.
     *
     * @return the string
     */
    public String getKey(){
        return key;
    }

    /**
     * Set name.
     *
     * @param name the name
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return name;
    }

    /**
     * Set site.
     *
     * @param site the site
     */
    public void setSite(String site){
        this.site=site;
    }

    /**
     * Get site string.
     *
     * @return the string
     */
    public String getSite(){
        return site;
    }

    /**
     * Set type.
     *
     * @param type the type
     */
    public void setType(String type){
        this.type=type;
    }

    /**
     * Get type string.
     *
     * @return the string
     */
    public String getType(){
        return type;
    }

    /**
     * Set size.
     *
     * @param size the size
     */
    public void setSize(int size){
        this.size=size;
    }

    /**
     * Get size int.
     *
     * @return the int
     */
    public int getSize(){
        return size;
    }
}

package edu.ktu.cinemind.entity;

/**
 * The type Cast.
 */
public class Cast extends Person {

    /**
     * The Cast id.
     */
    private int cast_id;

    /**
     * The Character.
     */
    private String character;

    /**
     * The Order.
     */
    private int order;

    /**
     * Instantiates a new Cast.
     */
    public Cast(){

    }

    /**
     * Instantiates a new Cast.
     *
     * @param id        the id
     * @param name      the name
     * @param imagePath the Image path
     * @param character the character
     */
    public Cast(int id, String name, String imagePath, String character){
        setId(id);
        setName(name);
        setImagePath(imagePath);
        this.character = character;
    }

    /**
     * Get character string.
     *
     * @return the string
     */
    public String getCharacter(){
        return character;
    }

    /**
     * Set character.
     *
     * @param character the character
     */
    public void setCharacter(String character){
        this.character = character;
    }


}

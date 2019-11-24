package edu.ktu.cinemind.entity;

/**
 * The type Image.
 */
public class Image {

    /**
     * The Aspect ratio.
     */
    private float aspectRatio;

    /**
     * The File path.
     */
    private String filePath;

    /**
     * The Height.
     */
    private int height;

    /**
     * The Width.
     */
    private int width;

    /**
     * Instantiates a new Image.
     */
    public Image(){

    }

    /**
     * Instantiates a new Image.
     *
     * @param aspectRatio the aspect ratio
     * @param filePath    the file path
     * @param width        the width
     * @param height       the height
     */
    public Image(float aspectRatio, String filePath, int width, int height){
        this.aspectRatio = aspectRatio;
        this.filePath = filePath;
        this.width =width;
        this.height =height;
    }

    /**
     * Instantiates a new Image.
     *
     * @param filePath the file path
     */
    public Image(String filePath){
        this.filePath = filePath;
    }

    /**
     * Get aspect ratio float.
     *
     * @return the float
     */
    public float getAspectRatio(){
        return aspectRatio;
    }

    /**
     * Set aspect ratio.
     *
     * @param aspect_ratio the aspect ratio
     */
    public void setAspectRatio(float aspect_ratio){
        this.aspectRatio =aspect_ratio;
    }

    /**
     * Get file path string.
     *
     * @return the string
     */
    public String getFilePath(){
        return filePath;
    }

    /**
     * Set file path.
     *
     * @param file_path the file path
     */
    public void setFilePath(String file_path){
        this.filePath = file_path;
    }

    /**
     * Get width int.
     *
     * @return the int
     */
    public int getWidth(){
        return width;
    }

    /**
     * Set width.
     *
     * @param width the width
     */
    public void setWidth(int width){
        this.width = width;
    }

    /**
     * Get height int.
     *
     * @return the int
     */
    public int getHeight(){
        return height;
    }

    /**
     * Set height.
     *
     * @param height the height
     */
    public void setHeight(int height){
        this.height = height;
    }


}

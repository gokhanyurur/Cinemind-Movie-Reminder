package edu.ktu.cinemind.entity;

/**
 * The type Crew.
 */
public class Crew extends Person {

    /**
     * The Department.
     */
    private String department;

    /**
     * The Job.
     */
    private String job;

    /**
     * Instantiates a new Crew.
     */
    public Crew(){

    }

    /**
     * Instantiates a new Crew.
     *
     * @param id         the id
     * @param name       the name
     * @param imageId    the Image id
     * @param department the department
     * @param job        the job
     */
    public Crew(int id, String name, String imageId, String department, String job){
        setId(id);
        setName(name);
        setImagePath(imageId);
        this.department=department;
        this.job = job;
    }

    /**
     * Get department string.
     *
     * @return the string
     */
    public String getDepartment(){
        return department;
    }

    /**
     * Set department.
     *
     * @param department the department
     */
    public void setDepartment(String department){
        this.department = department;
    }

    /**
     * Get job string.
     *
     * @return the string
     */
    public String getJob(){
        return job;
    }

    /**
     * Set job.
     *
     * @param job the job
     */
    public void setJob(String job){
        this.job=job;
    }


}

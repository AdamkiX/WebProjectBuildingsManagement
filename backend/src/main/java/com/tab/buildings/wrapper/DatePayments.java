package com.tab.buildings.wrapper;

/**
 * Class containst
 * - apt_number - appartment number
 * - sumForAppartment - sum of rent amount for period of time
 */
public class DatePayments {
    private Integer apt_number;
    private Double sumForAppartment;

    /**
     * Constructor for two parameters
     * @param apt_number
     * @param sumForAppartment
     */
    public DatePayments(Integer apt_number,Double sumForAppartment)
    {
        this.apt_number=apt_number;
        this.sumForAppartment = sumForAppartment;
    }

    /**
     * Constructor with no parameters
     */
    public DatePayments()
    {
        this.apt_number = null;
        this.sumForAppartment = null;
    }

    /**
     * getter of apt_number
     * @return pt_number
     */
    public Integer getApt_number()
    {
        return this.apt_number;
    }

    /**
     * getter of sumForAppartment
     * @return sumForAppartment
     */
    public Double getSumForAppartment()
    {
        return this.sumForAppartment;
    }

    /**
     * setter of apt_number
     * @param apt_number variable for set
     */
    public void setApt_number(Integer apt_number)
    {
        this.apt_number = apt_number;
    }

    /**
     * setter of sumForAppartment
     * @param sumForAppartment variable for set
     */
    public void setSumForAppartment(Double sumForAppartment)
    {
        this.sumForAppartment = sumForAppartment;
    }
}

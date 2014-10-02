package de.kosmalla.sandra.sample.wicket.webapp.domain;

import java.io.Serializable;


public class Person implements Serializable{


    /**  */
    private static final long serialVersionUID = -2578924430376271858L;

    private String firstName;

    private String lastName;

    public Person(){

    }

    public Person(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return this.firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return this.lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}

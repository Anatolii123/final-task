package org.lanit.internship.finaltask.entities;

import java.util.ArrayList;

public class PersonWithCars {

    private Person person;
    private ArrayList<Car> personsCars;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ArrayList<Car> getPersonsCars() {
        return personsCars;
    }

    public void setPersonsCars(ArrayList<Car> personsCars) {
        this.personsCars = personsCars;
    }
}

package org.lanit.internship.finaltask.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table
@ToString(of = {"id","name","birthdate"})
@EqualsAndHashCode(of = {"id"})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false, precision = 0)
    private Long id;
    @Column(name = "NAME", nullable = false, length = 45)
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "BIRTH_DATE", nullable = false)
    private Date birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (birthDate != null ? !birthDate.equals(person.birthDate) : person.birthDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        return result;
    }
}
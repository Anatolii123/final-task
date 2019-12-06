package org.lanit.internship.finaltask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Table
@ToString(of = {"id","model","horsepower","ownerId"})
@EqualsAndHashCode(of = {"id"})
public class Car {
    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    private Long id;
    @Column(name = "MODEL", nullable = false, precision = 0)
    private String model;
    @Column(name = "HORSEPOWER", nullable = false, precision = 0)
    private Integer horsepower;
    @Column(name = "OWNER_ID", nullable = false, precision = 0)
    private Long ownerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getHorsepower() {
        return horsepower;
    }

    public void setHorsepower(Integer horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (id != car.id) return false;
        if (horsepower != car.horsepower) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (int) (horsepower ^ (horsepower >>> 32));
        return result;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Transient
    @JsonIgnore
    public String getVendorModel() {
        String vendor = this.model.substring(0,this.model.indexOf("-"));
        return vendor;
    }

    @Transient
    @JsonIgnore
    public String getModelModel() {
        String model = this.model.substring(this.model.indexOf("-") + 1, this.model.length());
        return model;
    }

}
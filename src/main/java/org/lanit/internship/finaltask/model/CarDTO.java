package org.lanit.internship.finaltask.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.util.Date;

public class CarDTO {
    private String id;
    private String model;
    private String horsepower;
    private String ownerId;

    public String getVendorModel() {
        String vendor = this.model.substring(0,this.model.indexOf("-"));
        return vendor;
    }

    public String getModelModel() {
        String model = this.model.substring(this.model.indexOf("-") + 1, this.model.length());
        return model;
    }

    public Car toCar() {
        if (this.id == null || this.id.equals("") || this.model == null || this.model.equals("") ||
                !this.model.contains("-") || getVendorModel().equals("") || getModelModel().equals("") ||
                this.horsepower == null || this.ownerId == null) {
            return null;
        }
        Car car = new Car();
        try {
            car.setId(Long.valueOf(this.id));
            car.setHorsepower(Integer.parseInt(this.horsepower));
            car.setOwnerId(Long.valueOf(this.ownerId));
        } catch (NumberFormatException n) {
            return null;
        }
        car.setModel(this.model);
        return car;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getHorsepower() {
        return horsepower;
    }

    public String getOwnerId() {
        return ownerId;
    }
}

package org.lanit.internship.finaltask.model;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PersonDTO {
    private String id;
    private String name;
    private String birthdate;
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public PersonDTO fromPerson(Person person) {
        this.id = person.getId().toString();
        this.name = person.getName() != null ? person.getName() : "";
        this.birthdate = sdf.format(person.getBirthdate());
        return this;
    }

    /**
     * @return если данные в каком-то месте не валидны,то возвращаем null
     */
    public Person toPerson() throws ParseException {
        //тут все валидации, включаяя пустоту id и name == null, а также невалидную дату
        try {
            sdf.parse(this.birthdate);
        } catch (ParseException p) {
            return null;
        }
        if (this.id == null || this.id.equals("") || this.name == null || this.birthdate == null ||
                Integer.parseInt(this.birthdate.substring(0, 1)) > 31 ||
                Integer.parseInt(this.birthdate.substring(3, 4)) > 12 ||
                !(sdf.parse(this.birthdate).before(new Date()))) {
            return null;
        }
        Person person = new Person();
        try {
            person.setId(Long.valueOf(this.id));
        } catch (NumberFormatException n) {
            person.setId(null);
        }
        person.setName(this.name);
        person.setBirthdate(DateUtils.addHours(sdf.parse(this.birthdate), 5));
        return person;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthdate() {
        return birthdate;
    }
}

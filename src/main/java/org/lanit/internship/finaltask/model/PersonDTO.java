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
    public Person toPerson() throws ParseException, NullPointerException {
        //тут все валидации, включаяя пустоту id и name == null, а также невалидную дату
        if (this.id == null || this.id.equals("") || this.name == null || this.birthdate == null ||
                !this.birthdate.matches("(0[1-9]|[12][0-9]|3[01])[\\.](0[1-9]|1[012])[\\.](19|20)\\d\\d") ||
                Integer.parseInt(this.birthdate.substring(0, 2)) > 31 ||
                Integer.parseInt(this.birthdate.substring(3, 5)) > 12 ||
                !(sdf.parse(this.birthdate).before(new Date()))) {
            return null;
        }
        Person person = new Person();
        try {
            person.setId(Long.valueOf(this.id));
            person.setBirthdate(DateUtils.addHours(sdf.parse(this.birthdate), 5));
        } catch (NumberFormatException n) {
            return null;
        } catch (NullPointerException n) {
            return null;
        }
        person.setName(this.name);
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

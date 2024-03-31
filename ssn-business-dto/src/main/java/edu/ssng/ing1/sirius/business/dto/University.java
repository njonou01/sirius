package edu.ssng.ing1.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class University {
    private Integer id_university;
    private String label;
    private String shortname;
    private String acronym;

    public final University build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id_university", "label", "shortname",
                "acronym");
        return this;
    }

    public University() {
    }

    public University(Integer id_university, String label, String shortname, String acronym,
            String typeof_establishment, String location, String website, String department_code, String region_code,
            String place_called) {
        this.id_university = id_university;
        this.label = label;
        this.shortname = shortname;
        this.acronym = acronym;
    }

    public Integer getId_university() {
        return this.id_university;
    }

    public void setId_university(Integer id_university) {
        this.id_university = id_university;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getShortname() {
        return this.shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }



    public University id_university(Integer id_university) {
        setId_university(id_university);
        return this;
    }

    public University label(String label) {
        setLabel(label);
        return this;
    }

    public University shortname(String shortname) {
        setShortname(shortname);
        return this;
    }

    public University acronym(String acronym) {
        setAcronym(acronym);
        return this;
    }

   

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof University)) {
            return false;
        }
        University university = (University) o;
        return Objects.equals(id_university, university.id_university) && Objects.equals(label, university.label)
                && Objects.equals(shortname, university.shortname) && Objects.equals(acronym, university.acronym);
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }



    @Override
    public int hashCode() {
        return Objects.hash(id_university, label, shortname, acronym);
    }

    @Override
    public String toString() {
        return "{" +
                " id_university='" + getId_university() + "'" +
                ", label='" + getLabel() + "'" +
                ", shortname='" + getShortname() + "'" +
                ", acronym='" + getAcronym() + "'" +
                "}";
    }

}
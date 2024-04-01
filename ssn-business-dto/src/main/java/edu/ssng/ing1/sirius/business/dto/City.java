package edu.ssng.ing1.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class City {
    private int zipcode;
    private String city_name;
    private int id_city;

    public City(int zipcode, String city_name, int id_city) {
        this.zipcode = zipcode;
        this.city_name = city_name;
        this.id_city = id_city;
    }

    public City() {
    }

    public int getId_city() {
        return id_city;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public int getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public City zipcode(int zipcode) {
        setZipcode(zipcode);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zipcode, city_name, id_city);
    }

    public City city_name(String city_name) {
        setCity_name(city_name);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof City)) {
            return false;
        }
        City city = (City) o;
        return zipcode == city.zipcode && Objects.equals(city_name, city.city_name) && id_city == city.id_city;
    }

    public final City build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id_city", "zipcode", "city_name");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {

        return (buildPreparedStatement(preparedStatement, id_city, zipcode, city_name));
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement,
            final int id_city, final int zipcode, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        preparedStatement.setInt(++ix, zipcode);
        for (final String fieldName : fieldNames) {
            preparedStatement.setString(++ix, fieldName);
        }
        return preparedStatement;
    }

}

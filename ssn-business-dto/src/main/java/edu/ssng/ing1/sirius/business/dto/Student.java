package edu.ssng.ing1.sirius.business.dto;

import org.mindrot.jbcrypt.BCrypt;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;


import java.util.Objects;

public class Student {
    public String firstname;
    public String familyname;
    public String email;
    public String gender;
    public String password;
    public Date bithday;
    public String phoneNumber;
    public String profileImage;
    public String username ;

    public Student(String firstname, String familyname, String email, String gender, String password, Date bithday, String phoneNumber, String profileImage, String username) {
        this.firstname = firstname;
        this.familyname = familyname;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.bithday = bithday;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.username = username;
    }

    public Student(String password, String username) {
        this.password = password;
        this.username = username;
    }


    public Student() {
    }

    public final Student build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet,  "familyname","firstname",
                "email", "phoneNumber", "gender", "username", "password", "bithday");
        return this;
    }
    public final int signin(PreparedStatement preparedStatement)
            throws NoSuchFieldException, IllegalAccessException, SQLException {
        return build(preparedStatement).executeUpdate();
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {

        return buildPreparedStatement(preparedStatement, bithday , familyname, firstname,
                email, phoneNumber, gender,username,
                BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFamilyname() {
        return this.familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBithday() {
        return this.bithday;
    }

    public Student firstname(String firstname) {
        setFirstname(firstname);
        return this;
    }

    public Student familyname(String familyname) {
        setFamilyname(familyname);
        return this;
    }

    public Student email(String email) {
        setEmail(email);
        return this;
    }

    public Student gender(String gender) {
        setGender(gender);
        return this;
    }

    public Student password(String password) {
        setPassword(password);
        return this;
    }

    public Student bithday(Date bithday) {
        setBithday(bithday);
        return this;
    }

    public Student phoneNumber(String phoneNumber) {
        setPhoneNumber(phoneNumber);
        return this;
    }

    public void setBithday(Date bithday) {
        this.bithday = bithday;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return this.profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement,
    final Date date ,final String... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final String fieldName : fieldNames) {
            preparedStatement.setString(++ix, fieldName);
        }
        preparedStatement.setDate(++ix, date);
        return preparedStatement;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Student)) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(firstname, student.firstname) && Objects.equals(familyname, student.familyname)
                && Objects.equals(email, student.email) && Objects.equals(gender, student.gender)
                && Objects.equals(password, student.password) && Objects.equals(bithday, student.bithday)
                && Objects.equals(phoneNumber, student.phoneNumber);
    }

    @Override
    public String toString() {
        return "{" +
                " firstname='" + getFirstname() + "'" +
                ", familyname='" + getFamilyname() + "'" +
                ", email='" + getEmail() + "'" +
                ", gender='" + getGender() + "'" +
                ", password='" + getPassword() + "'" +
                ", bithday='" + getBithday() + "'" +
                ", phoneNumber='" + getPhoneNumber() + "'" +
                ", profileImage='" + getProfileImage() + "'" +
                "}";
    }

}
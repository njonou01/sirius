package edu.ssng.ing1.sirius.business.dto;

import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class Student {
    // sign up start -> ok
    private int id_student;
    private String firstname;
    private String familyname;
    private String gender;
    private Date bithday;
    private String phoneNumber;
    private int zipcode;
    private String adress;

    // sign up start-> ok
    private String email;
    private String password;

    // université - schoolcontroller -> ok
    private String university;
    private Date formation_start;
    private Date formation_stop;
    private String formation_description;
    private String training_followed;

    private String profileImage;
    private byte[] profileImageStream;


    public Student() {

    }


    public Student(String email) {
        this.email = email;
    }

    public Student(int id_student) {
        this.id_student = id_student;
    }

    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Student(String firstname, String familyname, String email, String gender, String password, Date bithday,
            String phoneNumber, String profileImage, byte[] profileImageStream, int id_student) {
        this.firstname = firstname;
        this.familyname = familyname;
        this.email = email;
        this.gender = gender;
        this.password = password;
        this.bithday = bithday;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.profileImageStream = profileImageStream;
        this.id_student = id_student;
      
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
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

    public byte[] getProfileImageStream() {
        return this.profileImageStream;
    }

    public void setProfileImageStream(byte[] profileImageStream) {
        this.profileImageStream = profileImageStream;
    }

    public int getId_student() {
        return this.id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
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

    public Student profileImage(String profileImage) {
        setProfileImage(profileImage);
        return this;
    }

    public Student profileImageStream(byte[] profileImageStream) {
        setProfileImageStream(profileImageStream);
        return this;
    }

    public Student id_student(int id_student) {
        setId_student(id_student);
        return this;
    }

    public int getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getAdress() {
        return this.adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getUniversity() {
        return this.university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public Date getFormation_start() {
        return this.formation_start;
    }

    public void setFormation_start(Date formation_start) {
        this.formation_start = formation_start;
    }

    public Date getFormation_stop() {
        return this.formation_stop;
    }

    public void setFormation_stop(Date formation_stop) {
        this.formation_stop = formation_stop;
    }

    public String getFormation_description() {
        return this.formation_description;
    }

    public void setFormation_description(String formation_description) {
        this.formation_description = formation_description;
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
                && Objects.equals(phoneNumber, student.phoneNumber)
                && Objects.equals(profileImageStream, student.profileImageStream) && id_student == student.id_student;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, familyname, email, gender, password, bithday, phoneNumber, profileImage,
                profileImageStream, id_student);
    }

    @Override
    public String toString() {
        return "Student [firstname=" + firstname + ", familyname=" + familyname + ", gender=" + gender + ", bithday="
                + bithday + ", phoneNumber=" + phoneNumber + ", zipcode=" + zipcode + ", adress=" + adress + ", email="
                + email + ", password=" + password + ", university=" + university + ", formation_start="
                + formation_start
                + ", formation_stop= " + formation_stop + ", formation_description=" + formation_description
                + ", training_followed= " + training_followed + ", profileImage=" + profileImage
                + ", profileImageStream= " + Arrays.toString(profileImageStream) + ", id_student=" + id_student + "]";
               
               
    }

    public final Student build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id_student", "familyname", "firstname",
                "email", "phoneNumber", "gender", "bithday");
        return this;
    }

    public final void buildUniversity(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "university",
                "formation_start",
                "formation_stop",
                "formation_description",
                "training_followed");
    }

    public final int signin(PreparedStatement preparedStatement)
            throws NoSuchFieldException, IllegalAccessException, SQLException {
        return build(preparedStatement).executeUpdate();
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {

        return createStudentStatement(preparedStatement,
                familyname, firstname, email, bithday, phoneNumber,
                gender, password, zipcode, adress, university,
                formation_start, formation_stop, formation_description, training_followed);
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement createStudentStatement(PreparedStatement preparedStatement,
            final Object... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final Object fieldName : fieldNames) {
            if (fieldName instanceof java.sql.Date) {
                preparedStatement.setDate(++ix, (java.sql.Date) fieldName);
            } else if (fieldName instanceof Date) {
                preparedStatement.setDate(++ix, (Date) fieldName);
            } else if (fieldName instanceof Integer) {
                preparedStatement.setInt(++ix, (Integer) fieldName);
            } else if (fieldName instanceof String) {
                preparedStatement.setString(++ix, (String) fieldName);
            }
        }
        return preparedStatement;
    }

    public final PreparedStatement setUniversityStudentStatement(PreparedStatement preparedStatement,
            final int is_student, final int id_university, final String description, final Date... dates)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final Date fieldName : dates) {
            preparedStatement.setDate(++ix, fieldName);
        }
        preparedStatement.setString(++ix, description);
        preparedStatement.setInt(++ix, is_student);
        preparedStatement.setInt(++ix, id_university);
        return preparedStatement;
    }

    public String getTraining_followed() {
        return training_followed;
    }

    public void setTraining_followed(String training_followed) {
        this.training_followed = training_followed;
    }

}
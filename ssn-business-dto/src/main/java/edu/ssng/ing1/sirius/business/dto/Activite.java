package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Set;

@JsonRootName(value = "activite")
public class Activite {
    private  String datecreation;
    private  String datedebut;
    private  String datefin;
    private  String nom_interet_activite;
    private  String libelle;
    private  String categorie;
    private  String provenance;
    private  String confidentialite;
    private  String nomCreateur;
    private  Integer id_student;
    private  Integer nbrparticipant;
    private  Boolean state;
    private  Set<Student> students;
    private  String emailCreateur;
    
    


    public Activite() {
    }
    public final Activite build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "datecreation","datedebut","datefin", "nom_interet_activite","libelle","categorie","provenance","confidentialite","nomCreateur","id_student","nbrparticipant","state");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
                // int ix = 0;
                // preparedStatement.setInt(++ix, id);
        return buildPreparedStatement(preparedStatement, nom_interet_activite,libelle,categorie,provenance,confidentialite,nomCreateur);
    }
    public Activite(String datecreation, String datedebut,String datefin, String nom_interet_activite,String libelle, String categorie,String provenance,String confidentialite,String nomCreateur,Integer id_student, Integer nbrparticipant, Boolean state, Set<Student> students, String emailCreateur) {
        this.datecreation = datecreation;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.nom_interet_activite=nom_interet_activite;
        this.libelle=libelle;
        this.categorie=categorie;
        this.provenance=provenance;
        this.nomCreateur=nomCreateur;
        this.id_student=id_student;
        this.nbrparticipant=nbrparticipant;
        this.state=state;
        this.students=students;
        this.emailCreateur=emailCreateur;
        
    }




    public String getCategorie() {
        return categorie;
    }

    public String getEmailCreateur() {
        return emailCreateur;
    }
    public void setEmailCreateur(String emailCreateur) {
        this.emailCreateur = emailCreateur;
    }
    
    public String getConfidentialite() {
        return confidentialite;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public String getDatedebut() {
        return datedebut;
    }

    public String getDatefin() {
        return datefin;
    }

    public Integer getId_student() {
        return id_student;
    }

    public String getLibelle() {
        return libelle;
    }

    public Integer getNbrparticipant() {
        return nbrparticipant;
    }

    public String getNomCreateur() {
        return nomCreateur;
    }

    public String getNom_interet_activite() {
        return nom_interet_activite;
    }

    public String getProvenance() {
        return provenance;
    }
    public Boolean getState() {
        return state;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }





    public void setNbrparticipant(Integer nbrparticipant) {
        this.nbrparticipant = nbrparticipant;
    }

    public void setNomCreateur(String nomCreateur) {
        this.nomCreateur = nomCreateur;
    }

    public void setNom_interet_activite(String nom_interet_activite) {
        this.nom_interet_activite = nom_interet_activite;
    }
    public void setProvenance(String provenance) {
        this.provenance = provenance;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public void setConfidentialite(String confidentialite) {
        this.confidentialite = confidentialite;
    }
    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }
    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }
    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }
    public void setId_student(Integer id_student) {
        this.id_student = id_student;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public void setState(Boolean state) {
        this.state = state;
    }

    

    private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName : fieldNames ) {

            if (fieldName.equals("id_student") || fieldName.equals("nbrparticipant")) {
                final Field field = this.getClass().getDeclaredField(fieldName);
                field.set(this, resultSet.getObject(fieldName, Integer.class));
            

            }else if(fieldName.equals("datecreation") || fieldName.equals("datefin") || fieldName.equals("datedebut") ){
                final Field field = this.getClass().getDeclaredField(fieldName);
                Timestamp timestampValue = resultSet.getTimestamp(fieldName); 
                String timestampAsString = timestampValue.toString(); 
                field.set(this, timestampAsString); 

            }else {
                final Field field = this.getClass().getDeclaredField(fieldName);
                field.set(this, resultSet.getObject(fieldName));
            }
            // final Field field = this.getClass().getDeclaredField(fieldName);
            // field.set(this, resultSet.getObject(fieldName));
        }
    }
    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;


        Timestamp timestamp = Timestamp.valueOf(datecreation);
        preparedStatement.setTimestamp(++ix, timestamp);

        timestamp = Timestamp.valueOf(datedebut);
        preparedStatement.setTimestamp(++ix, timestamp);

        timestamp = Timestamp.valueOf(datefin);
        preparedStatement.setTimestamp(++ix, timestamp);
        
        for(final String fieldName : fieldNames ) {
            preparedStatement.setString(++ix, fieldName);
        }
        
        preparedStatement.setInt(++ix, id_student);
        preparedStatement.setInt(++ix, nbrparticipant);
        preparedStatement.setBoolean(++ix, state);
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "datecreation='" + datecreation + '\'' +
                ", datedebut='" + datedebut + '\'' +
                ", datefin='" + datefin + '\'' +
                ",nom_interet_activite='" + nom_interet_activite + '\'' +
                ", libelle='" + libelle + '\'' +
                ", categorie='" + categorie + '\'' +
                ", provenance='" + provenance + '\'' +
                ", confidentialite='" + confidentialite + '\'' +
                ", nomCreateur='" + nomCreateur + '\'' +
                ", id_student='" + id_student + '\'' +
                ", nbrparticipant='" + nbrparticipant + '\'' +
                ", state='" + state + '\'' +
                
                '}';
    }
}
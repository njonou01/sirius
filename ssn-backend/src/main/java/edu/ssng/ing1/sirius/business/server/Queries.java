package edu.ssng.ing1.sirius.business.server;

public enum Queries {
        SELECT_ALL_ACTIVITY("SELECT t.datecreation, t.datedebut, t.datefin, t.nom_interet_activite, t.libelle, t.categorie, t.provenance, t.confidentialite, t.nomcreateur, t.id_student, t.nbrparticipant, t.state FROM \"ssn-db-ing1\".Activite t"),
        SELECT_MY_ACTIVITY("SELECT t.datecreation, t.datedebut, t.datefin, t.nom_interet_activite, t.libelle, t.categorie, t.provenance, t.confidentialite, t.nomcreateur, t.id_student, t.nbrparticipant, t.state FROM \"ssn-db-ing1\".Activite t WHERE t.id_student = ?"),
        ACTIVITY_INVITATION("INSERT INTO \"ssn-db-ing1\".activityinvitation (sender, receiver) VALUES (?, ?)"),
        RESPONSE_UPDATE("UPDATE ssn-db-ing1.activityinvitation\n" + 
                                "SET state = ?\n" + 
                                "WHERE sender = ? AND receiver = ?"),
        INSERT_ACTIVITY("INSERT into \"ssn-db-ing1\".Activite (\"datecreation\", \"datedebut\", \"datefin\", \"nom_interet_activite\", \"libelle\", \"categorie\", \"provenance\", \"confidentialite\", \"nomcreateur\", \"id_student\",\"nbrparticipant\" , \"state\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        ALREADY_ACTIVITY("SELECT DISTINCT s.id_student, s.nom, s.prenom " +
                "FROM \"ssn-db-ing1\".student s " +
                "JOIN \"ssn-db-ing1\".participationactivite pa ON s.id_student = pa.id_student " +
                "JOIN \"ssn-db-ing1\".participationactivite pa2 ON pa.idactivite = pa2.idactivite " +
                "JOIN \"ssn-db-ing1\".activite a ON pa.idactivite = a.idactivite " +
                "WHERE pa2.id_student = ? " +
                "AND pa.dateparticipation < CURRENT_DATE " +
                "AND a.state = false"),
        ACTIVITY_PARTICIPATION("SELECT s.id_student, s.nom, s.prenom " +
                "FROM \"ssn-db-ing1\".student s " +
                "JOIN \"ssn-db-ing1\".participationactivite pa ON s.id_student = pa.id_student " +
                "WHERE pa.idactivite = ?"),
        OVER_ACTIVITY("UPDATE \"ssn-db-ing1\".activite SET state = false WHERE idactivite = ?"),
        DELETE_PARTICIPATION("DELETE FROM \"ssn-db-ing1\".participationactivite WHERE id_student = ? AND idactivite = ?"),
        SELECT_ALL_CITIES("SELECT t.id_city , t.zipcode , t.city_name FROM \"ssn-db-ing1\".city t"),
        SELECT_ALL_STUDENTS(
                        "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                                        "\tFROM \"ssn-db-ing1\".student"),
        DISCONNECTION_STUDENT(
                        "La requete de mis à jour ici"),
        STUDENT_INFO(
                        "SELECT student.id_student, student.familly_name as familyname , student.first_name as firstname, student.email as email, student.phone_number as phoneNumber, student.gender as gender,student.profile_image as profile_image , student.password as password , student.birthday as bithday\n"
                                        + //
                                        "FROM \"ssn-db-ing1\".student student where email = ?"),
        SELECT_FRIENDS_FOR_CONNEXION(
                "SELECT \n" +
                "distinct (email)\n" +
                "FROM \n" +
                "    \"ssn-db-ing1\".student s\n" +
                "JOIN \n" +
                "    \"ssn-db-ing1\".befriend b ON s.id_student = b.sender OR s.id_student = b.receiver\n" +
                "WHERE \n" +
                "    (b.sender IN (SELECT id_student FROM \"ssn-db-ing1\".student WHERE email = ? )\n"
                +
                "    OR b.receiver IN (SELECT id_student FROM \"ssn-db-ing1\".student WHERE email = ? ))\n"
                +
                "    AND b.status = 'accepted'\n" +
                "    AND (b.end_relation_at IS NULL OR b.end_relation_at > now())\n" +
                "\tAND email <> ? "),
        SELECT_FRIENDS("SELECT DISTINCT ON (STUDENT.EMAIL)\n" +
                        "    STUDENT.FAMILLY_NAME AS FAMILYNAME,\n" +
                        "    STUDENT.FIRST_NAME AS FIRSTNAME,\n" +
                        "    STUDENT.EMAIL AS EMAIL,\n" +
                        "    STUDENT.PHONE_NUMBER AS PHONENUMBER,\n" +
                        "    STUDENT.GENDER AS GENDER,\n" +
                        "    STUDENT.USERNAME AS USERNAME,\n" +
                        "    STUDENT.PROFILE_IMAGE AS PROFILE_IMAGE,\n" +
                        "    STUDENT.PASSWORD AS PASSWORD,\n" +
                        "    STUDENT.BIRTHDAY AS BITHDAY,\n" +
                        "    BEFRIEND.STATUS,\n" +
                        "    BEFRIEND.BEFRIEND_SINCE,\n" +
                        "    BEFRIEND.RECEIVED_AT,\n" +
                        "    BEFRIEND.END_RELATION_AT,\n" +
                        "    UNIVERSITY.LABEL AS UNIVERSITY,\n" +
                        "    ATTENDED.START AS FORMATION_START,\n" +
                        "    ATTENDED.END AS FORMATION_STOP,\n" +
                        "    ATTENDED.DESCRIPTION AS FORMATION_DESCRIPTION,\n" +
                        "    ATTENDED.TRAINING_FOLLOWED \n" +
                        "FROM\n" +
                        "    \"ssn-db-ing1\".BEFRIEND AS BEFRIEND\n" +
                        "    INNER JOIN \"ssn-db-ing1\".STUDENT AS STUDENT ON BEFRIEND.SENDER = STUDENT.ID_STUDENT\n" +
                        "    INNER JOIN \"ssn-db-ing1\".UNIVERSITY AS UNIVERSITY ON STUDENT.ID_STUDENT = UNIVERSITY.ID_UNIVERSITY\n"
                        +
                        "    INNER JOIN (\n" +
                        "        SELECT ID_STUDENT, MAX(START) AS MAX_START\n" +
                        "        FROM \"ssn-db-ing1\".ATTENDED\n" +
                        "        GROUP BY ID_STUDENT\n" +
                        "    ) AS MAX_START_ATTENDED ON STUDENT.ID_STUDENT = MAX_START_ATTENDED.ID_STUDENT\n" +
                        "    INNER JOIN \"ssn-db-ing1\".ATTENDED ON STUDENT.ID_STUDENT = ATTENDED.ID_STUDENT AND ATTENDED.START = MAX_START_ATTENDED.MAX_START\n"
                        +
                        "WHERE\n" +
                        "    BEFRIEND.RECEIVER = ?\n" +
                        "    AND ATTENDED.END > CURRENT_DATE;\n" +
                        ""),

        SELECT_FRIEND_REQUEST_WITHOUT_ANSWER(
                        "SELECT student.familly_name, student.first_name, student.email, student.phone_number, student.gender, student.username,student.profile_image\n"
                                        + //
                                        "\tFROM \"ssn-db-ing1\".befriend as befriend inner join \"ssn-db-ing1\".student as student on befriend.receiver = student.id_student where befriend.sender=2 and befriend.status = 'no reponse'"),
        SELECT_ALL_UNIVERSITIES("SELECT t.id_university, t.label, t.shortname, t.acronym\n" + //
                        "\tFROM \"ssn-db-ing1\".university t ;"),
        DOES_STUDENT_EXIST(
                        "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                                        "\tFROM \"ssn-db-ing1\".student where email = ? "),
        INSERT_STUDENT(
                        "INSERT INTO \"ssn-db-ing1\".student (familly_name, first_name, email, phone_number, gender, username, \"password\", birthday) VALUES(?, ?, ?, ?, ?, ?, ? , ?)"),
        SIGN_IN_AS(

                "SELECT  password FROM \"ssn-db-ing1\".student where email=?"),

        GET_DATA_CONNEXION("SELECT * FROM \"ssn-db-ing1\".student where email=?");




        private final String query;

        private Queries(final String query) {
                this.query = query;
        }

        public String getQuery() {
                return query;
        }
}
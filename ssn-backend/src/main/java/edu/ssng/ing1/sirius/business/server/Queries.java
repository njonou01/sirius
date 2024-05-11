package edu.ssng.ing1.sirius.business.server;

enum Queries {
        SELECT_ALL_ACTIVITY(
                        "SELECT t.datecreation, t.datedebut, t.datefin, t.nom_interet_activite, t.libelle, t.categorie, t.provenance, t.confidentialite, t.nomcreateur, t.id_student, t.nbrparticipant, t.state FROM \"ssn-db-ing1\".Activite t"),
        SELECT_MY_ACTIVITY(
                        "SELECT t.datecreation, t.datedebut, t.datefin, t.nom_interet_activite, t.libelle, t.categorie, t.provenance, t.confidentialite, t.nomcreateur, t.id_student, t.nbrparticipant, t.state FROM \"ssn-db-ing1\".Activite t WHERE t.id_student = ?"),
        INSERT_ACTIVITY("INSERT into \"ssn-db-ing1\".Activite (\"datecreation\", \"datedebut\", \"datefin\", \"nom_interet_activite\", \"libelle\", \"categorie\", \"provenance\", \"confidentialite\", \"nomcreateur\",\"nbrparticipant\", \"id_student\" , \"state\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        SELECT_ALL_CITIES("SELECT t.id_city , t.zipcode , t.city_name FROM \"ssn-db-ing1\".city t"),
        SELECT_ALL_STUDENTS(
                        "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                                        "\tFROM \"ssn-db-ing1\".student"),
        STUDENT_INFO("SELECT student.id_student, student.familly_name as familyname , student.first_name as firstname, student.email as email, student.phone_number as phoneNumber, student.gender as gender,student.profile_image as profile_image , student.password as password , student.birthday as bithday\n"
                        + //
                        "FROM \"ssn-db-ing1\".student student where email = ?"),
        SELECT_FRIENDS("WITH\n" +
                        "\tLASTATTENDEDUNIVERSITY AS (\n" +
                        "\t\tSELECT\n" +
                        "\t\t\tID_STUDENT,\n" +
                        "\t\t\tID_UNIVERSITY,\n" +
                        "\t\t\tMAX(\"end\") AS MAX_END\n" +
                        "\t\tFROM\n" +
                        "\t\t\t\"ssn-db-ing1\".ATTENDED\n" +
                        "\t\tGROUP BY\n" +
                        "\t\t\tID_STUDENT,\n" +
                        "\t\t\tID_UNIVERSITY\n" +
                        "\t)\n" +
                        "SELECT DISTINCT\n" +
                        "\tON (S.EMAIL) S.ID_STUDENT AS ID_STUDENT,\n" +
                        "\tS.FAMILLY_NAME AS FAMILYNAME,\n" +
                        "\tS.FIRST_NAME AS FIRSTNAME,\n" +
                        "\tS.EMAIL AS EMAIL,\n" +
                        "\tS.PHONE_NUMBER AS PHONENUMBER,\n" +
                        "\tS.GENDER AS GENDER,\n" +
                        "\tS.USERNAME AS USERNAME,\n" +
                        "\tS.PROFILE_IMAGE AS PROFILE_IMAGE,\n" +
                        "\tS.PASSWORD AS PASSWORD,\n" +
                        "\tS.BIRTHDAY AS BITHDAY,\n" +
                        "\tBEF.STATUS AS STATUS,\n" +
                        "\tBEF.BEFRIEND_SINCE AS BEFRIEND_SINCE,\n" +
                        "\tBEF.RECEIVED_AT AS RECEIVED_AT,\n" +
                        "\tBEF.END_RELATION_AT AS END_RELATION_AT,\n" +
                        "\tU.LABEL AS UNIVERSITY,\n" +
                        "\tA.START AS FORMATION_START,\n" +
                        "\tA.END AS FORMATION_STOP,\n" +
                        "\tA.DESCRIPTION AS FORMATION_DESCRIPTION,\n" +
                        "\tA.TRAINING_FOLLOWED AS TRAINING_FOLLOWED\n" +
                        "FROM\n" +
                        "\t\"ssn-db-ing1\".BEFRIEND AS BEF\n" +
                        "\tJOIN \"ssn-db-ing1\".STUDENT AS S ON BEF.SENDER = S.ID_STUDENT\n" +
                        "\tJOIN LASTATTENDEDUNIVERSITY AS LAU ON S.ID_STUDENT = LAU.ID_STUDENT\n" +
                        "\tJOIN \"ssn-db-ing1\".ATTENDED AS A ON LAU.ID_STUDENT = A.ID_STUDENT\n" +
                        "\tAND LAU.ID_UNIVERSITY = A.ID_UNIVERSITY\n" +
                        "\tAND LAU.MAX_END = A.\"end\"\n" +
                        "\tJOIN \"ssn-db-ing1\".UNIVERSITY AS U ON LAU.ID_UNIVERSITY = U.ID_UNIVERSITY\n" +
                        "WHERE\n" +
                        "\tBEF.RECEIVER = ?\n" +
                        "\tAND BEF.STATUS <> 'rejected';"),

        SELECT_FRIEND_REQUEST_WITHOUT_ANSWER(
                        "SELECT student.familly_name, student.first_name, student.email, student.phone_number, student.gender, student.username,student.profile_image\n"
                                        + //
                                        "\tFROM \"ssn-db-ing1\".befriend as befriend inner join \"ssn-db-ing1\".student as student on befriend.receiver = student.id_student where befriend.sender=2 and befriend.status = 'no reponse'"),
        SELECT_ALL_UNIVERSITIES("SELECT t.id_university, t.label, t.shortname, t.acronym\n" + //
                        "\tFROM \"ssn-db-ing1\".university t ;"),
        DOES_STUDENT_EXIST(
                        "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                                        "\tFROM \"ssn-db-ing1\".student where email = ? "),
        INSERT_STUDENT("INSERT INTO \"ssn-db-ing1\".student (familly_name, first_name, email, phone_number, gender, username, \"password\", birthday) VALUES(?, ?, ?, ?, ?, ?, ? , ?)"),
        SIGN_IN_AS("SELECT  password FROM \"ssn-db-ing1\".student where email=?"),
        GET_DATA_CONNEXION("SELECT * FROM \"ssn-db-ing1\".student where email=?"),
        BECOME_FRIENDS("UPDATE \"ssn-db-ing1\".befriend\n" +
                        "SET status = 'accepted'\n" +
                        "WHERE sender = ? AND receiver = ?;"),
        REJECT_INVITATION("UPDATE \"ssn-db-ing1\".befriend\n" +
                        "SET status = 'rejected'\n" +
                        "WHERE sender = ? AND receiver = ?;"),
        SELECT_SUGGESTED_FRIENDS("SELECT\n" +
                        "    STUDENT.ID_STUDENT AS ID_STUDENT,\n" +
                        "    STUDENT.FAMILLY_NAME AS FAMILYNAME,\n" +
                        "    STUDENT.FIRST_NAME AS FIRSTNAME,\n" +
                        "    STUDENT.EMAIL AS EMAIL,\n" +
                        "    STUDENT.PHONE_NUMBER AS PHONENUMBER,\n" +
                        "    STUDENT.GENDER AS GENDER,\n" +
                        "    STUDENT.USERNAME AS USERNAME,\n" +
                        "    STUDENT.PROFILE_IMAGE AS PROFILE_IMAGE,\n" +
                        "    STUDENT.BIRTHDAY AS BITHDAY,\n" +
                        "    UNIVERSITY.LABEL AS UNIVERSITY,\n" +
                        "    ATTENDED.START AS FORMATION_START,\n" +
                        "    ATTENDED.END AS FORMATION_STOP,\n" +
                        "    ATTENDED.DESCRIPTION AS FORMATION_DESCRIPTION,\n" +
                        "    ATTENDED.TRAINING_FOLLOWED\n" +
                        "FROM\n" +
                        "\t\"ssn-db-ing1\".STUDENT as STUDENT\n" +
                        "\tNATURAL JOIN \"ssn-db-ing1\".ATTENDED as ATTENDED\n" +
                        "\tNATURAL JOIN \"ssn-db-ing1\".UNIVERSITY  as UNIVERSITY\n" +
                        "\tWHERE STUDENT.ID_STUDENT <> ?;"),
        FETCH_STUDENT_MESSAGES("SELECT \n" +
                        "    message.id_message, \n" +
                        "    message.sender_id, \n" +
                        "    message.receiver_id, \n" +
                        "    message.message_text, \n" +
                        "    message.media, \n" +
                        "    message.sent_at \n" +
                        "FROM \n" +
                        "    \"ssn-db-ing1\".message message \n" +
                        "WHERE \n" +
                        "    ? IN (message.sender_id, message.receiver_id) \n" +
                        "ORDER BY \n" +
                        "    message.sent_at DESC;"),
        SEND_MESSAGE("INSERT INTO \"ssn-db-ing1\".message (sender_id, receiver_id, message_text, media)\n" +
                        "VALUES (?, ?, ?, ?)\n" +
                        "RETURNING *;");

        private final String query;

        private Queries(final String query) {
                this.query = query;
        }

        public String getQuery() {
                return query;
        }
}
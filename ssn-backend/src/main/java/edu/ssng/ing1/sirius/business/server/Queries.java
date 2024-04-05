package edu.ssng.ing1.sirius.business.server;


 enum Queries {
        SELECT_ALL_ACTIVITY("SELECT t.datecreation, t.datedebut, t.datefin, t.nom_interet_activite, t.libelle, t.categorie, t.provenance, t.confidentialite, t.nomcreateur, t.id_student, t.nbrparticipant, t.state FROM \"ssn-db-ing1\".Activite t"),

        INSERT_ACTIVITY("INSERT into \"ssn-db-ing1\".Activite (\"datecreation\", \"datedebut\", \"datefin\", \"nom_interet_activite\", \"libelle\", \"categorie\", \"provenance\", \"confidentialite\", \"nomcreateur\",\"nbrparticipant\", \"id_student\" , \"state\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        
        SELECT_ALL_CITIES("SELECT t.id_city , t.zipcode , t.city_name FROM \"ssn-db-ing1\".city t"),
        SELECT_ALL_STUDENTS(
                "SELECT familly_name, first_name, email, phone_number, gender, username, password, birthday\n" + //
                        "\tFROM \"ssn-db-ing1\".student"),
        SELECT_FRIENDS(
                "SELECT student.familly_name as familyname , student.first_name as firstname, student.email as email, student.phone_number as phoneNumber, student.gender as gender, student.username as username,student.profile_image as profile_image , student.password as password , student.birthday as bithday \n"
                        + //
                        "\tFROM \"ssn-db-ing1\".befriend as befriend inner join \"ssn-db-ing1\".student as student on befriend.receiver = student.id_student where befriend.sender = ? and befriend.status = 'accepted'"),

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
                "SELECT  password FROM \"ssn-db-ing1\".student where email=? ");

        private final String query;

        private Queries(final String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    }
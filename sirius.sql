DROP TABLE IF EXISTS language;
CREATE TABLE "ssn-db-ing1".language (
  "id_language" serial,
  "title" varchar(50) ,
  "code" char(2),
  "acronym" char(5) ,
  PRIMARY KEY ("id_language"),
  UNIQUE("acronym")
);

DROP TABLE IF EXISTS student;
CREATE TABLE "ssn-db-ing1"."student" (
  "id_student" int NOT NULL AUTO_INCREMENT,
  "familly_name" varchar(50) NOT NULL,
  "first_name" varchar(50) NOT NULL,
  "email" varchar(50) NOT NULL,
  "birthday" date NOT NULL,
  "phone_number" varchar(25) NOT NULL,
  "gender" varchar(3),
  "profile_image" varchar(1024) DEFAULT "ssn-profile-image.png",
  "password" varchar(60) NOT NULL ,
  "registration_at" TIMESTAMP DEFAULT CURRENT_TIMESTAMP AT TIME ZONE 'UTC',
  "id_language" int ,
  UNIQUE("email"),
  PRIMARY KEY ("id_student"),
  KEY "id_language" ("id_language"),
  CONSTRAINT "student_ibfk_1" FOREIGN KEY ("id_language") REFERENCES "language" ("id_language")
) ;
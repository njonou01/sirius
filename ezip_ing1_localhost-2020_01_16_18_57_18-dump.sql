--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1 (Ubuntu 12.1-1.pgdg19.10+1)
-- Dumped by pg_dump version 12.1 (Ubuntu 12.1-1.pgdg19.10+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: students; Type: TABLE; Schema: ssn-db-ing1; Owner: ssnadmin
--

CREATE TABLE "ssn-db-ing1".students (
    name character varying(64) NOT NULL,
    firstname character varying(64) NOT NULL,
    id integer NOT NULL,
    "group" character varying(8) NOT NULL
);


ALTER TABLE "ssn-db-ing1".students OWNER TO ssnadmin;

--
-- Name: students_id_seq; Type: SEQUENCE; Schema: ssn-db-ing1; Owner: ssnadmin
--

CREATE SEQUENCE "ssn-db-ing1".students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "ssn-db-ing1".students_id_seq OWNER TO ssnadmin;

--
-- Name: students_id_seq; Type: SEQUENCE OWNED BY; Schema: ssn-db-ing1; Owner: ssnadmin
--

ALTER SEQUENCE "ssn-db-ing1".students_id_seq OWNED BY "ssn-db-ing1".students.id;


--
-- Name: students id; Type: DEFAULT; Schema: ssn-db-ing1; Owner: ssnadmin
--

ALTER TABLE ONLY "ssn-db-ing1".students ALTER COLUMN id SET DEFAULT nextval('"ssn-db-ing1".students_id_seq'::regclass);


--
-- Data for Name: students; Type: TABLE DATA; Schema: ssn-db-ing1; Owner: ssnadmin
--

-- COPY "ssn-db-ing1".students (name, firstname, id, "group") FROM stdin;
-- MYNAME	Myfirstname	401	FISA-B
-- \.


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: ssn-db-ing1; Owner: ssnadmin
--

SELECT pg_catalog.setval('"ssn-db-ing1".students_id_seq', 443, true);


--
-- Name: students students_pk; Type: CONSTRAINT; Schema: ssn-db-ing1; Owner: ssnadmin
--

ALTER TABLE ONLY "ssn-db-ing1".students
    ADD CONSTRAINT students_pk PRIMARY KEY (id);


--
-- Name: students_id_uindex; Type: INDEX; Schema: ssn-db-ing1; Owner: ssnadmin
--

CREATE UNIQUE INDEX students_id_uindex ON "ssn-db-ing1".students USING btree (id);


--
-- PostgreSQL database dump complete
--


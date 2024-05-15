package edu.ssng.ing1.sirius.requests.activities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.client.commons.ClientRequest;
import edu.ssng.ing1.sirius.client.commons.ConfigLoader;
import edu.ssng.ing1.sirius.client.commons.NetworkConfig;
import edu.ssng.ing1.sirius.commons.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import javax.print.DocFlavor.STRING;


public class InsertActivityQuery {

    private final static String LoggingLabel = "I n s e r t e r - C l i e n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
   
    
    private static String requestOrder = "";
    
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();




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




    public static void InsertActivite(Activite guy) throws IOException, InterruptedException, SQLException {

        // final Students guys = ConfigLoader.loadConfig(Students.class, studentsToBeInserted);
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        // logger.trace("Students loaded : {}", guys.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        // Activite guy= new Activite();

        // guy.setCategorie(categorie);
        // guy.setConfidentialite(confidentialite);
        // guy.setDatecreation(datecreation);
        // guy.setProvenance(provenance);
        // guy.setDatedebut(datedebut);
        // guy.setDatefin(datefin);
        // guy.setId_student(id_student);
        // guy.setNbrparticipant(nbrparticipant);
        // guy.setLibelle(libelle);
        // guy.setState(true);
        // guy.setDatecreation(datecreation);
        // guy.setNom_interet_activite(nom_interet_activite);
        // guy.setNomCreateur(nomCreateur);



        {


            

            // for(final Student guy : guys.getStudents()) { 
                final ObjectMapper objectMapper = new ObjectMapper();
                final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(guy);
                logger.trace("Student with its JSON face : {}", jsonifiedGuy);
                final String requestId = UUID.randomUUID().toString();
                final Request request = new Request();
                request.setRequestId(requestId);
                requestOrder="INSERT_ACTIVITY";
                request.setRequestOrder(requestOrder);
                request.setRequestContent(jsonifiedGuy);
                objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
                final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

                final InsertActivityClientRequest clientRequest = new InsertActivityClientRequest (
                                                                            networkConfig,
                                                                            birthdate++, request, guy, requestBytes);
                clientRequest.setResult("Done");
                clientRequests.push(clientRequest);
        }

        // while (!clientRequests.isEmpty()) {
        //     final ClientRequest clientRequest = clientRequests.pop();
        //     clientRequest.join();
        //     final Student guy = (Student)clientRequest.getInfo();
        //     logger.debug("Thread {} complete : {} {} {} --> {}",
        //                             clientRequest.getThreadName(),
        //                             guy.getFirstname(), guy.getLastname(), guy.getEmail(),
        //                             clientRequest.getResult());
        // }
    }
}

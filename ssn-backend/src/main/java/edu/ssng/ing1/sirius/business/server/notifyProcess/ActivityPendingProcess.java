package edu.ssng.ing1.sirius.business.server.notifyProcess;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ssng.commons.connectionpool.config.impl.ConnectionPoolImpl;
import edu.ssng.ing1.sirius.business.dto.Activite;
import edu.ssng.ing1.sirius.business.server.BroadcastNotification;
import edu.ssng.ing1.sirius.business.server.Queries;

public class ActivityPendingProcess extends Thread {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   Activite activite;
   int idActivite;
   Connection connection;
   Set<String> emails = new HashSet<>();
   private static final String dbEditorIsPGSQLHere = "postgresql";
   private ConnectionPoolImpl connectionPool = ConnectionPoolImpl.getInstance(dbEditorIsPGSQLHere);


   public ActivityPendingProcess(Activite activite,int idActivite) throws SQLException{
    
    this.activite=activite;
    this.idActivite=idActivite;
    

   }
    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        


        Queries query = Queries.valueOf("SELECT_PARTICIPATION");
        Date dateDebut = new Date();
        Date dateFin =  new Date();
        Date dateCreation =  new Date();
        try {
            if (0 < connectionPool.available()) {
                connection=connectionPool.get();
            }

            PreparedStatement stmt3 = connection.prepareStatement(query.getQuery());
                stmt3.setInt(1,idActivite);

                try (ResultSet rs = stmt3.executeQuery()) {
                    while (rs.next()) {
                        String email = rs.getString("email");
                        emails.add(email);
                        connectionPool.release(connection);
                    
                    }
                }

            dateDebut = dateFormat.parse(activite.getDatedebut());
            dateFin = dateFormat.parse(activite.getDatefin());
            dateCreation = dateFormat.parse(activite.getDatecreation());
            Long currentTimeMilli = Instant.now().toEpochMilli();
            if (currentTimeMilli<dateDebut.getTime()) {
                Long durationInMillis2 = dateDebut.getTime() - dateCreation.getTime();
                Thread.sleep(durationInMillis2); 
                BroadcastNotification.broadcast("BIGIN_ACTIVITY",emails, activite.getNom_interet_activite());
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                System.out.println(emails);

                
            }
            long durationInMillis = dateFin.getTime() - dateDebut.getTime();

                Thread.sleep(durationInMillis); 

                if (0 < connectionPool.available()) {
                    connection=connectionPool.get();
                }
                
                
                PreparedStatement stmt = connection.prepareStatement(query.getQuery());
                stmt.setInt(1,idActivite);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String email = rs.getString("email");
                        emails.add(email);
                    }
                }
                Queries query2= Queries.valueOf("UPDATE_STATE_ACTIVITY");

                PreparedStatement stmt2 = connection.prepareStatement(query2.getQuery());
                stmt2.setInt(1,idActivite);
                stmt2.executeUpdate();

                BroadcastNotification.broadcast("END_ACTIVITY",emails, activite.getNom_interet_activite());
                
                connectionPool.release(connection);
                
                System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                System.out.println("La durée est écoulée !"); 


            
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
       
        
       
        
       
    }

     
    
}

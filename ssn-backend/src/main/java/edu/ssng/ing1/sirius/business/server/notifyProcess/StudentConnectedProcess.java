package edu.ssng.ing1.sirius.business.server.notifyProcess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import edu.ssng.ing1.sirius.business.server.ConnectedStudent;
import edu.ssng.ing1.sirius.business.server.Queries;


public class StudentConnectedProcess {



     public synchronized static Set<String> getFriends(final String email, final Connection connection) {
        Set<String> myfriends = new HashSet<String>();
        Queries query = Queries.valueOf("SELECT_FRIENDS_FOR_CONNEXION");
        
            PreparedStatement stmt;
            try {
                stmt = connection.prepareStatement(query.getQuery());
                stmt.setString(1, email.trim());
                stmt.setString(2, email.trim());
                stmt.setString(3, email.trim());

                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    myfriends.add(rs.getString("email"));
            }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            System.out.println(myfriends);
            System.out.println(ConnectedStudent.getStudentConnectedemailHashmap().keySet());
            myfriends.retainAll(ConnectedStudent.getStudentConnectedemailHashmap().keySet());

                System.out.println(myfriends);
            
       

        return myfriends;
    }
    
}

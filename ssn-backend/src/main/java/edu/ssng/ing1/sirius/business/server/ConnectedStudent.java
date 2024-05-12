package edu.ssng.ing1.sirius.business.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.commons.SomeInfo;

/**
 * ConnectedStudent
 */
public class ConnectedStudent {

    private static Set<String> setOfStudentConnected= new HashSet<>();
    private static HashMap<String,Set<String>> studentConnectedemailHashmap= new HashMap<>();




    public static void addNewStudentConnected(SomeInfo someInfo){
        setOfStudentConnected.add(someInfo.getInfo());
        studentConnectedemailHashmap.put(someInfo.getInfo(), someInfo.getIpAdress());
    }

    public static void removeNewStudentConnected(SomeInfo someInfo){
        setOfStudentConnected.remove(someInfo.getInfo());
        studentConnectedemailHashmap.remove(someInfo.getInfo());
    }

    public static Set<String> getSetOfStudentConnected() {
        return setOfStudentConnected;
    }

    public static void setSetOfStudentConnected(Set<String> setOfStudentConnected) {
        ConnectedStudent.setOfStudentConnected = setOfStudentConnected;
    }

    public static HashMap<String, Set<String>> getStudentConnectedemailHashmap() {
        return studentConnectedemailHashmap;
    }
    public static void setStudentConnectedemailHashmap(HashMap<String, Set<String>> studentConnectedemailHashmap) {
        ConnectedStudent.studentConnectedemailHashmap = studentConnectedemailHashmap;
    }

}
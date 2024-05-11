package edu.ssng.ing1.sirius.business.server;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.ssng.ing1.sirius.business.dto.Student;

/**
 * ConnectedStudent
 */
public class ConnectedStudent {

    private static Set<String> setOfStudentConnected= new HashSet<>();
    private static HashMap<String,InetAddress> studentConnectedemailHashmap= new HashMap<>();




    public static void addNewStudentConnected(Student student){
        setOfStudentConnected.add(student.getEmail());
        studentConnectedemailHashmap.put(student.getEmail(), student.getAddressIp());
    }

    public static void removeNewStudentConnected(Student student){
        setOfStudentConnected.remove(student.getEmail());
        studentConnectedemailHashmap.remove(student.getEmail());
    }

    public static Set<String> getSetOfStudentConnected() {
        return setOfStudentConnected;
    }

    public static void setSetOfStudentConnected(Set<String> setOfStudentConnected) {
        ConnectedStudent.setOfStudentConnected = setOfStudentConnected;
    }

    public static HashMap<String, InetAddress> getStudentConnectedemailHashmap() {
        return studentConnectedemailHashmap;
    }
    public static void setStudentConnectedemailHashmap(HashMap<String, InetAddress> studentConnectedemailHashmap) {
        ConnectedStudent.studentConnectedemailHashmap = studentConnectedemailHashmap;
    }

}
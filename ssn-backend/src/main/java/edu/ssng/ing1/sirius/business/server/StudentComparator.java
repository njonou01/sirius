package edu.ssng.ing1.sirius.business.server;

import java.util.Comparator;

import edu.ssng.ing1.sirius.business.dto.Student;

public class StudentComparator implements Comparator<Student> {
    @Override
    public int compare(Student student1, Student student2) {
        return student1.getBithday().compareTo(student2.getBithday());
    }
}
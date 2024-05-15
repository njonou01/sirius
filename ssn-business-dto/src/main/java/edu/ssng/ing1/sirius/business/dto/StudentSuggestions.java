package edu.ssng.ing1.sirius.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashSet;
import java.util.Set;

public class StudentSuggestions {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("studentsuggestions")
    private Set<StudentSuggestion> students = new LinkedHashSet<StudentSuggestion>();

    public Set<StudentSuggestion> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentSuggestion> students) {
        this.students = students;
    }

    public final StudentSuggestions add(final StudentSuggestion student) {
        students.add(student);
        return this;
    }

    @Override
    public String toString() {
        return "Students{" +
                "students=" + students +
                '}';
    }
}
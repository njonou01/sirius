package edu.ssng.ing1.sirius.business.dto;

public class StudentSuggestion {
    private Student student;
    private int common_friends_count;

    public StudentSuggestion(Student student, int common_friends_count) {
        this.student = student;
        this.common_friends_count = common_friends_count;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getCommonFriendsCount() {
        return common_friends_count;
    }

    public void setCommonFriendsCount(int common_friends_count) {
        this.common_friends_count = common_friends_count;
    }

}

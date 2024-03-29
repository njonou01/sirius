
import java.util.*;

public class BeFriend {


    public BeFriend() {
    }
    private Boolean fiendshipStatus;
    private Date requestDate;
    private Date endFriendship;
    private Student receiverStudent;
    private Student askStudent;

    public BeFriend(Boolean fiendshipStatus, Date requestDate, Date endFriendship, Student receiverStudent, Student askStudent) {
        this.fiendshipStatus = fiendshipStatus;
        this.requestDate = requestDate;
        this.endFriendship = endFriendship;
        this.receiverStudent = receiverStudent;
        this.askStudent = askStudent;
    }

    public Boolean isFiendshipStatus() {
        return this.fiendshipStatus;
    }

    public Boolean getFiendshipStatus() {
        return this.fiendshipStatus;
    }

    public void setFiendshipStatus(Boolean fiendshipStatus) {
        this.fiendshipStatus = fiendshipStatus;
    }

    public Date getRequestDate() {
        return this.requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getEndFriendship() {
        return this.endFriendship;
    }

    public void setEndFriendship(Date endFriendship) {
        this.endFriendship = endFriendship;
    }

    public Student getReceiverStudent() {
        return this.receiverStudent;
    }

    public void setReceiverStudent(Student receiverStudent) {
        this.receiverStudent = receiverStudent;
    }

    public Student getAskStudent() {
        return this.askStudent;
    }

    public void setAskStudent(Student askStudent) {
        this.askStudent = askStudent;
    }

    public BeFriend fiendshipStatus(Boolean fiendshipStatus) {
        setFiendshipStatus(fiendshipStatus);
        return this;
    }

    public BeFriend requestDate(Date requestDate) {
        setRequestDate(requestDate);
        return this;
    }

    public BeFriend endFriendship(Date endFriendship) {
        setEndFriendship(endFriendship);
        return this;
    }

    public BeFriend receiverStudent(Student receiverStudent) {
        setReceiverStudent(receiverStudent);
        return this;
    }

    public BeFriend askStudent(Student askStudent) {
        setAskStudent(askStudent);
        return this;
    }
    @Override
    public String toString() {
        return "{" +
            " fiendshipStatus='" + isFiendshipStatus() + "'" +
            ", requestDate='" + getRequestDate() + "'" +
            ", endFriendship='" + getEndFriendship() + "'" +
            ", receiverStudent='" + getReceiverStudent() + "'" +
            ", askStudent='" + getAskStudent() + "'" +
            "}";
    }

}
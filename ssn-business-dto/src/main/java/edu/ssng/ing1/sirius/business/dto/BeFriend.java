package edu.ssng.ing1.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeFriend {
    private Student sender;
    private Student receiver;
    private String status;

    

    public BeFriend(Student sender, Student receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    private Timestamp end_relation_at;
    private Timestamp befriend_since;
    private Timestamp received_at;

    public BeFriend() {
    }

    public final BeFriend build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "status" ,"befriend_since","received_at");
        return this;
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    public BeFriend(Student sender, Student receiver, String status, Timestamp end_relation_at, Timestamp befriend_since,
            Timestamp received_at) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.end_relation_at = end_relation_at;
        this.befriend_since = befriend_since;
        this.received_at = received_at;
    }

    public Student getSender() {
        return this.sender;
    }

    public void setSender(Student sender) {
        this.sender = sender;
    }

    public Student getReceiver() {
        return this.receiver;
    }

    public void setReceiver(Student receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getEnd_relation_at() {
        return this.end_relation_at;
    }

    public void setEnd_relation_at(Timestamp end_relation_at) {
        this.end_relation_at = end_relation_at;
    }

    public Timestamp getBefriend_since() {
        return this.befriend_since;
    }

    public void setBefriend_since(Timestamp befriend_since) {
        this.befriend_since = befriend_since;
    }

    public Timestamp getReceived_at() {
        return this.received_at;
    }

    public void setReceived_at(Timestamp received_at) {
        this.received_at = received_at;
    }

    public BeFriend sender(Student sender) {
        setSender(sender);
        return this;
    }

    public BeFriend receiver(Student receiver) {
        setReceiver(receiver);
        return this;
    }

    public BeFriend status(String status) {
        setStatus(status);
        return this;
    }

    public BeFriend end_relation_at(Timestamp end_relation_at) {
        setEnd_relation_at(end_relation_at);
        return this;
    }

    public BeFriend befriend_since(Timestamp befriend_since) {
        setBefriend_since(befriend_since);
        return this;
    }

    public BeFriend received_at(Timestamp received_at) {
        setReceived_at(received_at);
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                " sender='" + getSender() + "'" +
                ", receiver='" + getReceiver() + "'" +
                ", status='" + getStatus() + "'" +
                ", end_relation_at='" + getEnd_relation_at() + "'" +
                ", befriend_since='" + getBefriend_since() + "'" +
                ", received_at='" + getReceived_at() + "'" +
                "}";
    }

}
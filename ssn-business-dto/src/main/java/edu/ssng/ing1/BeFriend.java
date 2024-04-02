package edu.ssng.ing1;

import java.sql.Date;

public class BeFriend {
    private int sender;
    public BeFriend(int sender, int receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }
    private int receiver;
    private String status;
    private Date end_relation_at;
    private Date befriend_since;
    private Date received_at;

    public BeFriend() {
    }

    public BeFriend(int sender, int receiver, String status, Date end_relation_at, Date befriend_since,
            Date received_at) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
        this.end_relation_at = end_relation_at;
        this.befriend_since = befriend_since;
        this.received_at = received_at;
    }

    public int getSender() {
        return this.sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return this.receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getEnd_relation_at() {
        return this.end_relation_at;
    }

    public void setEnd_relation_at(Date end_relation_at) {
        this.end_relation_at = end_relation_at;
    }

    public Date getBefriend_since() {
        return this.befriend_since;
    }

    public void setBefriend_since(Date befriend_since) {
        this.befriend_since = befriend_since;
    }

    public Date getReceived_at() {
        return this.received_at;
    }

    public void setReceived_at(Date received_at) {
        this.received_at = received_at;
    }

    public BeFriend sender(int sender) {
        setSender(sender);
        return this;
    }

    public BeFriend receiver(int receiver) {
        setReceiver(receiver);
        return this;
    }

    public BeFriend status(String status) {
        setStatus(status);
        return this;
    }

    public BeFriend end_relation_at(Date end_relation_at) {
        setEnd_relation_at(end_relation_at);
        return this;
    }

    public BeFriend befriend_since(Date befriend_since) {
        setBefriend_since(befriend_since);
        return this;
    }

    public BeFriend received_at(Date received_at) {
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
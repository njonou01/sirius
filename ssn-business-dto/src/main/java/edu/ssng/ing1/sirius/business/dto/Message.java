package edu.ssng.ing1.sirius.business.dto;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;


public class Message {
    private int idMessage;
    private int senderId;
    private int receiverId;
    private String messageText;
    private String media;
    private Timestamp sentAt;

    public final Message build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "id_message", "sender_id", "receiver_id",
                "message_text", "media", "sent_at");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return saveMessageStatement(preparedStatement, senderId, receiverId, messageText, media);
    }

    public Message() {
    }

    public Message(Integer idMessage, Integer senderId, Integer receiverId, String messageText, String media,
            Timestamp sentAt) {
        this.idMessage = idMessage;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.messageText = messageText;
        this.media = media;
        this.sentAt = sentAt;
    }

    public Integer getIdMessage() {
        return this.idMessage;
    }

    public void setIdMessage(Integer idMessage) {
        this.idMessage = idMessage;
    }

    public Integer getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMedia() {
        return this.media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Timestamp getSentAt() {
        return this.sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public Message idMessage(Integer idMessage) {
        setIdMessage(idMessage);
        return this;
    }

    public Message senderId(Integer senderId) {
        setSenderId(senderId);
        return this;
    }

    public Message receiverId(Integer receiverId) {
        setReceiverId(receiverId);
        return this;
    }

    public Message messageText(String messageText) {
        setMessageText(messageText);
        return this;
    }

    public Message media(String media) {
        setMedia(media);
        return this;
    }

    public Message sentAt(Timestamp sentAt) {
        setSentAt(sentAt);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(idMessage, message.idMessage) && Objects.equals(senderId, message.senderId)
                && Objects.equals(receiverId, message.receiverId) && Objects.equals(messageText, message.messageText)
                && Objects.equals(media, message.media) && Objects.equals(sentAt, message.sentAt);
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement saveMessageStatement(PreparedStatement preparedStatement,
            final Object... fieldValues)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final Object fieldValue : fieldValues) {
            if (fieldValue instanceof String) {
                preparedStatement.setString(++ix, (String) fieldValue);
            } else if (fieldValue instanceof Integer) {
                preparedStatement.setInt(++ix, (Integer) fieldValue);
            } else if (fieldValue instanceof Timestamp) {
                preparedStatement.setTimestamp(++ix, (Timestamp) fieldValue);
            } else {
                throw new IllegalArgumentException("Unsupported field value type: " + fieldValue.getClass().getName());
            }
        }
        preparedStatement.setTimestamp(++ix, sentAt);
        return preparedStatement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMessage, senderId, receiverId, messageText, media, sentAt);
    }

    @Override
    public String toString() {
        return "{" +
                " idMessage='" + getIdMessage() + "'" +
                ", senderId='" + getSenderId() + "'" +
                ", receiverId='" + getReceiverId() + "'" +
                ", messageText='" + getMessageText() + "'" +
                ", media='" + getMedia() + "'" +
                ", sentAt='" + getSentAt() + "'" +
                "}";
    }
}

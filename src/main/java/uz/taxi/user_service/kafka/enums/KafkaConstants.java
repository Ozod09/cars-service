package uz.taxi.user_service.kafka.enums;

public interface KafkaConstants {

    String CHAT_GROUP = "chat-group";
    String AUTH_GROUP = "auth-group";

    String TOPIC_CHAT_ADD_USER = "chat-add-user";
    String TOPIC_EMPLOYE_OR_USER_STATUS_UPDATE = "employe-or-user-status-update";
    String TOPIC_ADD_AUTH_USER = "add-auth-user";
    String TOPIC_UPDATE_PASSWORD = "update-password";
}

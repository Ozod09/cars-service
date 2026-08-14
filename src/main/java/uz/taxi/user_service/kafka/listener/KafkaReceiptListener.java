package uz.taxi.user_service.kafka.listener;//package uz.taxi.auth_service.kafka.listener;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import uz.taxi.auth_service.kafka.KafkaReqAddAuthUser;
//import uz.taxi.auth_service.kafka.KafkaReqEmployeOrUserStatusUpdate;
//import uz.taxi.auth_service.kafka.KafkaReqUpdatePassword;
//import uz.taxi.auth_service.kafka.enums.KafkaConstants;
//import uz.taxi.auth_service.rest.service.AuthService;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.stereotype.Component;
//
//
//
//@Log4j2
//@Component
//@RequiredArgsConstructor
//public class KafkaReceiptListener {
//
//    private final AuthService authService;
//
//    @KafkaListener(topics = KafkaConstants.TOPIC_EMPLOYE_OR_USER_STATUS_UPDATE, groupId = KafkaConstants.AUTH_GROUP, containerFactory = "employeOrUserStatusUpdateContainerFactory")
//    public void employeOrUserStatusUpdate(KafkaReqEmployeOrUserStatusUpdate request) {
//        log.info("KAFKA_REQ_EMPLOYE_OR_USER_STATUS_UPDATE: {}", request.toString());
//        authService.employeOrUserStatusUpdate(request);
//    }
//
//    @KafkaListener(topics = KafkaConstants.TOPIC_ADD_AUTH_USER, groupId = KafkaConstants.AUTH_GROUP, containerFactory = "addAuthUser")
//    public void addAuthUser(KafkaReqAddAuthUser request) {
//        log.info("KAFKA_REQ_ADD_AUTH_USER: {}", request.toString());
//        authService.addAuthUser(request);
//    }
//
//    @KafkaListener(topics = KafkaConstants.TOPIC_UPDATE_PASSWORD, groupId = KafkaConstants.AUTH_GROUP, containerFactory = "updatePassword")
//    public void updatePassword(KafkaReqUpdatePassword request) {
//        log.info("KAFKA_REQ_UPDATE_PASSWORD: {}", request.toString());
//        authService.updatePassword(request);
//    }
//}

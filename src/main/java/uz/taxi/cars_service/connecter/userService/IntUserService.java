package uz.taxi.cars_service.connecter.userService;//package uz.taxi.user_service.connecter.userService;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//import uz.taxi.user_service.common.GlobalVar;
//import uz.taxi.user_service.connecter.IntServiceURL;
//import uz.taxi.user_service.connecter.WebClientConnector;
//import uz.taxi.user_service.connecter.userService.payload.IntReqSaveTrustedDevice;
//
//import java.time.Duration;
//import java.util.UUID;
//
//@Slf4j
//@Service
//public class IntUserService {
//
//    private final WebClientConnector connector;
//
//    public IntUserService(@Qualifier("webClient") WebClient webClient,
//                          @Qualifier("userServiceSetting") IntUserServiceSetting userServiceSetting) {
//        this.connector = new WebClientConnector(webClient, userServiceSetting);
//    }
//
//    public void studentCreate(SignUpRequest request) {
//        try {
//            log.info("INT_USER_CREATE_REQ: {}", request.toString());
//
//            var response = connector.doPost(
//                    IntServiceURL.STUDENT_CREATE.getUrl(),
//                    GlobalVar.getHeaders(),
//                    request,
//                    String.class,
//                    null,
//                    true,
//                    Duration.ofSeconds(60),
//                    true,
//                    true
//            );
//
//            log.info("INT_USER_CREATE_RESPONSE: {}, logId: {}", response, GlobalVar.getLogId());
//
//        } catch (Throwable th) {
//            log.error("INT_USER_CREATE_ERROR: {}, logId: {}", th.getMessage(), GlobalVar.getLogId());
//        }
//    }
//
//    public String saveTrustedDevice(UUID uuid, UUID userId, String deviceId, String deviceModel, DeviceTypeEnum deviceType) {
//        var request = new IntReqSaveTrustedDevice();
//        request.setUuid(uuid);
//        request.setUserId(userId);
//        request.setDeviceId(deviceId);
//        request.setDeviceType(deviceType);
//        request.setDeviceModel(deviceModel);
//
//        try {
//           return connector.doPost(
//                    IntServiceURL.SAVE_TRUSTED_DEVICE.getUrl(),
//                    GlobalVar.getHeaders(),
//                    request,
//                    String.class,
//                    null,
//                    true,
//                    Duration.ofSeconds(30),
//                    false,
//                    true
//            );
//        } catch (Throwable th) {
//            log.error("INT_USER_SAVE_TRUSTED_ERROR: {}, logId: {}", th.getMessage(), GlobalVar.getLogId());
//            return null;
//        }
//    }
//}

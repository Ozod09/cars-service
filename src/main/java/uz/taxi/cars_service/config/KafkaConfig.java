package uz.taxi.cars_service.config;//package uz.sfera.auth_service.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import uz.sfera.auth_service.kafka.KafkaReqAddAuthUser;
//import uz.sfera.auth_service.kafka.KafkaReqEmployeOrUserStatusUpdate;
//import uz.sfera.auth_service.kafka.KafkaReqUpdatePassword;
//import uz.sfera.auth_service.kafka.KafkaReqUser;
//import uz.sfera.auth_service.kafka.enums.KafkaConstants;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.*;
//import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//@EnableKafka
//public class KafkaConfig {
//
//    @Value(value = "${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Bean
//    public ProducerFactory<String, Object> kafkaMessageProducerFactory(ObjectMapper mapper) {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
//        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
//
//        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>(mapper));
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> kafkaMessageProducerFactory) {
//        return new KafkaTemplate<>(kafkaMessageProducerFactory);
//    }
//
//    public <T> ConsumerFactory<String, T> kafkaMessageConsumerFactory(Class<T> clazz, ObjectMapper mapper) {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.AUTH_GROUP);
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
//
//        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
//        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
//
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, clazz.getName());
//
//        return new DefaultKafkaConsumerFactory<>(
//                config,
//                new StringDeserializer(),
//                new JsonDeserializer<>(clazz, false)
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaReqUser> reqContainerFactory(ObjectMapper mapper) {
//        ConcurrentKafkaListenerContainerFactory<String, KafkaReqUser> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(kafkaMessageConsumerFactory(KafkaReqUser.class, mapper));
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaReqEmployeOrUserStatusUpdate> employeOrUserStatusUpdateContainerFactory(ObjectMapper mapper) {
//        ConcurrentKafkaListenerContainerFactory<String, KafkaReqEmployeOrUserStatusUpdate> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(kafkaMessageConsumerFactory(KafkaReqEmployeOrUserStatusUpdate.class, mapper));
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaReqAddAuthUser> addAuthUser(ObjectMapper mapper) {
//        ConcurrentKafkaListenerContainerFactory<String, KafkaReqAddAuthUser> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(kafkaMessageConsumerFactory(KafkaReqAddAuthUser.class, mapper));
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, KafkaReqUpdatePassword> updatePassword(ObjectMapper mapper) {
//        ConcurrentKafkaListenerContainerFactory<String, KafkaReqUpdatePassword> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(kafkaMessageConsumerFactory(KafkaReqUpdatePassword.class, mapper));
//        return factory;
//    }
//}

package uz.taxi.cars_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import uz.taxi.cars_service.config.prop.AuthProp;
import uz.taxi.cars_service.config.prop.InternalProp;

@SpringBootApplication
@EnableConfigurationProperties(value = {AuthProp.class, InternalProp.class})
public class CarsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsServiceApplication.class, args);
    }
}

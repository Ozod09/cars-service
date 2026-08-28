package uz.taxi.cars_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.taxi.cars_service.base.BaseEntity;
import uz.taxi.cars_service.enums.BrentStatusEnum;
import uz.taxi.cars_service.enums.CarsStatusEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "cars")
@Getter
@Setter
public class Cars extends BaseEntity {

    @Column(nullable = false)
    private UUID driverId;

    private String firstName;

    private String lastName;

    private String phone;

    private String attachmentPath;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String number;

    private String texPassportPhotoPath;

    @ElementCollection
    @CollectionTable(name = "cars_service_ids", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "cars_class_id")
    private List<UUID> carsServiceIds = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BrentStatusEnum brentStatus = BrentStatusEnum.NOBRAND;

    @ElementCollection
    @CollectionTable(name = "brent_cars_take_photo_paths", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "photo_path")
    private List<String> brentCarsTakePhotoPaths = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarsStatusEnum status = CarsStatusEnum.UNCONFIRMED;

    @Column(insertable = false, unique = true, updatable = false, name = "car_redis_id")
    private Long carRedisId;
}

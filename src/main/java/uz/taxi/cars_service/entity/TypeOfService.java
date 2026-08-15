package uz.taxi.cars_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.taxi.cars_service.base.BaseEntity;
import uz.taxi.cars_service.enums.CarsCategoryStatusEnum;

import java.util.UUID;

@Entity
@Table(name = "type_of_services")
@Getter
@Setter
public class TypeOfService extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    private String attachmentPath;

    private UUID regionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarsCategoryStatusEnum status = CarsCategoryStatusEnum.ACTIVE;
}

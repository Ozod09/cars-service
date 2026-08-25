package uz.taxi.cars_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.taxi.cars_service.base.BaseEntity;
import uz.taxi.cars_service.enums.CarsCategoryStatusEnum;

@Entity
@Table(name = "cars_classes")
@Getter
@Setter
public class CarsClass extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_of_service_id")
    private TypeOfService typeOfService;

    @Column(nullable = false)
    private String name;

    private String description;

    private Double startPrice;

    private Double startKm;

    private Double startKmPrice;

    private Double kmPrice;

    private Double eveningPrice;

    private Double paidWaitingTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarsCategoryStatusEnum status = CarsCategoryStatusEnum.ACTIVE;
}

package uz.taxi.cars_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.taxi.cars_service.entity.Cars;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarsRepository extends JpaRepository<Cars, UUID> {

    @Query("""
        SELECT c FROM Cars c
        WHERE (:filter IS NULL OR :filter = ''
           OR LOWER(c.model) LIKE LOWER(CONCAT('%', :filter, '%'))
           OR LOWER(c.number) LIKE LOWER(CONCAT('%', :filter, '%')))
        AND (:driverId IS NULL OR c.driverId = :driverId)
        """)
    Page<Cars> findAllByFilter(@Param("filter") String filter,
                                @Param("driverId") UUID driverId,
                                Pageable pageable);

    Optional<Cars> findByDriverId(UUID driverId);
}

package uz.taxi.cars_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.taxi.cars_service.entity.CarsClass;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarsClassRepository extends JpaRepository<CarsClass, UUID> {

    @Query("""
        SELECT c FROM CarsClass c
        WHERE (:filter IS NULL OR :filter = ''
           OR LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')))
        AND (:typeOfServiceId IS NULL OR c.typeOfService.id = :typeOfServiceId)
        """)
    Page<CarsClass> findAllByFilter(@Param("filter") String filter,
                                     @Param("typeOfServiceId") UUID typeOfServiceId,
                                     Pageable pageable);


    @Query("""
        SELECT c FROM CarsClass c
        WHERE (:filter IS NULL OR :filter = ''
           OR LOWER(c.name) LIKE LOWER(CONCAT('%', :filter, '%')))
        AND (:typeOfServiceId IS NULL OR c.typeOfService.id = :typeOfServiceId)
        and c.status = 'ACTIVE'
    """)
    List<CarsClass> findAllByFilter(@Param("filter") String filter,
                                    @Param("typeOfServiceId") UUID typeOfServiceId);
}

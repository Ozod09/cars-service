package uz.taxi.cars_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.taxi.cars_service.entity.TypeOfService;

import java.util.UUID;

@Repository
public interface TypeOfServiceRepository extends JpaRepository<TypeOfService, UUID> {

    @Query("""
    SELECT t FROM TypeOfService t
    WHERE t.regionId = :regionId
      AND (
            (:filter IS NULL OR :filter = '')
         OR LOWER(t.name) LIKE LOWER(CONCAT('%', :filter, '%'))
      )
    """)
    Page<TypeOfService> findAllByFilter(@Param("regionId") UUID regionId,
                                        @Param("filter") String filter,
                                        Pageable pageable);
}

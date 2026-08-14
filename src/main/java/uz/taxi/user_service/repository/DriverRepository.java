package uz.taxi.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {

    boolean existsByPhone(String phoneNumber);

    Optional<Driver> findByPhone(String phoneNumber);

    boolean existsByPassportSerialNumber(String passportSerialNumber);
}

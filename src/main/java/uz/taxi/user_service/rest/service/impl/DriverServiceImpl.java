package uz.taxi.user_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.taxi.user_service.base.Messages;
import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.enums.UserStatusEnum;
import uz.taxi.user_service.exception.BadRequestException;
import uz.taxi.user_service.repository.DriverRepository;
import uz.taxi.user_service.rest.payload.req.StatusEditRequest;
import uz.taxi.user_service.rest.payload.req.driver.DriverRequest;
import uz.taxi.user_service.rest.payload.res.driver.DriverRes;
import uz.taxi.user_service.rest.service.DriverService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository repository;

    @Override
    public GenericResponse<?> create(DriverRequest request) {

        if (request.getId() == null) {
            throw new BadRequestException(Messages.DRIVER_ID_NOT_FOUND);
        }

        if (repository.existsByPhone(request.getPhone())) {
            throw new BadRequestException(Messages.PHONE_ALREADY_EXISTS);
        }

        if (repository.existsByPassportSerialNumber(request.getPassportSerialNumber())) {
            throw new BadRequestException(Messages.PASSPORT_ALREADY_EXISTS);
        }

        Driver driver = new Driver();
        driver.setId(request.getId());
        driver.setFirstName(request.getFirstName());
        driver.setLastName(request.getLastName());
        driver.setPhone(request.getPhone());
        driver.setBirthDate(request.getBirthDate());
        driver.setPassportSerialNumber(request.getPassportSerialNumber());
        driver.setPassportPhotoPath(request.getPassportPhotoPath());
        driver.setStatus(UserStatusEnum.NOACTIVE);

        Driver save = repository.save(driver);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID driverId, DriverRequest req) {
        Driver driver = repository.findById(driverId).orElseThrow(() ->
                new BadRequestException(Messages.DRIVER_NOT_FOUND));

        // Check phone uniqueness if changed
        if (!driver.getPhone().equals(req.getPhone()) && repository.existsByPhone(req.getPhone())) {
            throw new BadRequestException(Messages.PHONE_ALREADY_EXISTS);
        }

        // Check passport uniqueness if changed
        if (!driver.getPassportSerialNumber().equals(req.getPassportSerialNumber()) &&
                repository.existsByPassportSerialNumber(req.getPassportSerialNumber())) {
            throw new BadRequestException(Messages.PASSPORT_ALREADY_EXISTS);
        }

        driver.setFirstName(req.getFirstName());
        driver.setLastName(req.getLastName());
        driver.setPhone(req.getPhone());
        driver.setBirthDate(req.getBirthDate());
        driver.setPassportSerialNumber(req.getPassportSerialNumber());
        driver.setPassportPhotoPath(req.getPassportPhotoPath());
        driver.setUpdatedAt(LocalDateTime.now());

        Driver save = repository.save(driver);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> get(UUID driverId) {
        Driver driver = repository.findById(driverId).orElseThrow(() ->
                new BadRequestException(Messages.DRIVER_NOT_FOUND));

        return GenericResponse.success(Messages.SUCCESS, driverRes(driver));
    }

    public DriverRes driverRes(Driver driver) {
        DriverRes driverRes = new DriverRes();
        driverRes.setId(driver.getId());
        driverRes.setFirstName(driver.getFirstName());
        driverRes.setLastName(driver.getLastName());
        driverRes.setPhone(driver.getPhone());
        driverRes.setAttachmentPath(driver.getAttachmentPath());
        driverRes.setBirthDate(driver.getBirthDate());
        driverRes.setStatus(driver.getStatus().name());
        driverRes.setPassportSerialNumber(driver.getPassportSerialNumber());
        driverRes.setPassportPhotoPath(driver.getPassportPhotoPath());

        return driverRes;
    };

    @Override
    public GenericResponse<?> getAll() {
        List<Driver> drivers = repository.findAll();
        return GenericResponse.success(Messages.SUCCESS, drivers);
    }

    @Override
    public GenericResponse<?> editStatus(StatusEditRequest request) {
        Driver driver = repository.findById(request.getId()).orElseThrow(() ->
                new BadRequestException(Messages.DRIVER_NOT_FOUND));

        UserStatusEnum userStatusEnum = UserStatusEnum.valueOf(request.getUserStatus());
        driver.setStatus(userStatusEnum);

        Driver save = repository.save(driver);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID driverId) {
        Driver driver = repository.findById(driverId).orElseThrow(() ->
                new BadRequestException(Messages.DRIVER_NOT_FOUND));

        driver.setStatus(UserStatusEnum.DELETED);
        driver.setUpdatedAt(LocalDateTime.now());
        repository.save(driver);

        return GenericResponse.success(Messages.DELETED_SUCCESSFULLY, null);
    }
}

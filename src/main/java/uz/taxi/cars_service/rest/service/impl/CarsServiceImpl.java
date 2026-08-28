package uz.taxi.cars_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.taxi.cars_service.base.Message;
import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.entity.Cars;
import uz.taxi.cars_service.enums.BrentStatusEnum;
import uz.taxi.cars_service.enums.CarsStatusEnum;
import uz.taxi.cars_service.exception.BadRequestException;
import uz.taxi.cars_service.exception.CustomNotFoundException;
import uz.taxi.cars_service.exception.UnauthorizedException;
import uz.taxi.cars_service.repository.CarsRepository;
import uz.taxi.cars_service.rest.payload.req.cars.BrantCarsRequest;
import uz.taxi.cars_service.rest.payload.req.cars.CarsRequest;
import uz.taxi.cars_service.rest.payload.res.ResPageable;
import uz.taxi.cars_service.rest.payload.res.cars.CarsResponse;
import uz.taxi.cars_service.rest.service.CarGeoService;
import uz.taxi.cars_service.rest.service.CarsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarsServiceImpl implements CarsService {

    private final CarsRepository repository;
    private final CarGeoService carGeoService;

    @Override
    public GenericResponse<?> carLocationUpdate(Long carId, double lat, double lon) {

        //        UUID driverId = GlobalVar.getUserId();
        UUID driverId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        if (driverId == null) {
            throw new UnauthorizedException(Message.AUTHENTICATION_REQUIRED);
        }

        if (carId == null || carId == 0) {
            throw new BadRequestException(Message.CAR_REDIS_ID);
        }

        carGeoService.updateLocation(carId, lat, lon);

        return GenericResponse.success(Message.SUCCESS, Message.SUCCESS);
    }

    @Override
    public GenericResponse<?> startShift() {

//        UUID driverId = GlobalVar.getUserId();
        UUID driverId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66ada6");
        if (driverId == null) {
            throw new UnauthorizedException(Message.AUTHENTICATION_REQUIRED);
        }

        Cars car = repository.findByDriverId(driverId)
                .orElseThrow(() -> new CustomNotFoundException(Message.CAR_NOT_FOUND));

        carGeoService.startShift(car.getCarRedisId());

        return GenericResponse.success(Message.SUCCESS, car.getCarRedisId());
    }

    @Override
    public GenericResponse<?> endShift() {

//        UUID driverId = GlobalVar.getUserId();
        UUID driverId = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");
        if (driverId == null) {
            throw new UnauthorizedException(Message.AUTHENTICATION_REQUIRED);
        }

        Cars car = repository.findByDriverId(driverId)
                .orElseThrow(() -> new CustomNotFoundException(Message.CAR_NOT_FOUND));

        carGeoService.endShift(car.getCarRedisId());

        return GenericResponse.success(Message.SUCCESS, Message.SUCCESS);
    }

    @Override
    public GenericResponse<?> getPage(String filter, UUID driverId, int page, int size) {

        String normalizedFilter = normalizeFilter(filter);

        Page<Cars> entities = repository.findAllByFilter(
                normalizedFilter,
                driverId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        List<CarsResponse> list = entities.getContent().stream()
                .map(this::toResponse)
                .toList();

        ResPageable resPageable = ResPageable.builder()
                .object(list)
                .totalPage(entities.getTotalPages())
                .page(page)
                .size(size)
                .totalElements(entities.getTotalElements())
                .build();

        return GenericResponse.success(Message.SUCCESS, resPageable);
    }

    @Override
    public GenericResponse<?> create(CarsRequest request) {

        List<UUID> objects = request.getCarsServiceIds() != null
                ? request.getCarsServiceIds() : new ArrayList<>();

        Cars entity = new Cars();
        entity.setDriverId(request.getDriverId());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhone(request.getPhone());
        entity.setAttachmentPath(request.getAttachmentPath());
        entity.setModel(request.getModel());
        entity.setNumber(request.getNumber());
        entity.setTexPassportPhotoPath(request.getTexPassportPhotoPath());
        entity.setCarsServiceIds(objects);
        entity.setStatus(CarsStatusEnum.UNCONFIRMED);

        Cars saved = repository.save(entity);

        return GenericResponse.success(Message.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID id, CarsRequest request) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        entity.setDriverId(request.getDriverId());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhone(request.getPhone());
        entity.setAttachmentPath(request.getAttachmentPath());
        entity.setModel(request.getModel());
        entity.setNumber(request.getNumber());
        entity.setTexPassportPhotoPath(request.getTexPassportPhotoPath());
        entity.setCarsServiceIds(request.getCarsServiceIds() != null ? request.getCarsServiceIds() : new ArrayList<>());

        Cars saved = repository.save(entity);

        return GenericResponse.success(Message.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> editBrent(UUID id, BrantCarsRequest request) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        entity.setBrentStatus(BrentStatusEnum.UNCONFIRMED);
        entity.setBrentCarsTakePhotoPaths(request.getBrentCarsTakePhotoPaths());

        Cars saved = repository.save(entity);

        return GenericResponse.success(Message.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> get(UUID id) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        return GenericResponse.success(Message.SUCCESS, toResponse(entity));
    }

    @Override
    public GenericResponse<?> getByDriverId(UUID driverId) {

        Cars entity = repository.findByDriverId(driverId)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        return GenericResponse.success(Message.SUCCESS, toResponse(entity));
    }

    @Override
    public GenericResponse<?> editStatus(UUID id, String status) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        try {
            CarsStatusEnum statusEnum = CarsStatusEnum.valueOf(status);
            entity.setStatus(statusEnum);
            repository.save(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Message.INVALID_STATUS_VALUE);
        }

        return GenericResponse.success(Message.SUCCESS, entity.getId());
    }

    @Override
    public GenericResponse<?> editBrentStatus(UUID id, String brentStatus) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        try {
            BrentStatusEnum brentStatusEnum = BrentStatusEnum.valueOf(brentStatus);
            entity.setBrentStatus(brentStatusEnum);
            repository.save(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Message.INVALID_BRENT_STATUS);
        }

        return GenericResponse.success(Message.SUCCESS, entity.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID id) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        entity.setStatus(CarsStatusEnum.DELETED);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.save(entity);

        return GenericResponse.success(Message.DELETED_SUCCESSFULLY, Message.DELETED_SUCCESSFULLY.getText());
    }

    private CarsResponse toResponse(Cars entity) {

        CarsResponse response = new CarsResponse();
        response.setId(entity.getId());
        response.setDriverId(entity.getDriverId());
        response.setFirstName(entity.getFirstName());
        response.setLastName(entity.getLastName());
        response.setPhone(entity.getPhone());
        response.setAttachmentPath(entity.getAttachmentPath());
        response.setModel(entity.getModel());
        response.setNumber(entity.getNumber());
        response.setTexPassportPhotoPath(entity.getTexPassportPhotoPath());
        response.setCarsServiceIds(entity.getCarsServiceIds());
        response.setBrentStatus(entity.getBrentStatus().name());
        response.setBrentCarsTakePhotoPaths(entity.getBrentCarsTakePhotoPaths());
        response.setStatus(entity.getStatus().name());
        response.setCarRedisId(entity.getCarRedisId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }

    private String normalizeFilter(String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            return null;
        }
        return filter.trim();
    }
}

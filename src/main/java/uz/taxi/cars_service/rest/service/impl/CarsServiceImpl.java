package uz.taxi.cars_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.taxi.cars_service.base.Messages;
import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.entity.Cars;
import uz.taxi.cars_service.enums.BrentStatusEnum;
import uz.taxi.cars_service.enums.CarsStatusEnum;
import uz.taxi.cars_service.exception.BadRequestException;
import uz.taxi.cars_service.exception.CustomNotFoundException;
import uz.taxi.cars_service.repository.CarsRepository;
import uz.taxi.cars_service.rest.payload.req.cars.BrantCarsRequest;
import uz.taxi.cars_service.rest.payload.req.cars.CarsRequest;
import uz.taxi.cars_service.rest.payload.res.ResPageable;
import uz.taxi.cars_service.rest.payload.res.cars.CarsResponse;
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

        return GenericResponse.success(Messages.SUCCESS, resPageable);
    }

    @Override
    public GenericResponse<?> create(CarsRequest request) {

        Cars entity = new Cars();
        entity.setDriverId(request.getDriverId());
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhone(request.getPhone());
        entity.setAttachmentPath(request.getAttachmentPath());
        entity.setModel(request.getModel());
        entity.setNumber(request.getNumber());
        entity.setTexPassportPhotoPath(request.getTexPassportPhotoPath());
        entity.setCarsServiceIds(request.getCarsServiceIds() != null ? request.getCarsServiceIds() : new ArrayList<>());
        entity.setStatus(CarsStatusEnum.UNCONFIRMED);

        Cars saved = repository.save(entity);

        return GenericResponse.success(Messages.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID id, CarsRequest request) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

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

        return GenericResponse.success(Messages.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> editBrent(UUID id, BrantCarsRequest request) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

        entity.setBrentStatus(BrentStatusEnum.UNCONFIRMED);
        entity.setBrentCarsTakePhotoPaths(request.getBrentCarsTakePhotoPaths());

        Cars saved = repository.save(entity);

        return GenericResponse.success(Messages.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> get(UUID id) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

        return GenericResponse.success(Messages.SUCCESS, toResponse(entity));
    }

    @Override
    public GenericResponse<?> getByDriverId(UUID driverId) {

        Cars entity = repository.findByDriverId(driverId)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

        return GenericResponse.success(Messages.SUCCESS, toResponse(entity));
    }

    @Override
    public GenericResponse<?> editStatus(UUID id, String status) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

        try {
            CarsStatusEnum statusEnum = CarsStatusEnum.valueOf(status);
            entity.setStatus(statusEnum);
            repository.save(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status value");
        }

        return GenericResponse.success(Messages.SUCCESS, entity.getId());
    }

    @Override
    public GenericResponse<?> editBrentStatus(UUID id, String brentStatus) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

        try {
            BrentStatusEnum brentStatusEnum = BrentStatusEnum.valueOf(brentStatus);
            entity.setBrentStatus(brentStatusEnum);
            repository.save(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid brent status value");
        }

        return GenericResponse.success(Messages.SUCCESS, entity.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID id) {

        Cars entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Messages.DATA_NOT_FOUND));

        entity.setStatus(CarsStatusEnum.DELETED);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.save(entity);

        return GenericResponse.success(Messages.DELETED_SUCCESSFULLY, null);
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

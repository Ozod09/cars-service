package uz.taxi.cars_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.taxi.cars_service.base.Messages;
import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.entity.CarsClass;
import uz.taxi.cars_service.entity.TypeOfService;
import uz.taxi.cars_service.enums.CarsCategoryStatusEnum;
import uz.taxi.cars_service.exception.BadRequestException;
import uz.taxi.cars_service.repository.CarsClassRepository;
import uz.taxi.cars_service.repository.TypeOfServiceRepository;
import uz.taxi.cars_service.rest.payload.req.carsclass.CarsClassRequest;
import uz.taxi.cars_service.rest.payload.res.ResPageable;
import uz.taxi.cars_service.rest.payload.res.carsclass.CarsClassResponse;
import uz.taxi.cars_service.rest.service.CarsClassService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarsClassServiceImpl implements CarsClassService {

    private final CarsClassRepository repository;
    private final TypeOfServiceRepository typeOfServiceRepository;

    @Override
    public GenericResponse<?> create(CarsClassRequest request) {
        TypeOfService typeOfService = typeOfServiceRepository.findById(request.getTypeOfServiceId())
                .orElseThrow(() -> new BadRequestException("Type of service not found"));

        CarsClass entity = new CarsClass();
        entity.setTypeOfService(typeOfService);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStartPrice(request.getStartPrice());
        entity.setOneKmPrice(request.getOneKmPrice());
        entity.setDaytimePrice(request.getDaytimePrice());
        entity.setEveningPrice(request.getEveningPrice());
        entity.setPaidWaitingTime(request.getPaidWaitingTime());
        entity.setStatus(CarsCategoryStatusEnum.ACTIVE);

        CarsClass saved = repository.save(entity);

        return GenericResponse.success(Messages.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID id, CarsClassRequest request) {
        CarsClass entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException(Messages.DATA_NOT_FOUND));

        TypeOfService typeOfService = typeOfServiceRepository.findById(request.getTypeOfServiceId())
                .orElseThrow(() -> new BadRequestException("Type of service not found"));

        entity.setTypeOfService(typeOfService);
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setStartPrice(request.getStartPrice());
        entity.setOneKmPrice(request.getOneKmPrice());
        entity.setDaytimePrice(request.getDaytimePrice());
        entity.setEveningPrice(request.getEveningPrice());
        entity.setPaidWaitingTime(request.getPaidWaitingTime());
        entity.setUpdatedAt(LocalDateTime.now());

        CarsClass saved = repository.save(entity);

        return GenericResponse.success(Messages.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> get(UUID id) {
        CarsClass entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException(Messages.DATA_NOT_FOUND));

        return GenericResponse.success(Messages.SUCCESS, toResponse(entity));
    }

    @Override
    public GenericResponse<?> getPage(String filter, UUID typeOfServiceId, int page, int size) {
        String normalizedFilter = normalizeFilter(filter);

        Page<CarsClass> entities = repository.findAllByFilter(
                normalizedFilter,
                typeOfServiceId,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        List<CarsClassResponse> list = entities.getContent().stream()
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
    public GenericResponse<?> editStatus(UUID id, String status) {
        CarsClass entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException(Messages.DATA_NOT_FOUND));

        try {
            CarsCategoryStatusEnum statusEnum = CarsCategoryStatusEnum.valueOf(status);
            entity.setStatus(statusEnum);
            repository.save(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status value");
        }

        return GenericResponse.success(Messages.SUCCESS, entity.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID id) {
        CarsClass entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException(Messages.DATA_NOT_FOUND));

        entity.setStatus(CarsCategoryStatusEnum.DELETED);
        repository.save(entity);

        return GenericResponse.success(Messages.DELETED_SUCCESSFULLY, null);
    }

    private CarsClassResponse toResponse(CarsClass entity) {
        CarsClassResponse response = new CarsClassResponse();
        response.setId(entity.getId());
        response.setTypeOfServiceId(entity.getTypeOfService().getId());
        response.setTypeOfServiceName(entity.getTypeOfService().getName());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setStartPrice(entity.getStartPrice());
        response.setOneKmPrice(entity.getOneKmPrice());
        response.setDaytimePrice(entity.getDaytimePrice());
        response.setEveningPrice(entity.getEveningPrice());
        response.setPaidWaitingTime(entity.getPaidWaitingTime());
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

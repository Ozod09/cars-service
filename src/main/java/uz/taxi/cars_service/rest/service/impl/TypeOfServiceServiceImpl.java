package uz.taxi.cars_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.taxi.cars_service.base.Message;
import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.entity.TypeOfService;
import uz.taxi.cars_service.enums.CarsCategoryStatusEnum;
import uz.taxi.cars_service.exception.BadRequestException;
import uz.taxi.cars_service.exception.CustomNotFoundException;
import uz.taxi.cars_service.repository.TypeOfServiceRepository;
import uz.taxi.cars_service.rest.payload.req.typeOfService.TypeOfServiceRequest;
import uz.taxi.cars_service.rest.payload.res.ResPageable;
import uz.taxi.cars_service.rest.payload.res.typeofservice.TypeOfServiceResponse;
import uz.taxi.cars_service.rest.service.TypeOfServiceService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeOfServiceServiceImpl implements TypeOfServiceService {

    private final TypeOfServiceRepository repository;


    @Override
    public GenericResponse<?> get(UUID id) {

        TypeOfService entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        return GenericResponse.success(Message.SUCCESS, toResponse(entity));
    }

    @Override
    public GenericResponse<?> getPage(UUID regionId, String filter, int page, int size) {

        String normalizedFilter = normalizeFilter(filter);

        Page<TypeOfService> entities = repository.findAllByFilter(
                regionId, normalizedFilter,
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        List<TypeOfServiceResponse> list = entities.getContent().stream()
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
    public GenericResponse<?> getList(UUID regionId, String filter) {

        List<TypeOfService> allByFilter = repository.findAllByFilter(regionId, filter);

        List<TypeOfServiceResponse> list = allByFilter.stream()
                .map(this::toResponse)
                .toList();


        return GenericResponse.success(Message.SUCCESS, list);
    }

    @Override
    public GenericResponse<?> create(TypeOfServiceRequest request) {

        TypeOfService entity = new TypeOfService();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
//        entity.setAttachmentPath(request.getAttachmentPath());
        entity.setRegionId(request.getRegionId());
        entity.setStatus(CarsCategoryStatusEnum.ACTIVE);

        TypeOfService saved = repository.save(entity);

        return GenericResponse.success(Message.SUCCESS, saved.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID id, TypeOfServiceRequest request) {

        TypeOfService entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setAttachmentPath(request.getAttachmentPath());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setRegionId(request.getRegionId());

        repository.save(entity);

        return GenericResponse.success(Message.SUCCESS, id);
    }

    @Override
    public GenericResponse<?> editStatus(UUID id, String status) {

        TypeOfService entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        try {
            CarsCategoryStatusEnum statusEnum = CarsCategoryStatusEnum.valueOf(status);
            entity.setStatus(statusEnum);
            entity.setUpdatedAt(LocalDateTime.now());
            repository.save(entity);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Message.INVALID_STATUS_VALUE);
        }

        return GenericResponse.success(Message.SUCCESS, entity.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID id) {

        TypeOfService entity = repository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Message.DATA_NOT_FOUND));

        entity.setStatus(CarsCategoryStatusEnum.DELETED);
        entity.setUpdatedAt(LocalDateTime.now());
        repository.save(entity);

        return GenericResponse.success(Message.DELETED_SUCCESSFULLY, null);
    }

    private TypeOfServiceResponse toResponse(TypeOfService entity) {

        TypeOfServiceResponse response = new TypeOfServiceResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setAttachmentPath(entity.getAttachmentPath());
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

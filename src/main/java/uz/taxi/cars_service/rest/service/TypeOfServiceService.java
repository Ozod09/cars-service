package uz.taxi.cars_service.rest.service;

import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.rest.payload.req.typeOfService.TypeOfServiceRequest;

import java.util.UUID;

public interface TypeOfServiceService {

    GenericResponse<?> create(TypeOfServiceRequest request);

    GenericResponse<?> edit(UUID id, TypeOfServiceRequest request);

    GenericResponse<?> get(UUID id);

    GenericResponse<?> getPage(UUID regionId, String filter, int page, int size);

    GenericResponse<?> getList(UUID regionId, String filter);

    GenericResponse<?> editStatus(UUID id, String status);

    GenericResponse<?> delete(UUID id);
}

package uz.taxi.cars_service.rest.service;

import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.rest.payload.req.carsClass.CarsClassRequest;

import java.util.UUID;

public interface CarsClassService {

    GenericResponse<?> create(CarsClassRequest request);

    GenericResponse<?> edit(UUID id, CarsClassRequest request);

    GenericResponse<?> get(UUID id);

    GenericResponse<?> getPage(String filter, UUID typeOfServiceId, int page, int size);

    GenericResponse<?> getList(UUID typeOfServiceId, String filter);

    GenericResponse<?> editStatus(UUID id, String status);

    GenericResponse<?> delete(UUID id);
}

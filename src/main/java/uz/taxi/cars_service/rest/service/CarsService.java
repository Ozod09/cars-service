package uz.taxi.cars_service.rest.service;

import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.rest.payload.req.cars.BrantCarsRequest;
import uz.taxi.cars_service.rest.payload.req.cars.CarsRequest;

import java.util.UUID;

public interface CarsService {

    GenericResponse<?> create(CarsRequest request);

    GenericResponse<?> edit(UUID id, CarsRequest request);

    GenericResponse<?> editBrent(UUID id, BrantCarsRequest request);

    GenericResponse<?> get(UUID id);

    GenericResponse<?> getByDriverId(UUID driverId);

    GenericResponse<?> getPage(String filter, UUID driverId, int page, int size);

    GenericResponse<?> editStatus(UUID id, String status);

    GenericResponse<?> editBrentStatus(UUID id, String brentStatus);

    GenericResponse<?> delete(UUID id);
}

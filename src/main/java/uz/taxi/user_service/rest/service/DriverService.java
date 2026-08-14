package uz.taxi.user_service.rest.service;

import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.rest.payload.req.StatusEditRequest;
import uz.taxi.user_service.rest.payload.req.driver.DriverRequest;

import java.util.UUID;

public interface DriverService {

    GenericResponse<?> create(DriverRequest driverRequest);

    GenericResponse<?> edit(UUID driverId, DriverRequest req);

    GenericResponse<?> get(UUID driverId);

    GenericResponse<?> getAll();

    GenericResponse<?> editStatus(StatusEditRequest request);

    GenericResponse<?> delete(UUID driverId);
}

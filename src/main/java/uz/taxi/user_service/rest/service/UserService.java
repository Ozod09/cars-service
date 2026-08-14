package uz.taxi.user_service.rest.service;

import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.rest.payload.req.StatusEditRequest;
import uz.taxi.user_service.rest.payload.req.user.UserRequest;

import java.util.UUID;

public interface UserService {

    GenericResponse<?> create(UserRequest userRequest);

    GenericResponse<?> edit(UUID userId, UserRequest req);

    GenericResponse<?> get(UUID userId);

//    GenericResponse<?> getAll();

    GenericResponse<?> editStatus(StatusEditRequest request);

    GenericResponse<?> delete(UUID userId);
}

package uz.taxi.user_service.rest.service;

import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.rest.payload.req.client.ClientRequest;

import java.util.UUID;

public interface ClientService {

    GenericResponse<?> create(ClientRequest clientRequest);

    GenericResponse<?> edit(UUID clientId, ClientRequest req);

    GenericResponse<?> get(UUID clientId);

//    GenericResponse<?> getAll();

    GenericResponse<?> editStatus(UUID clientId);

    GenericResponse<?> delete(UUID clientId);
}

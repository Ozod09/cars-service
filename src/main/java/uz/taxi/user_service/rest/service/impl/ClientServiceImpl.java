package uz.taxi.user_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.taxi.user_service.base.Messages;
import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.enums.UserStatusEnum;
import uz.taxi.user_service.exception.BadRequestException;
import uz.taxi.user_service.repository.ClientRepository;
import uz.taxi.user_service.rest.payload.req.client.ClientRequest;
import uz.taxi.user_service.rest.payload.res.client.ClientRes;
import uz.taxi.user_service.rest.service.ClientService;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public GenericResponse<?> create(ClientRequest request) {

        if (request.getId() == null) {
            throw new BadRequestException(Messages.CLIENT_ID_NOT_FOUND);
        }

        if (repository.existsByPhone(request.getPhone())) {
            throw new BadRequestException(Messages.PHONE_ALREADY_EXISTS);
        }

        Client client = new Client();
        client.setId(request.getId());
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setPhone(request.getPhone());
        client.setAttachmentPath(request.getAttachmentPath());
        client.setStatus(UserStatusEnum.ACTIVE);
        Client save = repository.save(client);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID clientId, ClientRequest req) {
        Client client = repository.findById(clientId).orElseThrow(() ->
                new BadRequestException(Messages.CLIENT_ID_NOT_FOUND));

        // Check phone uniqueness if changed
        if (!client.getPhone().equals(req.getPhone()) && repository.existsByPhone(req.getPhone())) {
            throw new BadRequestException(Messages.PHONE_ALREADY_EXISTS);
        }

        client.setFirstName(req.getFirstName());
        client.setLastName(req.getLastName());
        client.setPhone(req.getPhone());
        client.setAttachmentPath(req.getAttachmentPath());
        client.setUpdatedAt(LocalDateTime.now());

        Client save = repository.save(client);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> get(UUID clientId) {
        Client client = repository.findById(clientId).orElseThrow(() ->
                new BadRequestException(Messages.CLIENT_ID_NOT_FOUND));

        return GenericResponse.success(Messages.SUCCESS, clientRes(client));
    }

    private ClientRes clientRes(Client client) {
        ClientRes clientRes = new ClientRes();
        clientRes.setId(client.getId());
        clientRes.setFirstName(client.getFirstName());
        clientRes.setLastName(client.getLastName());
        clientRes.setPhone(client.getPhone());
        clientRes.setAttachmentPath(client.getAttachmentPath());
        clientRes.setStatus(clientRes.getStatus());
        return clientRes;
    }

//    @Override
//    public GenericResponse<?> getAll() {
//        List<Client> clients = repository.findAll();
//        return GenericResponse.success(Messages.SUCCESS, clients);
//    }

    @Override
    public GenericResponse<?> editStatus(UUID clientId) {
        Client client = repository.findById(clientId).orElseThrow(() ->
                new BadRequestException(Messages.CLIENT_ID_NOT_FOUND));

        if (client.getStatus() == UserStatusEnum.ACTIVE) {
            client.setStatus(UserStatusEnum.BLOCKED);
        } else {
            client.setStatus(UserStatusEnum.ACTIVE);
        }

        client.setUpdatedAt(LocalDateTime.now());
        Client save = repository.save(client);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID clientId) {
        Client client = repository.findById(clientId).orElseThrow(() ->
                new BadRequestException(Messages.CLIENT_ID_NOT_FOUND));

        client.setStatus(UserStatusEnum.DELETED);
        client.setUpdatedAt(LocalDateTime.now());
        repository.save(client);

        return GenericResponse.success(Messages.DELETED_SUCCESSFULLY, null);
    }
}

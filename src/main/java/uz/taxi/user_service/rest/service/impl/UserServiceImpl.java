package uz.taxi.user_service.rest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.taxi.user_service.base.Messages;
import uz.taxi.user_service.common.GenericResponse;
import uz.taxi.user_service.enums.UserStatusEnum;
import uz.taxi.user_service.exception.BadRequestException;
import uz.taxi.user_service.repository.UserRepository;
import uz.taxi.user_service.rest.payload.req.StatusEditRequest;
import uz.taxi.user_service.rest.payload.req.user.UserRequest;
import uz.taxi.user_service.rest.payload.res.user.UserRes;
import uz.taxi.user_service.rest.service.UserService;

import java.time.LocalDateTime;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GenericResponse<?> create(UserRequest request) {

        if (repository.existsByPhone(request.getPhone())) {
            throw new BadRequestException(Messages.PHONE_ALREADY_EXISTS);
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAttachmentPath(request.getAttachmentPath());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRegionId(request.getRegionId());
        user.setRoleName(request.getRoleName());
        user.setStatus(UserStatusEnum.ACTIVE);

        User save = repository.save(user);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> edit(UUID userId, UserRequest req) {
        User user = repository.findById(userId).orElseThrow(() ->
                new BadRequestException(Messages.USER_NOT_FOUND));

        // Check phone uniqueness if changed
        if (!user.getPhone().equals(req.getPhone()) && repository.existsByPhone(req.getPhone())) {
            throw new BadRequestException(Messages.PHONE_ALREADY_EXISTS);
        }

        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPhone(req.getPhone());
        user.setAttachmentPath(req.getAttachmentPath());
        user.setRegionId(req.getRegionId());
        user.setRoleName(req.getRoleName());
        user.setUpdatedAt(LocalDateTime.now());

        // Update password only if provided
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        User save = repository.save(user);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> get(UUID userId) {
        User user = repository.findById(userId).orElseThrow(() ->
                new BadRequestException(Messages.USER_NOT_FOUND));

        return GenericResponse.success(Messages.SUCCESS, userRes(user));
    }

    public UserRes userRes(User user){
        UserRes userRes = new UserRes();
        userRes.setId(user.getId());
        userRes.setFirstName(user.getFirstName());
        userRes.setLastName(user.getLastName());
        userRes.setPhone(user.getPhone());
        userRes.setAttachmentPath(user.getAttachmentPath());
        userRes.setRegionId(user.getRegionId());
        userRes.setRoleName(user.getRoleName());

        return userRes;
    }

//    @Override
//    public GenericResponse<?> getAll() {
//        List<User> users = repository.findAll();
//        return GenericResponse.success(Messages.SUCCESS, users);
//    }

    @Override
    public GenericResponse<?> editStatus(StatusEditRequest request) {
        User user = repository.findById(request.getId()).orElseThrow(() ->
                new BadRequestException(Messages.USER_NOT_FOUND));

        UserStatusEnum userStatusEnum = UserStatusEnum.valueOf(request.getUserStatus());
        user.setStatus(userStatusEnum);

        User save = repository.save(user);

        return GenericResponse.success(Messages.SUCCESS, save.getId());
    }

    @Override
    public GenericResponse<?> delete(UUID userId) {
        User user = repository.findById(userId).orElseThrow(() ->
                new BadRequestException(Messages.USER_NOT_FOUND));

        user.setStatus(UserStatusEnum.DELETED);
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);

        return GenericResponse.success(Messages.DELETED_SUCCESSFULLY, null);
    }
}

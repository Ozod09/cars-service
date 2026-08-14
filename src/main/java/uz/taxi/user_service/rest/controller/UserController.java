package uz.taxi.user_service.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.taxi.user_service.base.BaseURL;
import uz.taxi.user_service.rest.payload.req.StatusEditRequest;
import uz.taxi.user_service.rest.payload.req.user.UserRequest;
import uz.taxi.user_service.rest.service.UserService;

import java.util.UUID;


@RestController
@RequestMapping(BaseURL.API1 + BaseURL.USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> edit(@PathVariable UUID userId,
                                   @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.edit(userId, request));
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<?> get(@PathVariable UUID userId) {
//        return ResponseEntity.ok(userService.get(userId));
//    }
//
//    @GetMapping()
//    public ResponseEntity<?> getAll() {
//        return ResponseEntity.ok(userService.getAll());
//    }

    @PatchMapping("/{userId}/status")
    public ResponseEntity<?> editStatus(@RequestBody StatusEditRequest request ) {
        return ResponseEntity.ok(userService.editStatus(request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.delete(userId));
    }
}

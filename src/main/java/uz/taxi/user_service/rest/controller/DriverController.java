package uz.taxi.user_service.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.taxi.user_service.base.BaseURL;
import uz.taxi.user_service.rest.payload.req.StatusEditRequest;
import uz.taxi.user_service.rest.payload.req.driver.DriverRequest;
import uz.taxi.user_service.rest.service.DriverService;

import java.util.UUID;


@RestController
@RequestMapping(BaseURL.API1 + BaseURL.USER + BaseURL.DRIVER)
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(driverService.create(request));
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<?> edit(@PathVariable UUID driverId,
                                   @Valid @RequestBody DriverRequest request) {
        return ResponseEntity.ok(driverService.edit(driverId, request));
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<?> get(@PathVariable UUID driverId) {
        return ResponseEntity.ok(driverService.get(driverId));
    }

    @GetMapping()
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(driverService.getAll());
    }

    @PatchMapping("/status")
    public ResponseEntity<?> editStatus(@RequestBody StatusEditRequest request) {
        return ResponseEntity.ok(driverService.editStatus(request));
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<?> delete(@PathVariable UUID driverId) {
        return ResponseEntity.ok(driverService.delete(driverId));
    }
}

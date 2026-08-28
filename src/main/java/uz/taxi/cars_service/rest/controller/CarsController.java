package uz.taxi.cars_service.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.taxi.cars_service.base.BaseURL;
import uz.taxi.cars_service.common.GenericResponse;
import uz.taxi.cars_service.rest.payload.req.cars.BrantCarsRequest;
import uz.taxi.cars_service.rest.payload.req.cars.CarsRequest;
import uz.taxi.cars_service.rest.service.CarsService;

import java.util.UUID;

@RestController
@RequestMapping(BaseURL.API1 + BaseURL.CARS)
@RequiredArgsConstructor
public class CarsController {

    private final CarsService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CarsRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-driver/{driverId}")
    public ResponseEntity<?> getByDriverId(@PathVariable UUID driverId) {
        return ResponseEntity.ok(service.getByDriverId(driverId));
    }

    @GetMapping
    public ResponseEntity<?> getPage(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) UUID driverId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getPage(filter, driverId, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable UUID id,
            @Valid @RequestBody CarsRequest request
    ) {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @PutMapping("/brent/{id}")
    public ResponseEntity<?> editBrent(
            @PathVariable UUID id,
            @Valid @RequestBody BrantCarsRequest request
    ) {
        return ResponseEntity.ok(service.editBrent(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> editStatus(
            @PathVariable UUID id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(service.editStatus(id, status));
    }

    @PatchMapping("/{id}/brent-status")
    public ResponseEntity<?> editBrentStatus(
            @PathVariable UUID id,
            @RequestParam String brentStatus
    ) {
        return ResponseEntity.ok(service.editBrentStatus(id, brentStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @PostMapping(BaseURL.START_SHIFT)
    public GenericResponse<?> startShift() {
        return service.startShift();
    }

    @PostMapping(BaseURL.END_SHIFT)
    public GenericResponse<?> endShift() {
        return service.endShift();
    }

    @PutMapping(BaseURL.LOCATION)
    public GenericResponse<?> carLocationUpdate(
            @RequestParam Long carId,
            @RequestParam double lat,
            @RequestParam double lon)
    {
        return service.carLocationUpdate(carId, lat, lon);
    }
}

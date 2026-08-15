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
import uz.taxi.cars_service.rest.payload.req.typeOfService.TypeOfServiceRequest;
import uz.taxi.cars_service.rest.service.TypeOfServiceService;

import java.util.UUID;

@RestController
@RequestMapping(BaseURL.API1 + BaseURL.CARS + BaseURL.TYPEOFSERVICE)
@RequiredArgsConstructor
public class TypeOfServiceController {

    private final TypeOfServiceService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TypeOfServiceRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable UUID id) {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping
    public ResponseEntity<?> getPage(
            @RequestParam UUID regionId,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(service.getPage(regionId, filter, page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @PathVariable UUID id,
            @Valid @RequestBody TypeOfServiceRequest request
    ) {
        return ResponseEntity.ok(service.edit(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> editStatus(
            @PathVariable UUID id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(service.editStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(service.delete(id));
    }
}

package uz.taxi.user_service.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.taxi.user_service.base.BaseURL;
import uz.taxi.user_service.rest.payload.req.client.ClientRequest;
import uz.taxi.user_service.rest.service.ClientService;

import java.util.UUID;


@RestController
@RequestMapping(BaseURL.API1 + BaseURL.USER + BaseURL.CLIENT)
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok(clientService.create(request));
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<?> edit(@PathVariable UUID clientId,
                                   @Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok(clientService.edit(clientId, request));
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> get(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.get(clientId));
    }

//    @GetMapping()
//    public ResponseEntity<?> getAll() {
//        return ResponseEntity.ok(clientService.getAll());
//    }

    @PatchMapping("/{clientId}/status")
    public ResponseEntity<?> editStatus(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.editStatus(clientId));
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<?> delete(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientService.delete(clientId));
    }
}

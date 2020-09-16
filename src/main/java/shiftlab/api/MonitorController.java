package shiftlab.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiftlab.model.Monitor;
import shiftlab.model.ProductList;
import shiftlab.service.MonitorService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v001/products/monitors")
public class MonitorController {
    private final MonitorService service;

    @PostMapping
    public ResponseEntity<Monitor> createMonitor(
            @RequestBody Monitor monitor) {
        Monitor result = service.createMonitor(monitor);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{monitorId}")
    public ResponseEntity<Monitor> updateMonitor(
            @PathVariable String monitorId,
            @RequestBody Monitor monitor) {
        Monitor updatedMonitor = service.updateMonitor(monitor, monitorId);
        return ResponseEntity.ok(updatedMonitor);
    }

    @GetMapping
    public ResponseEntity<ProductList<Monitor>> getAllMonitors() {
        ProductList<Monitor> monitorProductList = service.getAllMonitors();
        return ResponseEntity.ok(monitorProductList);
    }

    @GetMapping("/{monitorId}")
    public ResponseEntity<Monitor> getMonitorById(
            @PathVariable String monitorId) {
        Monitor monitor = service.getMonitorById(monitorId);
        return ResponseEntity.ok(monitor);
    }
}

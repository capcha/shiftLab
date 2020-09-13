package shift.lab.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.models.Monitor;
import shift.lab.models.ProductList;
import shift.lab.services.MonitorService;

@RestController
@RequestMapping(path = "/api/v001/products/monitors")
public class MonitorController {
    private MonitorService service;

    public MonitorController(MonitorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Monitor> createMonitor(
            @RequestBody Monitor monitor) {
        Monitor result = service.createMonitor(monitor);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/{monitorId}")
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

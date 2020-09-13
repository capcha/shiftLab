package shift.lab.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.models.HardDrive;
import shift.lab.models.ProductList;
import shift.lab.services.HardDriveService;

@RestController
@RequestMapping(path = "/api/v001/products/hardDrives")
public class HardDriveController {
    private HardDriveService service;

    public HardDriveController(HardDriveService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HardDrive> createHardDrive(
            @RequestBody HardDrive hardDrive) {
        HardDrive result = service.createHardDrive(hardDrive);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/{hardDriveId}")
    public ResponseEntity<HardDrive> updateHardDrive(
            @PathVariable String hardDriveId,
            @RequestBody HardDrive hardDrive) {
        HardDrive updatedHardDrive = service.updateHardDrive(hardDrive, hardDriveId);
        return ResponseEntity.ok(updatedHardDrive);
    }

    @GetMapping
    public ResponseEntity<ProductList<HardDrive>> getAllHardDrives() {
        ProductList<HardDrive> hardDriveProductList = service.getAllHardDrives();
        return ResponseEntity.ok(hardDriveProductList);
    }

    @GetMapping("/{hardDriveId}")
    public ResponseEntity<HardDrive> getHardDriveById(
            @PathVariable String hardDriveId) {
        HardDrive hardDrive = service.getHardDriveById(hardDriveId);
        return ResponseEntity.ok(hardDrive);
    }
}

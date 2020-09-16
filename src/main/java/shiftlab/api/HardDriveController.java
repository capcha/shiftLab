package shiftlab.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiftlab.model.HardDrive;
import shiftlab.model.ProductList;
import shiftlab.service.HardDriveService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v001/products/hardDrives")
public class HardDriveController {
    private final HardDriveService service;

    @PostMapping
    public ResponseEntity<HardDrive> createHardDrive(
            @RequestBody HardDrive hardDrive) {
        HardDrive result = service.createHardDrive(hardDrive);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{hardDriveId}")
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

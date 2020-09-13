package shift.lab.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.models.DesktopComputer;
import shift.lab.models.ProductList;
import shift.lab.services.DesktopComputerService;

@RestController
@RequestMapping(path = "/api/v001/products/desktopComputers")
public class DesktopComputerController {
    private DesktopComputerService service;

    public DesktopComputerController(DesktopComputerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<DesktopComputer> createDesktopComputer(
            @RequestBody DesktopComputer desktopComputer) {
        DesktopComputer result = service.createDesktopComputer(desktopComputer);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/{desktopComputerId}")
    public ResponseEntity<DesktopComputer> updateDesktopComputer(
            @PathVariable String desktopComputerId,
            @RequestBody DesktopComputer desktopComputer) {
        DesktopComputer updatedDesktopComputer = service.updateDesktopComputer(desktopComputer, desktopComputerId);
        return ResponseEntity.ok(updatedDesktopComputer);
    }

    @GetMapping
    public ResponseEntity<ProductList<DesktopComputer>> getAllDesktopComputers() {
        ProductList<DesktopComputer> desktopComputerProductList = service.getAllDesktopComputers();
        return ResponseEntity.ok(desktopComputerProductList);
    }

    @GetMapping("/{desktopComputerId}")
    public ResponseEntity<DesktopComputer> getDesktopComputerById(
            @PathVariable String desktopComputerId) {
        DesktopComputer desktopComputer = service.getDesktopComputerById(desktopComputerId);
        return ResponseEntity.ok(desktopComputer);
    }
}

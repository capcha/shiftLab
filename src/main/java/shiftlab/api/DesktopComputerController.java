package shiftlab.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shiftlab.model.DesktopComputer;
import shiftlab.model.ProductList;
import shiftlab.service.DesktopComputerService;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v001/products/desktopComputers")
public class DesktopComputerController {
    private final DesktopComputerService service;

    @PostMapping
    public ResponseEntity<DesktopComputer> create(
            @RequestBody DesktopComputer desktopComputer) {
        DesktopComputer result = service.createDesktopComputer(desktopComputer);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{desktopComputerId}")
    public ResponseEntity<DesktopComputer> update(
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

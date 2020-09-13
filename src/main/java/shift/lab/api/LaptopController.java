package shift.lab.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.models.Laptop;
import shift.lab.models.ProductList;
import shift.lab.services.LaptopService;

@RestController
@RequestMapping(path = "/api/v001/products/laptops")
public class LaptopController {
    private LaptopService service;

    public LaptopController(LaptopService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Laptop> createLaptop(
            @RequestBody Laptop laptop) {
        Laptop result = service.createLaptop(laptop);
        return ResponseEntity.ok(result);
    }


    @PatchMapping("/{laptopId}")
    public ResponseEntity<Laptop> updateLaptop(
            @PathVariable String laptopId,
            @RequestBody Laptop laptop) {
        Laptop updatedLaptop = service.updateLaptop(laptop, laptopId);
        return ResponseEntity.ok(updatedLaptop);
    }

    @GetMapping
    public ResponseEntity<ProductList<Laptop>> getAllLaptops() {
        ProductList<Laptop> laptopProductList = service.getAllLaptops();
        return ResponseEntity.ok(laptopProductList);
    }

    @GetMapping("/{laptopId}")
    public ResponseEntity<Laptop> getLaptopById(
            @PathVariable String laptopId) {
        Laptop laptop = service.getLaptopById(laptopId);
        return ResponseEntity.ok(laptop);
    }
}

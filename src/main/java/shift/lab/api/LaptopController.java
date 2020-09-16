package shift.lab.api;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shift.lab.model.ProductList;
import shift.lab.service.LaptopService;
import shift.lab.model.Laptop;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v001/products/laptops")
public class LaptopController {
    private final LaptopService service;

    @PostMapping
    public ResponseEntity<Laptop> createLaptop(
            @RequestBody Laptop laptop) {
        Laptop result = service.createLaptop(laptop);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/{laptopId}")
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

package shiftlab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shiftlab.model.Laptop;
import shiftlab.model.ProductList;
import shiftlab.dao.LaptopDAO;

@Service
@AllArgsConstructor
public class LaptopService {
    private final LaptopDAO laptopDAO;

    public Laptop createLaptop(Laptop laptop) {
        return laptopDAO.create(laptop);
    }

    public Laptop updateLaptop(Laptop laptop, String laptopId) {
        return laptopDAO.update(laptop, laptopId);
    }

    public ProductList<Laptop> getAllLaptops() {
        return laptopDAO.getAll();
    }

    public Laptop getLaptopById(String laptopId) {
        return laptopDAO.getById(laptopId);
    }
}

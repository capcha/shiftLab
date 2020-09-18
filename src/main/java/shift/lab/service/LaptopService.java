package shift.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shift.lab.exception.NotFoundException;
import shift.lab.model.Laptop;
import shift.lab.model.ProductList;
import shift.lab.dao.LaptopDAO;

@Service
@AllArgsConstructor
public class LaptopService {
    private final LaptopDAO laptopDAO;

    public Laptop createLaptop(Laptop laptop) {
        return laptopDAO.create(laptop);
    }

    public Laptop updateLaptop(Laptop laptop, String laptopId) {
        final Laptop update = laptopDAO.update(laptop, laptopId);

        if (update == null) {
            throw new NotFoundException();
        }

        return update;
    }

    public ProductList<Laptop> getAllLaptops() {
        return laptopDAO.getAll();
    }

    public Laptop getLaptopById(String laptopId) {
        final Laptop laptopById = laptopDAO.getById(laptopId);

        if (laptopById == null) {
            throw new NotFoundException();
        }

        return laptopById;
    }
}

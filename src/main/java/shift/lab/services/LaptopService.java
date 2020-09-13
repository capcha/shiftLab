package shift.lab.services;

import org.springframework.stereotype.Service;
import shift.lab.models.Laptop;
import shift.lab.models.ProductList;
import shift.lab.repositories.DatabaseLaptopRepository;

@Service
public class LaptopService {
    private final DatabaseLaptopRepository databaseLaptopRepository;

    public LaptopService(DatabaseLaptopRepository databaseLaptopRepository) {
        this.databaseLaptopRepository = databaseLaptopRepository;
    }

    public Laptop createLaptop(Laptop laptop) {return databaseLaptopRepository.createLaptop(laptop);}
    public Laptop updateLaptop(Laptop laptop, String laptopId) {return databaseLaptopRepository.updateLaptop(laptop, laptopId);}
    public ProductList<Laptop> getAllLaptops() {return databaseLaptopRepository.getAllLaptops();}
    public Laptop getLaptopById(String laptopId) {return databaseLaptopRepository.getLaptopById(laptopId);}

}

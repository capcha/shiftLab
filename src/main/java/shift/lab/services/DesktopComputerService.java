package shift.lab.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import shift.lab.models.DesktopComputer;
import shift.lab.models.ProductList;
import shift.lab.repositories.DatabaseDesktopComputerRepository;

@Service
public class DesktopComputerService {
    private final DatabaseDesktopComputerRepository databaseDesktopComputerRepository;

    public DesktopComputerService(DatabaseDesktopComputerRepository databaseDesktopComputerRepository) {
        this.databaseDesktopComputerRepository = databaseDesktopComputerRepository;
    }

    public DesktopComputer createDesktopComputer(DesktopComputer desktopComputer) {return databaseDesktopComputerRepository.createDesktopComputer(desktopComputer);}
    public DesktopComputer updateDesktopComputer(DesktopComputer desktopComputer, String desktopComputerId) {return databaseDesktopComputerRepository.updateDesktopComputer(desktopComputer, desktopComputerId);}
    public ProductList<DesktopComputer> getAllDesktopComputers() {return databaseDesktopComputerRepository.getAllDesktopComputers();}
    public DesktopComputer getDesktopComputerById(String desktopId) {return databaseDesktopComputerRepository.getDesktopComputerById(desktopId);}

}

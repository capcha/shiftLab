package shift.lab.services;

import org.springframework.stereotype.Service;
import shift.lab.models.HardDrive;
import shift.lab.models.ProductList;
import shift.lab.repositories.DatabaseHardDriveRepository;

@Service
public class HardDriveService {
    private final DatabaseHardDriveRepository databaseHardDriveRepository;

    public HardDriveService(DatabaseHardDriveRepository databaseHardDriveRepository) {
        this.databaseHardDriveRepository = databaseHardDriveRepository;
    }

    public HardDrive createHardDrive(HardDrive hardDrive) {return databaseHardDriveRepository.createHardDrive(hardDrive);}
    public HardDrive updateHardDrive(HardDrive hardDrive, String hardDriveId) {return databaseHardDriveRepository.updateHardDrive(hardDrive, hardDriveId);}
    public ProductList<HardDrive> getAllHardDrives() {return databaseHardDriveRepository.getAllHardDrives();}
    public HardDrive getHardDriveById(String hardDriveId) {return databaseHardDriveRepository.getHardDriveById(hardDriveId);}

}

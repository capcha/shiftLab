package shift.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shift.lab.dao.HardDriveDAO;
import shift.lab.model.HardDrive;
import shift.lab.model.ProductList;

@Service
@AllArgsConstructor
public class HardDriveService {
    private final HardDriveDAO hardDriveDAO;

    public HardDrive createHardDrive(HardDrive hardDrive) {
        return hardDriveDAO.create(hardDrive);
    }

    public HardDrive updateHardDrive(HardDrive hardDrive, String hardDriveId) {
        return hardDriveDAO.update(hardDrive, hardDriveId);
    }

    public ProductList<HardDrive> getAllHardDrives() {
        return hardDriveDAO.getAll();
    }

    public HardDrive getHardDriveById(String hardDriveId) {
        return hardDriveDAO.getById(hardDriveId);
    }
}

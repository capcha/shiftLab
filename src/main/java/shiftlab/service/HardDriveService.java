package shiftlab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shiftlab.model.HardDrive;
import shiftlab.model.ProductList;
import shiftlab.dao.HardDriveDAO;

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

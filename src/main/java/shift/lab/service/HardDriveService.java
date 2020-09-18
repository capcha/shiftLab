package shift.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shift.lab.dao.HardDriveDAO;
import shift.lab.exception.NotFoundException;
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
        HardDrive update = hardDriveDAO.update(hardDrive, hardDriveId);

        if (update == null) {
            throw new NotFoundException();
        }

        return update;
    }

    public ProductList<HardDrive> getAllHardDrives() {
        return hardDriveDAO.getAll();
    }

    public HardDrive getHardDriveById(String hardDriveId) {
        final HardDrive hardDrive = hardDriveDAO.getById(hardDriveId);

        if (hardDrive == null) {
            throw new NotFoundException();
        }

        return hardDrive;
    }
}

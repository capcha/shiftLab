package shiftlab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shiftlab.model.DesktopComputer;
import shiftlab.model.ProductList;
import shiftlab.dao.DesktopComputerDAO;

@Service
@AllArgsConstructor
public class DesktopComputerService {
    private final DesktopComputerDAO desktopComputerDAO;

    public DesktopComputer createDesktopComputer(DesktopComputer desktopComputer) {
        return desktopComputerDAO.create(desktopComputer);
    }

    public DesktopComputer updateDesktopComputer(DesktopComputer desktopComputer, String desktopComputerId) {
        return desktopComputerDAO.update(desktopComputer, desktopComputerId);
    }

    public ProductList<DesktopComputer> getAllDesktopComputers() {
        return desktopComputerDAO.getAll();
    }

    public DesktopComputer getDesktopComputerById(String desktopId) {
        return desktopComputerDAO.getById(desktopId);
    }
}

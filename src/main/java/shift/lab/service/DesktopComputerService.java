package shift.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shift.lab.dao.DesktopComputerDAO;
import shift.lab.model.ProductList;
import shift.lab.model.DesktopComputer;

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

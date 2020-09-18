package shift.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shift.lab.dao.MonitorDAO;
import shift.lab.exception.NotFoundException;
import shift.lab.model.ProductList;
import shift.lab.model.Monitor;

@Service
@AllArgsConstructor
public class MonitorService {
    private final MonitorDAO monitorDAO;

    public Monitor createMonitor(Monitor monitor) {
        return monitorDAO.create(monitor);
    }

    public Monitor updateMonitor(Monitor monitor, String monitorId) {
        final Monitor update = monitorDAO.update(monitor, monitorId);

        if (update == null) {
            throw new NotFoundException();
        }

        return update;
    }

    public ProductList<Monitor> getAllMonitors() {
        return monitorDAO.getAll();
    }

    public Monitor getMonitorById(String monitorId) {
        final Monitor monitorById = monitorDAO.getById(monitorId);

        if (monitorById == null) {
            throw new NotFoundException();
        }

        return monitorById;
    }
}

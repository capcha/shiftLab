package shift.lab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shift.lab.dao.MonitorDAO;
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
        return monitorDAO.update(monitor, monitorId);
    }

    public ProductList<Monitor> getAllMonitors() {
        return monitorDAO.getAll();
    }

    public Monitor getMonitorById(String monitorId) {
        return monitorDAO.getById(monitorId);
    }
}

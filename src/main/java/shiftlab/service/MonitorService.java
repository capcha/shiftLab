package shiftlab.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shiftlab.model.Monitor;
import shiftlab.model.ProductList;
import shiftlab.dao.MonitorDAO;

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

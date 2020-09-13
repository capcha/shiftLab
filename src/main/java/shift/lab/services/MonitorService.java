package shift.lab.services;

import org.springframework.stereotype.Service;
import shift.lab.models.Monitor;
import shift.lab.models.ProductList;
import shift.lab.repositories.DatabaseMonitorRepository;

@Service
public class MonitorService {
    private final DatabaseMonitorRepository databaseMonitorRepository;

    public MonitorService(DatabaseMonitorRepository databaseMonitorRepository) {
        this.databaseMonitorRepository = databaseMonitorRepository;
    }

    public Monitor createMonitor(Monitor monitor) {return databaseMonitorRepository.createMonitor(monitor);}
    public Monitor updateMonitor(Monitor monitor, String monitorId) {return databaseMonitorRepository.updateMonitor(monitor, monitorId);}
    public ProductList<Monitor> getAllMonitors() {return databaseMonitorRepository.getAllMonitors();}
    public Monitor getMonitorById(String monitorId) {return databaseMonitorRepository.getMonitorById(monitorId);}

}

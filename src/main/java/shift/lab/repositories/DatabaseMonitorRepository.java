package shift.lab.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.exceptions.NotFoundException;
import shift.lab.models.Monitor;
import shift.lab.models.ProductList;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "true")
public class DatabaseMonitorRepository {

    private NamedParameterJdbcTemplate jdbcMonitorTemplate;

    public DatabaseMonitorRepository(NamedParameterJdbcTemplate jdbcMonitorTemplate) {
        this.jdbcMonitorTemplate = jdbcMonitorTemplate;
    }

    @PostConstruct
    public void initialize() {
        String createGenerateIdSequenceSql = "create sequence MONITOR_ID_GENERATOR";

        String createTableSql = "create table MONITORS (" +
                "MONITOR_ID  VARCHAR(64) default MONITOR_ID_GENERATOR.nextval," +
                "SERIAL_NUMBER     VARCHAR(128)," +
                "MANUFACTURER  VARCHAR(64)," +
                "PRICE    DECIMAL(20, 2)," +
                "CURRENCY VARCHAR(64)," +
                "AMOUNT    DOUBLE," +
                "DIAGONAL DOUBLE" +
                ");";

        jdbcMonitorTemplate.update(createGenerateIdSequenceSql, new MapSqlParameterSource());
        jdbcMonitorTemplate.update(createTableSql, new MapSqlParameterSource());
    }

    public Monitor createMonitor(Monitor monitor) {

        String insertMonitorSql = "insert into MONITORS (SERIAL_NUMBER, MANUFACTURER, " +
                "PRICE, CURRENCY, AMOUNT, DIAGONAL) " +
                "values (:serialNumber, :manufacturer, :price, :currency, :amount, :diagonal" +
                ")";

        MapSqlParameterSource monitorParams = new MapSqlParameterSource()
                .addValue("serialNumber", monitor.getSerialNumber())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("price", monitor.getPrice().getValue())
                .addValue("currency", monitor.getPrice().getCurrency())
                .addValue("amount", monitor.getAmount())
                .addValue("diagonal", monitor.getDiagonal());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcMonitorTemplate.update(insertMonitorSql, monitorParams, generatedKeyHolder);

        String productId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("MONITOR_ID").toString();

        return new Monitor(productId,
                monitor.getSerialNumber(),
                monitor.getManufacturer(),
                monitor.getPrice(),
                monitor.getAmount(),
                monitor.getDiagonal()
        );

    }

    public Monitor updateMonitor(Monitor monitor, String monitorId) {
        String fetchSql = "select MONITOR_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, DIAGONAL " +
                "from MONITORS where MONITOR_ID=:monitorId";

        MapSqlParameterSource checkMonitorParams = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

        Monitor monitorQuery = jdbcMonitorTemplate.queryForObject(fetchSql, checkMonitorParams, new MonitorRowMapper());

        if (monitorQuery == null) {
            throw new NotFoundException();
        }

        String updateMonitorSql = "update MONITORS " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "DIAGONAL=:diagonal " +
                "where MONITOR_ID=:monitorId";

        MapSqlParameterSource monitorParams = new MapSqlParameterSource()
                .addValue("monitorId", monitorId)
                .addValue("serialNumber", monitor.getSerialNumber())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("price", monitor.getPrice().getValue())
                .addValue("currency", monitor.getPrice().getCurrency())
                .addValue("amount", monitor.getAmount())
                .addValue("diagonal", monitor.getDiagonal());

        jdbcMonitorTemplate.update(updateMonitorSql, monitorParams);

        return new Monitor(monitorId,
                monitor.getSerialNumber(),
                monitor.getManufacturer(),
                monitor.getPrice(),
                monitor.getAmount(),
                monitor.getDiagonal()
        );

    }

    public ProductList<Monitor> getAllMonitors() {
        String fetchSql = "select MONITOR_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, DIAGONAL " +
                "from MONITORS";

        List<Monitor> monitors = jdbcMonitorTemplate.query(fetchSql, new MonitorRowMapper());

        return new ProductList<Monitor>(monitors);
    }

    public Monitor getMonitorById(String monitorId) {
        String fetchSql = "select MONITOR_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, DIAGONAL " +
                "from MONITORS where MONITOR_ID=:monitorId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

        Monitor monitor = jdbcMonitorTemplate.queryForObject(fetchSql, params, new MonitorRowMapper());

        if (monitor == null) {
            throw new NotFoundException();
        }

        return monitor;
    }
}

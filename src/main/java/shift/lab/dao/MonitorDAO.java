package shift.lab.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.model.ProductList;
import shift.lab.exception.NotFoundException;
import shift.lab.model.Monitor;
import shift.lab.rowmapper.MonitorRowMapper;

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class MonitorDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String insertSql = "insert into MONITORS (SERIAL_NUMBER, MANUFACTURER, " +
            "PRICE, CURRENCY, AMOUNT, DIAGONAL) " +
            "values (:serialNumber, :manufacturer, :price, :currency, :amount, :diagonal" +
            ")";

    private static final String getByIdSql = "select * from MONITORS where MONITOR_ID=:monitorId";

    private static final String updateSql = "update MONITORS " +
            "set SERIAL_NUMBER=:serialNumber, " +
            "MANUFACTURER=:manufacturer, " +
            "PRICE=:price, " +
            "CURRENCY=:currency, " +
            "AMOUNT=:amount, " +
            "DIAGONAL=:diagonal " +
            "where MONITOR_ID=:monitorId";

    private static final String getAllSql = "select * from MONITORS";

    public Monitor create(Monitor monitor) {
        MapSqlParameterSource monitorParams = new MapSqlParameterSource()
                .addValue("serialNumber", monitor.getSerialNumber())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("price", monitor.getPrice().getValue())
                .addValue("currency", monitor.getPrice().getCurrency())
                .addValue("amount", monitor.getAmount())
                .addValue("diagonal", monitor.getDiagonal());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, monitorParams, generatedKeyHolder);

        String monitorId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("MONITOR_ID").toString();

        return new Monitor(monitorId,
                monitor.getSerialNumber(),
                monitor.getManufacturer(),
                monitor.getPrice(),
                monitor.getAmount(),
                monitor.getDiagonal()
        );
    }

    public Monitor update(Monitor monitor, String monitorId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

        List<Monitor> monitorQuery = jdbcTemplate.query(getByIdSql, params, new MonitorRowMapper());

        if (monitorQuery.size() == 0) {
            throw new NotFoundException();
        }

        MapSqlParameterSource monitorParams = new MapSqlParameterSource()
                .addValue("monitorId", monitorId)
                .addValue("serialNumber", monitor.getSerialNumber())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("price", monitor.getPrice().getValue())
                .addValue("currency", monitor.getPrice().getCurrency())
                .addValue("amount", monitor.getAmount())
                .addValue("diagonal", monitor.getDiagonal());

        jdbcTemplate.update(updateSql, monitorParams);

        return jdbcTemplate.query(getByIdSql, params, new MonitorRowMapper()).get(0);
    }

    public ProductList<Monitor> getAll() {
        List<Monitor> monitors = jdbcTemplate.query(getAllSql, new MonitorRowMapper());

        return new ProductList<>(monitors);
    }

    public Monitor getById(String monitorId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

        List<Monitor> monitorQuery = jdbcTemplate.query(getByIdSql, params, new MonitorRowMapper());

        if (monitorQuery.size() == 0) {
            throw new NotFoundException();
        }

        return monitorQuery.get(0);
    }
}

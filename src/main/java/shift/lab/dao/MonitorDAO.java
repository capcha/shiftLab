package shift.lab.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.model.Monitor;
import shift.lab.model.Price;
import shift.lab.model.ProductList;
import shift.lab.rowmapper.MonitorRowMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MonitorDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final MonitorRowMapper monitorRowMapper;

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
        String value = Optional.ofNullable(monitor.getPrice())
                .map((price) -> price.getValue().toString())
                .orElse(null);

        String currency = Optional.ofNullable(monitor.getPrice())
                .map(Price::getCurrency)
                .orElse(null);

        MapSqlParameterSource monitorParams = new MapSqlParameterSource()
                .addValue("serialNumber", monitor.getSerialNumber())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("price", value)
                .addValue("currency", currency)
                .addValue("amount", monitor.getAmount())
                .addValue("diagonal", monitor.getDiagonal());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, monitorParams, generatedKeyHolder);

        String monitorId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("MONITOR_ID").toString();

        return getById(monitorId);
    }

    public Monitor update(Monitor monitor, String monitorId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

        List<Monitor> monitorQuery = jdbcTemplate.query(getByIdSql, params, monitorRowMapper);

        if (monitorQuery.isEmpty()) {
            return null;
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

        return jdbcTemplate.query(getByIdSql, params, monitorRowMapper).get(0);
    }

    public ProductList<Monitor> getAll() {
        List<Monitor> monitors = jdbcTemplate.query(getAllSql, monitorRowMapper);

        return new ProductList<>(monitors);
    }

    public Monitor getById(String monitorId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

        List<Monitor> monitorQuery = jdbcTemplate.query(getByIdSql, params, monitorRowMapper);

        if (monitorQuery.isEmpty()) {
            return null;
        }

        return monitorQuery.get(0);
    }
}

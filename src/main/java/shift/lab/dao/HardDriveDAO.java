package shift.lab.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.model.HardDrive;
import shift.lab.model.Price;
import shift.lab.model.ProductList;
import shift.lab.rowmapper.HardDriveRowMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HardDriveDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final HardDriveRowMapper hardDriveRowMapper;

    private static final String insertSql = "insert into HDDS (SERIAL_NUMBER, MANUFACTURER, " +
            "PRICE, CURRENCY, AMOUNT, VOLUME) " +
            "values (:serialNumber, :manufacturer, :price, :currency, :amount, :volume" +
            ")";

    private static final String getByIdSql = "select * from HDDS where HDD_ID=:hardDriveId";

    private static final String updateSql = "update HDDS " +
            "set SERIAL_NUMBER=:serialNumber, " +
            "MANUFACTURER=:manufacturer, " +
            "PRICE=:price, " +
            "CURRENCY=:currency, " +
            "AMOUNT=:amount, " +
            "VOLUME=:volume " +
            "where HDD_ID=:hardDriveId";

    private static final String getAllSql = "select * from HDDS";

    public HardDrive create(HardDrive hardDrive) {
        String value = Optional.ofNullable(hardDrive.getPrice())
                .map((price) -> price.getValue().toString())
                .orElse(null);

        String currency = Optional.ofNullable(hardDrive.getPrice())
                .map(Price::getCurrency)
                .orElse(null);

        MapSqlParameterSource hardDriveParams = new MapSqlParameterSource()
                .addValue("serialNumber", hardDrive.getSerialNumber())
                .addValue("manufacturer", hardDrive.getManufacturer())
                .addValue("price", value)
                .addValue("currency", currency)
                .addValue("amount", hardDrive.getAmount())
                .addValue("volume", hardDrive.getVolume());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, hardDriveParams, generatedKeyHolder);

        String hardDriveId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("HDD_ID").toString();

        return getById(hardDriveId);
    }

    public HardDrive update(HardDrive hardDrive, String hardDriveId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId);

        List<HardDrive> hardDriveQuery = jdbcTemplate.query(getByIdSql, params, hardDriveRowMapper);

        if (hardDriveQuery.isEmpty()) {
            return null;
        }

        MapSqlParameterSource hardDriveParams = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId)
                .addValue("serialNumber", hardDrive.getSerialNumber())
                .addValue("manufacturer", hardDrive.getManufacturer())
                .addValue("price", hardDrive.getPrice().getValue())
                .addValue("currency", hardDrive.getPrice().getCurrency())
                .addValue("amount", hardDrive.getAmount())
                .addValue("volume", hardDrive.getVolume());

        jdbcTemplate.update(updateSql, hardDriveParams);

        return jdbcTemplate.query(getByIdSql, params, hardDriveRowMapper).get(0);
    }

    public ProductList<HardDrive> getAll() {
        List<HardDrive> hardDrives = jdbcTemplate.query(getAllSql, hardDriveRowMapper);

        return new ProductList<>(hardDrives);
    }

    public HardDrive getById(String hardDriveId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId);

        List<HardDrive> hardDriveQuery = jdbcTemplate.query(getByIdSql, params, hardDriveRowMapper);

        if (hardDriveQuery.isEmpty()) {
            return null;
        }

        return hardDriveQuery.get(0);
    }
}

package shift.lab.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.exceptions.NotFoundException;
import shift.lab.models.HardDrive;
import shift.lab.models.ProductList;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "true")
public class DatabaseHardDriveRepository {

    private NamedParameterJdbcTemplate jdbcHardDriveTemplate;

    public DatabaseHardDriveRepository(NamedParameterJdbcTemplate jdbcHardDriveTemplate) {
        this.jdbcHardDriveTemplate = jdbcHardDriveTemplate;
    }

    @PostConstruct
    public void initialize() {
        String createGenerateIdSequenceSql = "create sequence HDD_ID_GENERATOR";

        String createTableSql = "create table HDDS (" +
                "HDD_ID  VARCHAR(64) default HDD_ID_GENERATOR.nextval," +
                "SERIAL_NUMBER     VARCHAR(128)," +
                "MANUFACTURER  VARCHAR(64)," +
                "PRICE    DECIMAL(20, 2)," +
                "CURRENCY VARCHAR(64)," +
                "AMOUNT    DOUBLE," +
                "VOLUME DOUBLE" +
                ");";

        jdbcHardDriveTemplate.update(createGenerateIdSequenceSql, new MapSqlParameterSource());
        jdbcHardDriveTemplate.update(createTableSql, new MapSqlParameterSource());
    }

    public HardDrive createHardDrive(HardDrive hardDrive) {

        String insertHardDriveSql = "insert into HDDS (SERIAL_NUMBER, MANUFACTURER, " +
                "PRICE, CURRENCY, AMOUNT, VOLUME) " +
                "values (:serialNumber, :manufacturer, :price, :currency, :amount, :volume" +
                ")";

        MapSqlParameterSource hardDriveParams = new MapSqlParameterSource()
                .addValue("serialNumber", hardDrive.getSerialNumber())
                .addValue("manufacturer", hardDrive.getManufacturer())
                .addValue("price", hardDrive.getPrice().getValue())
                .addValue("currency", hardDrive.getPrice().getCurrency())
                .addValue("amount", hardDrive.getAmount())
                .addValue("volume", hardDrive.getVolume());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcHardDriveTemplate.update(insertHardDriveSql, hardDriveParams, generatedKeyHolder);

        String productId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("HDD_ID").toString();

        return new HardDrive(productId,
                hardDrive.getSerialNumber(),
                hardDrive.getManufacturer(),
                hardDrive.getPrice(),
                hardDrive.getAmount(),
                hardDrive.getVolume()
        );

    }

    public HardDrive updateHardDrive(HardDrive hardDrive, String hardDriveId) {
        String fetchSql = "select HDD_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, VOLUME " +
                "from HDDS where HDD_ID=:hardDriveId";

        MapSqlParameterSource checkHardDriveParams = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId);

        HardDrive hardDriveQuery = jdbcHardDriveTemplate.queryForObject(fetchSql, checkHardDriveParams, new HardDriveRowMapper());

        if (hardDriveQuery == null) {
            throw new NotFoundException();
        }

        String updateHardDriveSql = "update HDDS " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "VOLUME=:volume " +
                "where HDD_ID=:hardDriveId";

        MapSqlParameterSource hardDriveParams = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId)
                .addValue("serialNumber", hardDrive.getSerialNumber())
                .addValue("manufacturer", hardDrive.getManufacturer())
                .addValue("price", hardDrive.getPrice().getValue())
                .addValue("currency", hardDrive.getPrice().getCurrency())
                .addValue("amount", hardDrive.getAmount())
                .addValue("volume", hardDrive.getVolume());

        jdbcHardDriveTemplate.update(updateHardDriveSql, hardDriveParams);

        return new HardDrive(hardDriveId,
                hardDrive.getSerialNumber(),
                hardDrive.getManufacturer(),
                hardDrive.getPrice(),
                hardDrive.getAmount(),
                hardDrive.getVolume()
        );

    }

    public ProductList<HardDrive> getAllHardDrives() {
        String fetchSql = "select HDD_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, VOLUME " +
                "from HDDS";

        List<HardDrive> hardDrives = jdbcHardDriveTemplate.query(fetchSql, new HardDriveRowMapper());

        return new ProductList<HardDrive>(hardDrives);
    }

    public HardDrive getHardDriveById(String hardDriveId) {
        String fetchSql = "select HDD_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, VOLUME " +
                "from HDDS where HDD_ID=:hardDriveId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId);

        HardDrive hardDrive = jdbcHardDriveTemplate.queryForObject(fetchSql, params, new HardDriveRowMapper());

        if (hardDrive == null) {
            throw new NotFoundException();
        }

        return hardDrive;
    }
}

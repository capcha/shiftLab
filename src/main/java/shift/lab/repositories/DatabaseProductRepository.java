/*
package shift.lab.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.exceptions.NotFoundException;
import shift.lab.models.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;


@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "true")
public class DatabaseProductRepository {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @PostConstruct
    public void initialize() {

        tableInitialize("MONITOR", "DIAGONAL", "REAL");
        tableInitialize("HDD", "VOLUME", "REAL");
        tableInitialize("DESKTOP", "FORMFACTOR", "VARCHAR(64)");
        tableInitialize("LAPTOP", "SIZE", "REAL");

    }

    private void tableInitialize(String tableName, String param, String paramType) {
        String createGenerateIdSequenceSql = "create sequence " + tableName + "_ID_GENERATOR";

        String createTableSql = "create table " + tableName + " (" +
                tableName + "_ID  VARCHAR(64) default " + tableName +"_ID_GENERATOR.nextval," +
                "SERIAL_NUMBER     VARCHAR(128)," +
                "MANUFACTURER  VARCHAR(64)," +
                "PRICE    DECIMAL(20, 2)," +
                "CURRENCY VARCHAR (64)" +
                "AMOUNT    REAL," +
                param + " " + paramType +
                ");";

        jdbcTemplate.update(createGenerateIdSequenceSql, new MapSqlParameterSource());
        jdbcTemplate.update(createTableSql, new MapSqlParameterSource());
    }

    public Monitor createMonitor(Product product) {

        StringBuilder productId = new StringBuilder();
        createProduct(product, "MONITOR", "DIAGONAL", productId);

        return new Monitor(productId.toString(),
                product.getSerialNumber(),
                product.getManufacturer(),
                product.getPrice(),
                product.getAmount(),
                Double.parseDouble(product.getParam())
        );

    }

    public Laptop createLaptop(Product product) {

        StringBuilder productId = new StringBuilder();
        createProduct(product, "LAPTOP", "SIZE", productId);

        return new Laptop(productId.toString(),
                product.getSerialNumber(),
                product.getManufacturer(),
                product.getPrice(),
                product.getAmount(),
                Double.parseDouble(product.getParam())
        );

    }

    public HardDrive createHardDrive(Product product) {

        StringBuilder productId = new StringBuilder();
        createProduct(product, "HDD", "VOLUME", productId);

        return new HardDrive(productId.toString(),
                product.getSerialNumber(),
                product.getManufacturer(),
                product.getPrice(),
                product.getAmount(),
                Double.parseDouble(product.getParam())
        );

    }

    public DesktopComputer createDesktopComputer(Product product) {

        StringBuilder productId = new StringBuilder();
        createProduct(product, "DESKTOP", "FORMFACTOR", productId);

        return new DesktopComputer(productId.toString(),
                product.getSerialNumber(),
                product.getManufacturer(),
                product.getPrice(),
                product.getAmount(),
                FormFactor.valueOf(product.getParam())
        );

    }

    private void createProduct(Product product, String productType, String param, StringBuilder productId) {
        String insertProductSql = "insert into " + productType + " (SERIAL_NUMBER, MANUFACTURER, " +
                "PRICE, CURRENCY, AMOUNT, " + param + ") " +
                "values (:serialNumber, :manufacturer, :price, :currency, :amount, :" + param.toLowerCase() +
                ")";

        MapSqlParameterSource productParams = new MapSqlParameterSource()
                .addValue("serialNumber", product.getSerialNumber())
                .addValue("manufacturer", product.getManufacturer())
                .addValue("price", product.getPrice().getValue())
                .addValue("currency", product.getPrice().getCurrency())
                .addValue("amount", product.getAmount())
                .addValue(param, product.getParam());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertProductSql, productParams, generatedKeyHolder);

        String prodId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get(productType + "_ID").toString();

        productId.append(prodId);
    }

    public Monitor updateMonitor(Monitor monitor, String monitorId) {
        String fetchSql = "select MONITOR_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, DIAGONAL" +
                "from MONITOR where MONITOR_ID=:monitorId";

        MapSqlParameterSource checkMonitorParams = new MapSqlParameterSource()
                .addValue("monitorId", monitorId);

       Monitor monitorQuery = jdbcTemplate.queryForObject(fetchSql, checkMonitorParams, new MonitorRowMapper());

       if (monitorQuery == null) {
           throw new NotFoundException();
       }

        String updateMonitorSql = "update MONITOR " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "DIAGONAL=:diagonal, " +
                "where MONITOR_ID=:monitorId";

        MapSqlParameterSource monitorParams = new MapSqlParameterSource()
                .addValue("serialNumber", monitor.getSerialNumber())
                .addValue("manufacturer", monitor.getManufacturer())
                .addValue("price", monitor.getPrice().getValue())
                .addValue("currency", monitor.getPrice().getCurrency())
                .addValue("amount", monitor.getAmount())
                .addValue("diagonal", monitor.getDiagonal());

        jdbcTemplate.update(updateMonitorSql, monitorParams);

        return new Monitor(monitorId,
                monitor.getSerialNumber(),
                monitor.getManufacturer(),
                monitor.getPrice(),
                monitor.getAmount(),
                monitor.getDiagonal()
                );

    }

    public Laptop updateLaptop(Laptop laptop, String laptopId) {
        String fetchSql = "select LAPTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, SIZE" +
                "from LAPTOP where LAPTOP_ID=:laptopId";

        MapSqlParameterSource checkLaptopParams = new MapSqlParameterSource()
                .addValue("laptopId", laptopId);

        Laptop laptopQuery = jdbcTemplate.queryForObject(fetchSql, checkLaptopParams, new LaptopRowMapper());

        if (laptopQuery == null) {
            throw new NotFoundException();
        }

        String updateLaptopSql = "update LAPTOP " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "SIZE=:size, " +
                "where LAPTOP_ID=:laptopId";

        MapSqlParameterSource laptopParams = new MapSqlParameterSource()
                .addValue("serialNumber", laptop.getSerialNumber())
                .addValue("manufacturer", laptop.getManufacturer())
                .addValue("price", laptop.getPrice().getValue())
                .addValue("currency", laptop.getPrice().getCurrency())
                .addValue("amount", laptop.getAmount())
                .addValue("size", laptop.getSize());

        jdbcTemplate.update(updateLaptopSql, laptopParams);

        return new Laptop(laptopId,
                laptop.getSerialNumber(),
                laptop.getManufacturer(),
                laptop.getPrice(),
                laptop.getAmount(),
                laptop.getSize()
        );

    }

    public HardDrive updateHardDrive(HardDrive hardDrive, String hardDriveId) {
        String fetchSql = "select HDD_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, VOLUME" +
                "from HDD where HDD_ID=:hardDriveId";

        MapSqlParameterSource checkHardDriveParams = new MapSqlParameterSource()
                .addValue("hardDriveId", hardDriveId);

        HardDrive hardDriveQuery = jdbcTemplate.queryForObject(fetchSql, checkHardDriveParams, new HardDriveRowMapper());

        if (hardDriveQuery == null) {
            throw new NotFoundException();
        }

        String updateHardDriveSql = "update HDD " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "VOLUME=:volume, " +
                "where HDD_ID=:hardDriveId";

        MapSqlParameterSource hardDriveParams = new MapSqlParameterSource()
                .addValue("serialNumber", hardDrive.getSerialNumber())
                .addValue("manufacturer", hardDrive.getManufacturer())
                .addValue("price", hardDrive.getPrice().getValue())
                .addValue("currency", hardDrive.getPrice().getCurrency())
                .addValue("amount", hardDrive.getAmount())
                .addValue("volume", hardDrive.getVolume());

        jdbcTemplate.update(updateHardDriveSql, hardDriveParams);

        return new HardDrive(hardDriveId,
                hardDrive.getSerialNumber(),
                hardDrive.getManufacturer(),
                hardDrive.getPrice(),
                hardDrive.getAmount(),
                hardDrive.getVolume()
        );

    }


}
*/

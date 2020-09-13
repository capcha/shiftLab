package shift.lab.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.exceptions.NotFoundException;
import shift.lab.models.Laptop;
import shift.lab.models.ProductList;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "true")
public class DatabaseLaptopRepository {

    private NamedParameterJdbcTemplate jdbcLaptopTemplate;

    public DatabaseLaptopRepository(NamedParameterJdbcTemplate jdbcLaptopTemplate) {
        this.jdbcLaptopTemplate = jdbcLaptopTemplate;
    }

    @PostConstruct
    public void initialize() {
        String createGenerateIdSequenceSql = "create sequence LAPTOP_ID_GENERATOR";

        String createTableSql = "create table LAPTOPS (" +
                "LAPTOP_ID  VARCHAR(64) default LAPTOP_ID_GENERATOR.nextval," +
                "SERIAL_NUMBER     VARCHAR(128)," +
                "MANUFACTURER  VARCHAR(64)," +
                "PRICE    DECIMAL(20, 2)," +
                "CURRENCY VARCHAR(64)," +
                "AMOUNT    DOUBLE," +
                "LAPTOP_SIZE DOUBLE" +
                ");";

        jdbcLaptopTemplate.update(createGenerateIdSequenceSql, new MapSqlParameterSource());
        jdbcLaptopTemplate.update(createTableSql, new MapSqlParameterSource());
    }

    public Laptop createLaptop(Laptop laptop) {

        String insertLaptopSql = "insert into LAPTOPS (SERIAL_NUMBER, MANUFACTURER, " +
                "PRICE, CURRENCY, AMOUNT, LAPTOP_SIZE) " +
                "values (:serialNumber, :manufacturer, :price, :currency, :amount, :laptopSize" +
                ")";

        MapSqlParameterSource laptopParams = new MapSqlParameterSource()
                .addValue("serialNumber", laptop.getSerialNumber())
                .addValue("manufacturer", laptop.getManufacturer())
                .addValue("price", laptop.getPrice().getValue())
                .addValue("currency", laptop.getPrice().getCurrency())
                .addValue("amount", laptop.getAmount())
                .addValue("laptopSize", laptop.getSize());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcLaptopTemplate.update(insertLaptopSql, laptopParams, generatedKeyHolder);

        String productId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("LAPTOP_ID").toString();

        return new Laptop(productId,
                laptop.getSerialNumber(),
                laptop.getManufacturer(),
                laptop.getPrice(),
                laptop.getAmount(),
                laptop.getSize()
        );

    }

    public Laptop updateLaptop(Laptop laptop, String laptopId) {
        String fetchSql = "select LAPTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, LAPTOP_SIZE " +
                "from LAPTOPS where LAPTOP_ID=:laptopId";

        MapSqlParameterSource checkLaptopParams = new MapSqlParameterSource()
                .addValue("laptopId", laptopId);

        Laptop laptopQuery = jdbcLaptopTemplate.queryForObject(fetchSql, checkLaptopParams, new LaptopRowMapper());

        if (laptopQuery == null) {
            throw new NotFoundException();
        }

        String updateLaptopSql = "update LAPTOPS " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "LAPTOP_SIZE=:laptopSize " +
                "where LAPTOP_ID=:laptopId";

        MapSqlParameterSource laptopParams = new MapSqlParameterSource()
                .addValue("laptopId", laptopId)
                .addValue("serialNumber", laptop.getSerialNumber())
                .addValue("manufacturer", laptop.getManufacturer())
                .addValue("price", laptop.getPrice().getValue())
                .addValue("currency", laptop.getPrice().getCurrency())
                .addValue("amount", laptop.getAmount())
                .addValue("laptopSize", laptop.getSize());

        jdbcLaptopTemplate.update(updateLaptopSql, laptopParams);

        return new Laptop(laptopId,
                laptop.getSerialNumber(),
                laptop.getManufacturer(),
                laptop.getPrice(),
                laptop.getAmount(),
                laptop.getSize()
        );

    }

    public ProductList<Laptop> getAllLaptops() {
        String fetchSql = "select LAPTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, LAPTOP_SIZE " +
                "from LAPTOPS";

        List<Laptop> laptops = jdbcLaptopTemplate.query(fetchSql, new LaptopRowMapper());

        return new ProductList<Laptop>(laptops);
    }

    public Laptop getLaptopById(String laptopId) {
        String fetchSql = "select LAPTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, LAPTOP_SIZE " +
                "from LAPTOPS where LAPTOP_ID=:laptopId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("laptopId", laptopId);

        Laptop laptop = jdbcLaptopTemplate.queryForObject(fetchSql, params, new LaptopRowMapper());

        if (laptop == null) {
            throw new NotFoundException();
        }

        return laptop;
    }
}

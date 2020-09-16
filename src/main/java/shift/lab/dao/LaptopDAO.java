package shift.lab.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.model.ProductList;
import shift.lab.exception.NotFoundException;
import shift.lab.model.Laptop;
import shift.lab.rowmapper.LaptopRowMapper;

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class LaptopDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String insertSql = "insert into LAPTOPS (SERIAL_NUMBER, MANUFACTURER, " +
            "PRICE, CURRENCY, AMOUNT, LAPTOP_SIZE) " +
            "values (:serialNumber, :manufacturer, :price, :currency, :amount, :laptopSize" +
            ")";

    private static final String getByIdSql = "select * from LAPTOPS where LAPTOP_ID=:laptopId";

    private static final String updateSql = "update LAPTOPS " +
            "set SERIAL_NUMBER=:serialNumber, " +
            "MANUFACTURER=:manufacturer, " +
            "PRICE=:price, " +
            "CURRENCY=:currency, " +
            "AMOUNT=:amount, " +
            "LAPTOP_SIZE=:laptopSize " +
            "where LAPTOP_ID=:laptopId";

    private static final String getAllSql = "select * from LAPTOPS";

    public Laptop create(Laptop laptop) {
        MapSqlParameterSource laptopParams = new MapSqlParameterSource()
                .addValue("serialNumber", laptop.getSerialNumber())
                .addValue("manufacturer", laptop.getManufacturer())
                .addValue("price", laptop.getPrice().getValue())
                .addValue("currency", laptop.getPrice().getCurrency())
                .addValue("amount", laptop.getAmount())
                .addValue("laptopSize", laptop.getSize());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, laptopParams, generatedKeyHolder);

        String laptopId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("LAPTOP_ID").toString();

        return new Laptop(laptopId,
                laptop.getSerialNumber(),
                laptop.getManufacturer(),
                laptop.getPrice(),
                laptop.getAmount(),
                laptop.getSize()
        );
    }

    public Laptop update(Laptop laptop, String laptopId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("laptopId", laptopId);

        List<Laptop> laptopQuery = jdbcTemplate.query(getByIdSql, params, new LaptopRowMapper());

        if (laptopQuery.size() == 0) {
            throw new NotFoundException();
        }

        MapSqlParameterSource laptopParams = new MapSqlParameterSource()
                .addValue("laptopId", laptopId)
                .addValue("serialNumber", laptop.getSerialNumber())
                .addValue("manufacturer", laptop.getManufacturer())
                .addValue("price", laptop.getPrice().getValue())
                .addValue("currency", laptop.getPrice().getCurrency())
                .addValue("amount", laptop.getAmount())
                .addValue("laptopSize", laptop.getSize());

        jdbcTemplate.update(updateSql, laptopParams);

        return jdbcTemplate.query(getByIdSql, params, new LaptopRowMapper()).get(0);
    }

    public ProductList<Laptop> getAll() {
        List<Laptop> laptops = jdbcTemplate.query(getAllSql, new LaptopRowMapper());

        return new ProductList<>(laptops);
    }

    public Laptop getById(String laptopId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("laptopId", laptopId);

        List<Laptop> laptopQuery = jdbcTemplate.query(getByIdSql, params, new LaptopRowMapper());

        if (laptopQuery.size() == 0) {
            throw new NotFoundException();
        }
        return laptopQuery.get(0);
    }
}

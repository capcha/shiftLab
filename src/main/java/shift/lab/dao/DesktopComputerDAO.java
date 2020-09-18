package shift.lab.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.model.DesktopComputer;
import shift.lab.model.Price;
import shift.lab.model.ProductList;
import shift.lab.rowmapper.DesktopComputerRowMapper;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DesktopComputerDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DesktopComputerRowMapper desktopComputerRowMapper; 

    private static final String insertSql = "insert into DESKTOPS (SERIAL_NUMBER, MANUFACTURER, " +
            "PRICE, CURRENCY, AMOUNT, FORM_FACTOR) " +
            "values (:serialNumber, :manufacturer, :price, :currency, :amount, :formFactor" +
            ")";

    private static final String getByIdSql = "select * from DESKTOPS where DESKTOP_ID=:desktopId";

    private static final String updateSql = "update DESKTOPS " +
            "set SERIAL_NUMBER=:serialNumber, " +
            "MANUFACTURER=:manufacturer, " +
            "PRICE=:price, " +
            "CURRENCY=:currency, " +
            "AMOUNT=:amount, " +
            "FORM_FACTOR=:formFactor " +
            "where DESKTOP_ID=:desktopId";

    private static final String getAllSql = "select * from DESKTOPS";

    public DesktopComputer create(DesktopComputer desktopComputer) {
        String value = Optional.ofNullable(desktopComputer.getPrice())
                .map((price) -> price.getValue().toString())
                .orElse(null);

        String currency = Optional.ofNullable(desktopComputer.getPrice())
                .map(Price::getCurrency)
                .orElse(null);

        MapSqlParameterSource desktopComputerParams = new MapSqlParameterSource()
                .addValue("serialNumber", desktopComputer.getSerialNumber())
                .addValue("manufacturer", desktopComputer.getManufacturer())
                .addValue("price", value)
                .addValue("currency", currency)
                .addValue("amount", desktopComputer.getAmount())
                .addValue("formFactor", desktopComputer.getFormFactor().name());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, desktopComputerParams, generatedKeyHolder);

        String desktopId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("DESKTOP_ID").toString();

        return getById(desktopId);
    }

    public DesktopComputer update(DesktopComputer desktopComputer, String desktopId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desktopId", desktopId);

        List<DesktopComputer> desktopComputerQuery = jdbcTemplate.query(getByIdSql, params, desktopComputerRowMapper);

        if (desktopComputerQuery.isEmpty()) {
            return null;
        }

        MapSqlParameterSource desktopComputerParams = new MapSqlParameterSource()
                .addValue("desktopId", desktopId)
                .addValue("serialNumber", desktopComputer.getSerialNumber())
                .addValue("manufacturer", desktopComputer.getManufacturer())
                .addValue("price", desktopComputer.getPrice().getValue())
                .addValue("currency", desktopComputer.getPrice().getCurrency())
                .addValue("amount", desktopComputer.getAmount())
                .addValue("formFactor", desktopComputer.getFormFactor().name());

        jdbcTemplate.update(updateSql, desktopComputerParams);

        return jdbcTemplate.query(getByIdSql, params, desktopComputerRowMapper).get(0);
    }

    public ProductList<DesktopComputer> getAll() {
        List<DesktopComputer> desktopComputers = jdbcTemplate.query(getAllSql, desktopComputerRowMapper);

        return new ProductList<>(desktopComputers);
    }

    public DesktopComputer getById(String desktopId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desktopId", desktopId);

        List<DesktopComputer> desktopComputerQuery = jdbcTemplate.query(getByIdSql, params, desktopComputerRowMapper);

        if (desktopComputerQuery.isEmpty()) {
            return null;
        }

        return desktopComputerQuery.get(0);
    }
}

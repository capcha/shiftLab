package shiftlab.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shiftlab.exception.NotFoundException;
import shiftlab.model.DesktopComputer;
import shiftlab.model.ProductList;
import shiftlab.rowmapper.DesktopComputerRowMapper;

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class DesktopComputerDAO {
    private final NamedParameterJdbcTemplate jdbcTemplate;

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
        MapSqlParameterSource desktopComputerParams = new MapSqlParameterSource()
                .addValue("serialNumber", desktopComputer.getSerialNumber())
                .addValue("manufacturer", desktopComputer.getManufacturer())
                .addValue("price", desktopComputer.getPrice().getValue())
                .addValue("currency", desktopComputer.getPrice().getCurrency())
                .addValue("amount", desktopComputer.getAmount())
                .addValue("formFactor", desktopComputer.getFormFactor().name());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, desktopComputerParams, generatedKeyHolder);

        String desktopId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("DESKTOP_ID").toString();

        return new DesktopComputer(desktopId,
                desktopComputer.getSerialNumber(),
                desktopComputer.getManufacturer(),
                desktopComputer.getPrice(),
                desktopComputer.getAmount(),
                desktopComputer.getFormFactor()
        );
    }

    public DesktopComputer update(DesktopComputer desktopComputer, String desktopId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desktopId", desktopId);

        List<DesktopComputer> desktopComputerQuery = jdbcTemplate.query(getByIdSql, params, new DesktopComputerRowMapper());

        if (desktopComputerQuery.size() == 0) {
            throw new NotFoundException();
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

        return new DesktopComputer(desktopComputerQuery.get(0).getId(),
                desktopComputerQuery.get(0).getSerialNumber(),
                desktopComputerQuery.get(0).getManufacturer(),
                desktopComputerQuery.get(0).getPrice(),
                desktopComputerQuery.get(0).getAmount(),
                desktopComputerQuery.get(0).getFormFactor()
        );
    }

    public ProductList<DesktopComputer> getAll() {
        List<DesktopComputer> desktopComputers = jdbcTemplate.query(getAllSql, new DesktopComputerRowMapper());

        return new ProductList<>(desktopComputers);
    }

    public DesktopComputer getById(String desktopId) {

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desktopId", desktopId);

        List<DesktopComputer> desktopComputerQuery = jdbcTemplate.query(getByIdSql, params, new DesktopComputerRowMapper());

        if (desktopComputerQuery.size() == 0) {
            throw new NotFoundException();
        }

        return desktopComputerQuery.get(0);
    }
}

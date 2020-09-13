package shift.lab.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import shift.lab.exceptions.NotFoundException;
import shift.lab.models.DesktopComputer;
import shift.lab.models.ProductList;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;

@Repository
@ConditionalOnProperty(name = "use.database", havingValue = "true")
public class DatabaseDesktopComputerRepository {

    private NamedParameterJdbcTemplate jdbcDesktopComputerTemplate;

    public DatabaseDesktopComputerRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcDesktopComputerTemplate = namedParameterJdbcTemplate;
    }

    @PostConstruct
    public void initialize() {
        String createGenerateIdSequenceSql = "create sequence DESKTOP_ID_GENERATOR";

        String createTableSql = "create table DESKTOPS (" +
                "DESKTOP_ID  VARCHAR(64) default DESKTOP_ID_GENERATOR.nextval," +
                "SERIAL_NUMBER     VARCHAR(128)," +
                "MANUFACTURER  VARCHAR(64)," +
                "PRICE    DECIMAL(20, 2)," +
                "CURRENCY VARCHAR(64)," +
                "AMOUNT    DOUBLE," +
                "FORM_FACTOR VARCHAR(64)" +
                ");";

        jdbcDesktopComputerTemplate.update(createGenerateIdSequenceSql, new MapSqlParameterSource());
        jdbcDesktopComputerTemplate.update(createTableSql, new MapSqlParameterSource());
    }

    public DesktopComputer createDesktopComputer(DesktopComputer desktopComputer) {

        String insertDesktopComputerSql = "insert into DESKTOPS (SERIAL_NUMBER, MANUFACTURER, " +
                "PRICE, CURRENCY, AMOUNT, FORM_FACTOR) " +
                "values (:serialNumber, :manufacturer, :price, :currency, :amount, :formFactor" +
                ")";

        MapSqlParameterSource desktopComputerParams = new MapSqlParameterSource()
                .addValue("serialNumber", desktopComputer.getSerialNumber())
                .addValue("manufacturer", desktopComputer.getManufacturer())
                .addValue("price", desktopComputer.getPrice().getValue())
                .addValue("currency", desktopComputer.getPrice().getCurrency())
                .addValue("amount", desktopComputer.getAmount())
                .addValue("formFactor", desktopComputer.getFormFactor().name());

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcDesktopComputerTemplate.update(insertDesktopComputerSql, desktopComputerParams, generatedKeyHolder);

        String productId = Objects.requireNonNull(generatedKeyHolder.getKeys()).get("DESKTOP_ID").toString();

        return new DesktopComputer(productId,
                desktopComputer.getSerialNumber(),
                desktopComputer.getManufacturer(),
                desktopComputer.getPrice(),
                desktopComputer.getAmount(),
                desktopComputer.getFormFactor()
        );

    }


    public DesktopComputer updateDesktopComputer(DesktopComputer desktopComputer, String desktopId) {
        String fetchSql = "select DESKTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, FORM_FACTOR " +
                "from DESKTOPS where DESKTOP_ID=:desktopId";

        MapSqlParameterSource checkDesktopComputerParams = new MapSqlParameterSource()
                .addValue("desktopId", desktopId);

        DesktopComputer desktopComputerQuery = jdbcDesktopComputerTemplate.queryForObject(fetchSql, checkDesktopComputerParams, new DesktopComputerRowMapper());

        if (desktopComputerQuery == null) {
            throw new NotFoundException();
        }

        String updateDesktopComputerSql = "update DESKTOPS " +
                "set SERIAL_NUMBER=:serialNumber, " +
                "MANUFACTURER=:manufacturer, " +
                "PRICE=:price, " +
                "CURRENCY=:currency, " +
                "AMOUNT=:amount, " +
                "FORM_FACTOR=:formFactor " +
                "where DESKTOP_ID=:desktopId";

        MapSqlParameterSource desktopComputerParams = new MapSqlParameterSource()
                .addValue("desktopId", desktopId)
                .addValue("serialNumber", desktopComputer.getSerialNumber())
                .addValue("manufacturer", desktopComputer.getManufacturer())
                .addValue("price", desktopComputer.getPrice().getValue())
                .addValue("currency", desktopComputer.getPrice().getCurrency())
                .addValue("amount", desktopComputer.getAmount())
                .addValue("formFactor", desktopComputer.getFormFactor().name());

        jdbcDesktopComputerTemplate.update(updateDesktopComputerSql, desktopComputerParams);

        return new DesktopComputer(desktopId,
                desktopComputer.getSerialNumber(),
                desktopComputer.getManufacturer(),
                desktopComputer.getPrice(),
                desktopComputer.getAmount(),
                desktopComputer.getFormFactor()
        );

    }

    public ProductList<DesktopComputer> getAllDesktopComputers() {
        String fetchSql = "select DESKTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, FORM_FACTOR " +
                "from DESKTOPS";

        List<DesktopComputer> desktopComputers = jdbcDesktopComputerTemplate.query(fetchSql, new DesktopComputerRowMapper());

        return new ProductList<DesktopComputer>(desktopComputers);
    }

    public DesktopComputer getDesktopComputerById(String desktopId) {
        String fetchSql = "select DESKTOP_ID, SERIAL_NUMBER, MANUFACTURER, PRICE, CURRENCY, AMOUNT, FORM_FACTOR " +
                "from DESKTOPS where DESKTOP_ID=:desktopId";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("desktopId", desktopId);

        DesktopComputer desktopComputer = jdbcDesktopComputerTemplate.queryForObject(fetchSql, params, new DesktopComputerRowMapper());

        if (desktopComputer == null) {
            throw new NotFoundException();
        }

        return desktopComputer;
    }

}

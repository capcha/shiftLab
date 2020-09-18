package shift.lab.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import shift.lab.model.DesktopComputer;
import shift.lab.model.FormFactor;
import shift.lab.model.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DesktopComputerRowMapper implements RowMapper<DesktopComputer> {
    @Override
    public DesktopComputer mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new DesktopComputer(rs.getString("DESKTOP_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                        rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                FormFactor.valueOf(rs.getString("FORM_FACTOR"))
        );

    }
}
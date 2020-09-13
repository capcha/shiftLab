package shift.lab.repositories;

import org.springframework.jdbc.core.RowMapper;
import shift.lab.models.DesktopComputer;
import shift.lab.models.FormFactor;
import shift.lab.models.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DesktopComputerRowMapper implements RowMapper<DesktopComputer> {

    @Override
    public DesktopComputer mapRow(ResultSet rs, int rowNum) throws SQLException {

        DesktopComputer desktopComputer = new DesktopComputer(rs.getString("DESKTOP_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                        rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                FormFactor.valueOf(rs.getString("FORM_FACTOR"))
        );

        return desktopComputer;

    }
}
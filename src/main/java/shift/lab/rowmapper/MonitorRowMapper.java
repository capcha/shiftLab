package shift.lab.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import shift.lab.model.Monitor;
import shift.lab.model.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MonitorRowMapper implements RowMapper<Monitor> {
    @Override
    public Monitor mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Monitor(rs.getString("MONITOR_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                          rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                rs.getDouble("DIAGONAL")
                );

    }
}
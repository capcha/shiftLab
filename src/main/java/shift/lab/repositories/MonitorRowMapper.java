package shift.lab.repositories;

import org.springframework.jdbc.core.RowMapper;
import shift.lab.models.Monitor;
import shift.lab.models.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MonitorRowMapper implements RowMapper<Monitor> {

    @Override
    public Monitor mapRow(ResultSet rs, int rowNum) throws SQLException {

        Monitor monitor = new Monitor(rs.getString("MONITOR_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                          rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                rs.getDouble("DIAGONAL")
                );

        return monitor;

    }
}
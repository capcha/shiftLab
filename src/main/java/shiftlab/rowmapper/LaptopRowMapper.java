package shiftlab.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import shiftlab.model.Laptop;
import shiftlab.model.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LaptopRowMapper implements RowMapper<Laptop> {
    @Override
    public Laptop mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new Laptop(rs.getString("LAPTOP_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                        rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                rs.getDouble("LAPTOP_SIZE")
        );

    }
}
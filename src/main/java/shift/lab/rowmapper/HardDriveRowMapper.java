package shift.lab.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import shift.lab.model.HardDrive;
import shift.lab.model.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class HardDriveRowMapper implements RowMapper<HardDrive> {
    @Override
    public HardDrive mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new HardDrive(rs.getString("HDD_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                        rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                rs.getDouble("VOLUME")
        );

    }
}
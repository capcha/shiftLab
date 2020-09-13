package shift.lab.repositories;

import org.springframework.jdbc.core.RowMapper;
import shift.lab.models.HardDrive;
import shift.lab.models.Price;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HardDriveRowMapper implements RowMapper<HardDrive> {

    @Override
    public HardDrive mapRow(ResultSet rs, int rowNum) throws SQLException {

        HardDrive hardDrive = new HardDrive(rs.getString("HDD_ID"),
                rs.getString("SERIAL_NUMBER"),
                rs.getString("MANUFACTURER"),
                new Price(rs.getBigDecimal("PRICE"),
                        rs.getString("CURRENCY")),
                rs.getInt("AMOUNT"),
                rs.getDouble("VOLUME")
        );

        return hardDrive;

    }
}
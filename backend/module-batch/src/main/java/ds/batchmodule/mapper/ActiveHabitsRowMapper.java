package ds.batchmodule.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ds.batchmodule.vo.ActiveHabitsVO;

public class ActiveHabitsRowMapper implements RowMapper<ActiveHabitsVO> {

    @Override
    public ActiveHabitsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ActiveHabitsVO(
                rs.getLong("habit_id"),
                rs.getString("day_week")
        );
    }
}

package limchaeyoung.dailySprout.batch.mapper;

import limchaeyoung.dailySprout.batch.vo.ActiveHabitsVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActiveHabitsRowMapper implements RowMapper<ActiveHabitsVO> {

    @Override
    public ActiveHabitsVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ActiveHabitsVO(
                rs.getLong("habit_id"),
                rs.getString("day_week")
        );
    }
}

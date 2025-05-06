package apiassignment.alphasolutions.rowmapper;

import apiassignment.alphasolutions.model.Skill;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillRowmapper implements RowMapper<Skill> {
    @Override
    public Skill mapRow(ResultSet rs, int rowNum) throws SQLException {
        Skill skill = new Skill();

        skill.setSkillId(rs.getInt("skillID"));
        skill.setSkillName(rs.getString("skill_name"));

        return skill;
    }
}

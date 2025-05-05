package apiassignment.alphasolutions.model;

public class Skill {
    private int skillId;
    private String skillName;

    public Skill() {
    }

    public Skill(int skillId, String skillName) {
        this.skillId = skillId;
        this.skillName = skillName;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}

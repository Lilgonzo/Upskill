package com.api.entities;

public class Skill {
    Profile user;
    SkillType skillType;

    public void setUser(Profile user) {
        this.user = user;
    }

    public void setSkillType(SkillType skillType) {
        this.skillType = skillType;
    }
}

package com.game.service;

import com.game.entity.Profession;
import com.game.entity.Race;

public class PlayerRequstBody {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long birthday;
    private Boolean banned;
    private Integer experience;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Long getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
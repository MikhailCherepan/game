package com.game.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player {

    private Long id;
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long birthday;
    private Boolean banned;
    private Integer experience;
    private Integer level;
    private Integer untilNextLevel;

    public Player(){};

    public Player(long id, String name, String title, Race race, Profession profession, Date birthday, boolean banned, Integer experience, Integer level, Integer untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday.getTime();
        this.banned = banned;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Enumerated(EnumType.STRING)
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Enumerated(EnumType.STRING)
    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Date getBirthday() {
        return new Date(birthday);
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday.getTime();
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"name\":\"" + name + "\"" +
                ", \"title\":\"" + title + "\"" +
                ", \"race\":\"" + race + "\"" +
                ", \"profession\":\"" + profession + "\""+
                ", \"birthday\":" + birthday +
                ", \"banned\":" + banned +
                ", \"experience\":" + experience +
                ", \"level\":" + level +
                ", \"untilNextLevel\":" + untilNextLevel +
                "}";
    }
}
package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Profession;
import com.game.entity.Race;

public class PageRequst {
    String name;
    String title;
    Race race;
    Profession profession;
    Long after;
    Long before;
    Boolean banned;
    Integer minExperience;
    Integer maxExperience;
    Integer minLevel;
    Integer maxLevel;
    PlayerOrder order;
    Integer pageNumber;
    Integer pageSiz;

    public PageRequst(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order, Integer pageNumber, Integer pageSiz) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.after = after;
        this.before = before;
        this.banned = banned;
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.order = order;
        this.pageNumber = pageNumber;
        this.pageSiz = pageSiz;
    }
    public PageRequst(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.after = after;
        this.before = before;
        this.banned = banned;
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.order = null;
        this.pageNumber = null;
        this.pageSiz = null;
    }

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

    public Long getAfter() {
        return after;
    }

    public Long getBefore() {
        return before;
    }

    public Boolean getBanned() {
        return banned;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public Integer getMaxExperience() {
        return maxExperience;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public PlayerOrder getOrder() {
        return order;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Integer getPageSiz() {
        return pageSiz;
    }
}

package com.game.service;

import com.game.controller.PlayerRequstBody;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.List;

public interface PlayerService {


    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel);
    public Player getPlayerById(Long id);
    public Player createPlayer(PlayerRequstBody playerRequstBody);

    public void deletePlayer(Long id);
    public Player updatePlayer(Long id, PlayerRequstBody playerRequstBody);

}

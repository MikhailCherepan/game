package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {
    private PlayerServiceImp playerServiceImp;

    @Autowired

    public void setPlayerService(PlayerServiceImp playerServiceImp) {
        this.playerServiceImp = playerServiceImp;
    }


    @GetMapping(value = "rest/players")
    public List<Player> getPlayers(String name,
                                   String title,
                                   Race race,
                                   Profession profession,
                                   Long after,
                                   Long before,
                                   Boolean banned,
                                   Integer minExperience,
                                   Integer maxExperience,
                                   Integer minLevel,
                                   Integer maxLevel,
                                   PlayerOrder order,
                                   Integer pageNumber,
                                   Integer pageSize)
    {
        PageRequst pageRequst = new PageRequst(
                name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel,
                order,
                pageNumber,
                pageSize);

        List<Player> players = playerServiceImp.getPlayersToPage(pageRequst);
        return players;
    }

    @DeleteMapping(value = "rest/players/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerServiceImp.deletePlayer(id);
    }

    @GetMapping(value = "/rest/players/{id}")
    public Player getPlayerByID(@PathVariable Long id) {
        return playerServiceImp.getPlayerById(id);
    }

    @GetMapping(value = "/rest/players/count")
    public int getPlayersCount(String name,
                               String title,
                               Race race,
                               Profession profession,
                               Long after,
                               Long before,
                               Boolean banned,
                               Integer minExperience,
                               Integer maxExperience,
                               Integer minLevel,
                               Integer maxLevel)
    {
        PageRequst pageRequstToCount = new PageRequst( name,
                title,
                race,
                profession,
                after,
                before,
                banned,
                minExperience,
                maxExperience,
                minLevel,
                maxLevel);
        return playerServiceImp.getPlayersCount(pageRequstToCount);
    }

    @PostMapping(value = "/rest/players")
    public Player createPlayer(@RequestBody PlayerRequstBody playerRequstBody) {
        return playerServiceImp.createPlayer(
                playerRequstBody.getName(),
                playerRequstBody.getTitle(),
                playerRequstBody.getRace(),
                playerRequstBody.getProfession(),
                playerRequstBody.getBirthday(),
                playerRequstBody.getBanned(),
                playerRequstBody.getExperience()
        );
    }

    @PostMapping(value = "/rest/players/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody PlayerRequstBody playerRequstBody) {
        return playerServiceImp.updatePlayer(
                id,
                playerRequstBody.getName(),
                playerRequstBody.getTitle(),
                playerRequstBody.getRace(),
                playerRequstBody.getProfession(),
                playerRequstBody.getBirthday(),
                playerRequstBody.getBanned(),
                playerRequstBody.getExperience()
        );
    }



}
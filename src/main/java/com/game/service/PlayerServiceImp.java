package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.controller.PageRequst;
import com.game.controller.PlayerRequstBody;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@Service
public class PlayerServiceImp implements PlayerService{
    private PlayerRepository repository;

    @Autowired

    public void setRepository(PlayerRepository repository) {
        this.repository = repository;
    }


    public List<Player> getPlayersToPage(PageRequst pageRequst) {

        List<Player> all = getPlayers(
                pageRequst.getName(),
                pageRequst.getTitle(),
                pageRequst.getRace(),
                pageRequst.getProfession(),
                pageRequst.getAfter(),
                pageRequst.getBefore(),
                pageRequst.getBanned(),
                pageRequst.getMinExperience(),
                pageRequst.getMaxExperience(),
                pageRequst.getMinLevel(),
                pageRequst.getMaxLevel());

        if (pageRequst.getOrder() == PlayerOrder.NAME) {

            Collections.sort(all, new compreName());
        } else if (pageRequst.getOrder()  == PlayerOrder.BIRTHDAY) {

            Collections.sort(all, new compreBirthday());
        } else if (pageRequst.getOrder()  == PlayerOrder.LEVEL) {

            Collections.sort(all, new compareLevel());
        } else if (pageRequst.getOrder()  == PlayerOrder.EXPERIENCE) {

            Collections.sort(all, new compareExperience());
        } else {

            Collections.sort(all, new compreId());
        }

        return getPage(all, pageRequst.getPageNumber(), pageRequst.getPageSiz());

    }

    private boolean BirthdayBefore(Player player, Long before) {
        return player.getBirthday().getTime() < before;
    }

    private boolean BirthdayAfter(Player player, Long after) {
        return player.getBirthday().getTime() > after;
    }

    private boolean isLevelLessThan(Player player, Integer maxLevel) {
        return player.getLevel() <= maxLevel;
    }

    private boolean isLevelGreaterThan(Player player, Integer minLevel) {
        return player.getLevel() >= minLevel;
    }

    private boolean isExperienceLessThan(Player player, Integer maxExperience) {
        return player.getExperience() <= maxExperience;
    }

    private List<Player> getPage(List<Player> all, Integer pageNumber, Integer pageSize) {
        pageNumber = pageNumber == null ? 0 : pageNumber;
        pageSize = pageSize == null ? 3 : pageSize;

        int start = pageNumber * pageSize <= all.size() ? pageSize * pageNumber : all.size() - pageSize;
        int end = Math.min((pageSize * pageNumber + pageSize), all.size());
        start = Math.min(start, end);
        all = all.subList(start, end);

        return all;
    }

    private boolean isExperienceGreaterThan(Player player, Integer minExperience) {
        return player.getExperience() >= minExperience;
    }

    private boolean hasProfession(Player player, Profession profession) {
        return player.getProfession() == profession;
    }

    private boolean hasRace(Player player, Race race) {
        return player.getRace() == race;
    }

    private boolean hasName(Player player, String name) {
        return player.getName().toUpperCase().contains(name.toUpperCase());
    }
@Override
    public void deletePlayer(Long id) {
        checkValidId(id);
        repository.deleteById(id);
    }

    public boolean hasPlayer(Long id) {
        return repository.existsById(id);
    }
@Override
    public Player getPlayerById(Long id) {
        checkValidId(id);
        return repository.findById(id).orElseThrow((IllegalStateException::new));

    }
@Override
    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Long after, Long before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        List<Player> all = repository.findAll();
        if (name != null) {
            all = all.stream().filter(player -> hasName(player, name)).collect(Collectors.toList());
        }
        if (title != null) {
            all = all.stream().filter(player -> hasTitle(player, title)).collect(Collectors.toList());
        }
        if (race != null) {
            all = all.stream().filter(player -> hasRace(player, race)).collect(Collectors.toList());
        }
        if (profession != null) {
            all = all.stream().filter(player -> hasProfession(player, profession)).collect(Collectors.toList());
        }
        if (minExperience != null) {
            all = all.stream().filter(player -> isExperienceGreaterThan(player, minExperience)).collect(Collectors.toList());
        }
        if (maxExperience != null) {
            all = all.stream().filter(player -> isExperienceLessThan(player, maxExperience)).collect(Collectors.toList());
        }
        if (minLevel != null) {
            all = all.stream().filter(player -> isLevelGreaterThan(player, minLevel)).collect(Collectors.toList());
        }
        if (maxLevel != null) {
            all = all.stream().filter(player -> isLevelLessThan(player, maxLevel)).collect(Collectors.toList());
        }
        if (banned != null) {
            all = all.stream().filter(player -> isBanned(player, banned)).collect(Collectors.toList());
        }
        if (after != null) {
            all = all.stream().filter(player -> BirthdayAfter(player, after)).collect(Collectors.toList());
        }
        if (before != null) {
            all = all.stream().filter(player -> BirthdayBefore(player, before)).collect(Collectors.toList());
        }
        return all;
    }

    private Boolean isBanned(Player player, Boolean banned) {
        return player.getBanned() == banned;
    }

    public int getPlayersCount(PageRequst pageRequstToCount) {
        List<Player> playersList = getPlayers(
                pageRequstToCount.getName(),
                pageRequstToCount.getTitle(),
                pageRequstToCount.getRace(),
                pageRequstToCount.getProfession(),
                pageRequstToCount.getAfter(),
                pageRequstToCount.getBefore(),
                pageRequstToCount.getBanned(),
                pageRequstToCount.getMinExperience(),
                pageRequstToCount.getMaxExperience(),
                pageRequstToCount.getMinLevel(),
                pageRequstToCount.getMaxLevel());
        return playersList.size();
    }

    private boolean hasTitle(Player player, String title) {
        return player.getTitle().toUpperCase().contains(title.toUpperCase());
    }
@Override
    public Player createPlayer(PlayerRequstBody playerRequstBody) {

    if (isNameInvalid(playerRequstBody.getName())) throw new BadRequestException();
        if (isTitleInvalid(playerRequstBody.getTitle())) throw new BadRequestException();
        if (playerRequstBody.getRace() == null) throw new BadRequestException();
        if (playerRequstBody.getProfession() == null) throw new BadRequestException();
        if (playerRequstBody.getBirthday() == null || getYear(playerRequstBody.getBirthday() ) < 2000 || getYear(playerRequstBody.getBirthday() ) > 3000) throw new BadRequestException();
        if (playerRequstBody.getExperience() == null || playerRequstBody.getExperience() > 10000000 || playerRequstBody.getExperience() < 0) throw new BadRequestException();
        if (playerRequstBody.getBanned() == null)  playerRequstBody.setBanned(false);

        int level = (int) ((Math.sqrt(2500 + 200 * playerRequstBody.getExperience()) - 50) / (100));
        int untilNextLevel = 50 * (level + 1) * (level + 2) - playerRequstBody.getExperience();

        Player player = new Player();
        player.setName(playerRequstBody.getName());
        player.setTitle(playerRequstBody.getTitle());
        player.setRace(playerRequstBody.getRace());
        player.setProfession(playerRequstBody.getProfession());
        player.setBirthday(new Date(playerRequstBody.getBirthday()));
        player.setExperience(playerRequstBody.getExperience());
        player.setBanned(playerRequstBody.getBanned());
        player.setLevel(level);
        player.setUntilNextLevel(untilNextLevel);

        return repository.save(player);

    }

    private int getYear(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }

    private boolean isTitleInvalid(String title) {
        if (title == null) return true;
        return title.length() > 30;
    }

    private boolean isNameInvalid(String name) {
        if (name == null) return true;
        if (name.length() > 12) return true;
        return name.isEmpty();

    }
@Override
    public Player updatePlayer(Long id, PlayerRequstBody playerRequstBody) {
        checkValidId(id);
        Player player = repository.findById(id).orElseThrow((IllegalStateException::new));


        if (playerRequstBody.getName() != null) player.setName(playerRequstBody.getName());
        if (playerRequstBody.getTitle() != null) player.setTitle(playerRequstBody.getTitle());
        if (playerRequstBody.getRace() != null) player.setRace(playerRequstBody.getRace());
        if (playerRequstBody.getProfession() != null) player.setProfession(playerRequstBody.getProfession());
        if (playerRequstBody.getBirthday() != null) {
            if (getYear(playerRequstBody.getBirthday()) < 2000 || getYear(playerRequstBody.getBirthday()) > 3000) throw new BadRequestException();
            player.setBirthday(new Date(playerRequstBody.getBirthday()));
        }
        if (playerRequstBody.getExperience() != null) {
            if (playerRequstBody.getExperience() > 10000000 || playerRequstBody.getExperience() < 0) throw new BadRequestException();
            player.setExperience(playerRequstBody.getExperience());

            int level = (int) ((Math.sqrt(2500 + 200 * playerRequstBody.getExperience()) - 50) / (100));
            int untilNextLevel = 50 * (level + 1) * (level + 2) - playerRequstBody.getExperience();

            player.setUntilNextLevel(untilNextLevel);
            player.setLevel(level);
        }
        if (playerRequstBody.getBanned() != null) player.setBanned(playerRequstBody.getBanned());


        return repository.save(player);
    }

    private void checkValidId(Long id) {
        if (id == null || id < 1) {
            throw new BadRequestException();
        }
        if (!hasPlayer(id)) {
            throw new NotFoundException();
        }
    }




    static class compreName implements Comparator<Player>{

        @Override
        public int compare(Player o1, Player o2) {
            return  o1.getName().compareTo(o2.getName());
        }
    }
    static class compreBirthday implements Comparator<Player>{

        @Override
        public int compare(Player o2, Player o1) {
            return Long.compare(o2.getBirthday().getTime(), o1.getBirthday().getTime());
        }
    }
    static class compareLevel implements Comparator<Player>{

        @Override
        public int compare(Player o2, Player o1) {
            return Integer.compare(o2.getLevel(), o1.getLevel());
        }
    }

    static class compareExperience implements Comparator<Player> {

        @Override
        public int compare(Player o2, Player o1) {
            return Integer.compare(o2.getExperience(), o1.getExperience());
        }
    }

    static class compreId implements Comparator<Player>{

        @Override
        public int compare(Player o2, Player o1) {
            return Long.compare(o2.getId(), o1.getId());
        }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public class NotFoundException extends RuntimeException {
    }







}
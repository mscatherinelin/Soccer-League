package com.teamtreehouse.model;

import java.util.*;

public class Team {
    public List<Player> mTeamMembers;
    private String mCoach;
    private String mTeamName;

    public Team(String coach, String teamName) {
        mCoach = coach;
        mTeamName = teamName;
        mTeamMembers = new ArrayList<Player>();
    }

    public List<String> getTeamMembers() {
        Collections.sort(mTeamMembers);
        List<String> teamMembers = new ArrayList<String>();
        for (Player player : mTeamMembers) {
            teamMembers.add(player.toString());
        }
        return teamMembers;
    }

    public String getCoach() {
        return mCoach;
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void addPlayer(Player player) {
        mTeamMembers.add(player);
    }

    public Player removePlayer(int index) {
        return mTeamMembers.remove(index);
    }

    private Map<String, List<Player>> getHeightReport() {
        //create a map that maps players to a height range
        Map<String, List<Player>> report = new HashMap<String, List<Player>>();

        for (Player player : mTeamMembers) {
            if (player.getHeightInInches() >= 35 && player.getHeightInInches() <= 40) {
                //get all the players within that range
                List<Player> range1 = report.get("35-40");
                if (range1 == null) {
                    range1 = new ArrayList<Player>();
                    report.put("35-40", range1);
                }
                range1.add(player);
            } else if (player.getHeightInInches() >= 41 && player.getHeightInInches() <= 46) {
                //get all the players within that range
                List<Player> range2 = report.get("41-46");
                if (range2 == null) {
                    range2 = new ArrayList<Player>();
                    report.put("41-46", range2);
                }
                range2.add(player);
            } else if (player.getHeightInInches() >= 47 && player.getHeightInInches() <= 50) {
                //get all the players within that range
                List<Player> range3 = report.get("47-50");
                if (range3 == null) {
                    //if no players exist create a new list of players, and add ths current player to that list
                    range3 = new ArrayList<Player>();
                    report.put("47-50", range3);
                }
                range3.add(player);
            }

        }
        return report;
    }

    public Set<String> getRanges() {
        return getHeightReport().keySet();
    }

    public List<Player> getPlayersForRange(String range) {
        return getHeightReport().get(range);
    }

    private Map<String, List<Player>> getExperienceReport() {
        //map players to experience
        Map<String, List<Player>> experienceReport = new HashMap<String, List<Player>>();
        for (Player player : mTeamMembers) {
            if (player.isPreviousExperience()) {
                //obtain players with previous experience
                List<Player> experienced = experienceReport.get("Experienced");
                if (experienced == null) {
                    //if list does not exist, create new list and add it to hash map
                    experienced = new ArrayList<Player>();
                    experienceReport.put("Experienced", experienced);
                }
                //add player to list
                experienced.add(player);
            } else {
                List<Player> inexperienced = experienceReport.get("Inexperienced");
                if (inexperienced == null) {
                    inexperienced = new ArrayList<Player>();
                    experienceReport.put("Inexperienced", inexperienced);
                }
                inexperienced.add(player);
            }
        }
        return experienceReport;
    }

    public Set<String> getExperience() {
        return getExperienceReport().keySet();
    }

    public List<Player> getPlayersExperience(String experience) {
        return getExperienceReport().get(experience);
    }


}
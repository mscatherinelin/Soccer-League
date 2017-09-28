package com.teamtreehouse.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

public class League {
    private List<Team> mTeams;

    public League() {
        mTeams = new ArrayList<Team>();
    }

    public void addTeam(Team team) {
        mTeams.add(team);
    }

    public Team getTeam(int index) {
        return mTeams.get(index);

    }

    public List<String> getTeamNames() {
        List<String> teamNames = new ArrayList<String>();
        for (Team team : mTeams) {
            teamNames.add(team.getTeamName());
        }
        return teamNames;
    }

    private Map<String, List<String>> getRoster() {
        //get all players associated with a team
        Map<String, List<String>> roster = new TreeMap<>();
        for(Team team : mTeams)
            roster.put(team.getTeamName(), team.getTeamMembers());
        return roster;

    }

    public Set<String> getTeamName(){
        return getRoster().keySet();
    }

    public List<String> getTeamMembers(String teamName){
        return getRoster().get(teamName);
    }




}
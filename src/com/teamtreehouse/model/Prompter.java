package com.teamtreehouse.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Prompter {
    private BufferedReader mReader;
    private Map<String, String> mMenu;
    private League mLeague;

    public Prompter(League league) {
        mMenu = new HashMap<String, String>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mLeague = league;
        mMenu.put("create", "Create a new team");
        mMenu.put("add", "Add players to your team");
        mMenu.put("quit", "Exit the program");
        mMenu.put("Height report", "View a report of the team by height");
        mMenu.put("Experience report", "View a report of the team by experience");
        mMenu.put("Roster", "View a complete roster of the entire league");
        mMenu.put("remove", "Remove players from your team");

    }


    private String promptAction() throws IOException {
        System.out.printf("Your options are: %n");
        for (Map.Entry<String, String> option : mMenu.entrySet()) {
            System.out.printf("%s - %s %n",
                    option.getKey(),
                    option.getValue());

        }
        System.out.printf("Please choose from the above: %n");
        String choice = mReader.readLine();
        return choice.trim().toLowerCase();
    }

    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        if (options.size() == 0)
            return -1;

        for (String option : options) {
            System.out.printf("%d.) %s %n", counter, option);
            counter++;
        }
        System.out.print("Select an option:  ");
        String optionAsString = mReader.readLine();
        int choice = Integer.parseInt(optionAsString.trim());
        return choice - 1;
    }

    private void promptToCreateTeam() throws IOException {
        System.out.print("Pick a name for your team:  ");
        String teamName = mReader.readLine();
        System.out.print("What is the name of your coach?  ");
        String coach = mReader.readLine();
        Team newTeam = new Team(coach, teamName);
        mLeague.addTeam(newTeam);
    }

    private void promptNewPlayer() throws IOException {
        //create the roster of all available players to be chosen from
        System.out.println("Pick a team: ");
        List<String> teamNames = mLeague.getTeamNames();
        int teamIndex = promptForIndex(teamNames);

        if (teamIndex == -1) {
            System.out.println("Please create a team first");
            return;
        }

        System.out.println("Pick a player from the following list: ");
        List<Player> allPlayers = Arrays.asList(Players.load());
        List<String> playersToString = Players.playersToString(allPlayers);
        int index = promptForIndex(playersToString);
        Team team = mLeague.getTeam(teamIndex);
        Player player = allPlayers.get(index);
        team.addPlayer(player);

    }

    private void promptHeightReport() throws IOException {
        System.out.println("Pick a team :");
        List<String> teamNames = mLeague.getTeamNames();
        int teamIndex = promptForIndex(teamNames);
        Team team = mLeague.getTeam(teamIndex);
        Set<String> ranges = team.getRanges();
        for (String range : ranges) {
            List<Player> players = team.getPlayersForRange(range);
            System.out.printf("Players within the range %s (%d)%n", range, players.size());
            for (Player player : players) {
                System.out.printf("%s %s %n", player.getFirstName(), player.getLastName());
            }
        }
    }

    private void promptRoster(){
        System.out.println("Below is the roster for the soccer league");
        Set<String> teamNames = mLeague.getTeamName();
        for(String teamName : teamNames){
            System.out.printf("Players for %s: ", teamName);
            List<String> teamPlayers = mLeague.getTeamMembers(teamName);
            for(String player : teamPlayers){
                System.out.printf("%s ", player);
            }
            System.out.println();
        }

    }

    private void promptExperienceReport() throws IOException {
        System.out.println("Pick a team :");
        List<String> teamNames = mLeague.getTeamNames();
        int teamIndex = promptForIndex(teamNames);
        Team team = mLeague.getTeam(teamIndex);
        Set<String> experiences = team.getExperience();
        for (String experience : experiences) {
            List<Player> players = team.getPlayersExperience(experience);
            System.out.printf("%s players: %n", experience);
            for (Player player : players) {
                System.out.printf("%s %s %n", player.getFirstName(), player.getLastName());
            }
        }
    }

    private void removePlayer() throws IOException {
        System.out.println("Pick a team: ");
        List<String> teamNames = mLeague.getTeamNames();
        int teamIndex = promptForIndex(teamNames);
        Team team = mLeague.getTeam(teamIndex);
        int playerIndex = promptForIndex(team.getTeamMembers());
        if (playerIndex == -1)
            System.out.println("There are no players on this team!");


    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "create":
                        promptToCreateTeam();
                        System.out.println("New team created");
                        break;
                    case "add":
                        //add players to team
                        promptNewPlayer();
                        break;
                    case "height report":
                        promptHeightReport();
                        break;
                    case "experience report":
                        promptExperienceReport();
                        break;
                    case "remove":
                        removePlayer();
                        break;
                    case "roster":
                        promptRoster();
                        break;
                    case "quit":
                        System.out.println("Goodbye");
                        break;
                    default:
                        System.out.printf("Uknown choice: '%s'. Please try again. %n%n%n", choice);
                }
            } catch (IOException ioe) {
                System.out.println("Problem with inpue");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }
}
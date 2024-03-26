# Homework 1

## Objectives

AwesomeTournament is a web application whose main purpose is to organize sport tournaments, manage teams, and follow results and rankings for every tournament. It offers an opportunity to tournament organizers to create different types of tournaments, create draws and keep track of teams and players. On the other hand, basic users could pick the tournament they are interested in and follow the match results and statistics.

## Main functionalities

### Creation of a new tournament

This funcionality offers an opportunity to everyone to create a sport tournament. On the main page of the web app, it is possible to create an account which is required for the tournament creation. After successfully completing the sign up proccess, there are other steps which should be taken such as providing basic data about the tournament (Name, Logo, Deadline for the team creation, Max number of players, Max number of teams etc.).

### Invitation of teams to participate

After the tournament is created, the tournament creator has ability to send an invitation link to the teams interested in the tournament.

### Creation of the team for the tournament

The next step after receiving the invitation link is the team creation. During this phase one member of the team would create an account for the team. After successfully creation of an account, other team members could access to the account using appropriate credentials. During this phase it is possible to add required data about the players such as Full Name, Date of Birth, Position, upload the medical certificate but it is also possible to save it as a draft and continue later.

### Addition/edit/deletion of players

Each member of the team should have an access to the team account which would allow him/her to read, add, edit or delete players to/from the team. In order to do that, the user should visit the main page of the app, choose the desired tournament and then login to the team account.

### Approvation/rejection of a team

An admin of the app could approve or reject tournaments which are created by the tournament creators. Only the approved tournaments can be organized, while rejected ones can not.

### Follow of the tournament results

Another feature of the app is following results of the tournament matches, current statistic etc. That feature is available for every active tournament, after choosing the particular tournament from the app's main page (the user don't have to be logged in).

### Creation of the draw

Each tournament creator has an ability to make a draw for the tournament (there are multiple options for making a draw). After the deadline for the teams creation is met, the tournament creator can make a draw for the tournament, which is going to be visible on the page for every tournament (after the tournament creator is logged in).

### Match logging

The tournament creator can log to the tournament page and edit the result of the matches so that other users could see them.

## Data Logic Layer

The ER schema contains 6 entities which are going to be described in details in this section.

1. User 

Entity Type User contains ID (INT), Email (CHAR) and Password (CHAR) for every user. The primary key is ID, while Email is a unique column. Every user can create one or more tournaments as well as one or more teams.

2. Tournament

Entity Type Tournament contains ID (INT), Name (CHAR), Token (CHAR), Creator_User_ID (INT), Max_Teams (INT), Max_Players (INT), Min_Players (INT), Starting_Players (INT), Max_Substitutions (INT), Deadline (DATETIME), Start_Date (DATETIME), Creation_Date (DATETIME), Logo (CHAR) and Is_Finished (BOOL). The primary key is ID, Name is a unique column while Creator_User_ID is a foreign key contraint to the User table. Every tournament must be created by exactly one user. Logo contains path to the file which represents logo for a particular tournament. Max_Players and Min_Players are maximum and minimum numbers of players for each team in the tournament, while Starting_Players is a number of players in each match of the tournament.

3. Team 

Entity Type Team contains ID (INT), Name (CHAR), Logo (CHAR), Creator_User_ID (INT) and Tournament_ID (INT). The primary key is ID, Name is a unique column while Creator_User_ID is a foreign key contraint to the User table and Tournament_ID is a foreign key contraint to the Tournament table. Every Team must belong to the one tournament and must be created by some user. This Entity Type can have multiple players and can play multiple matches.

4. Player

Entity Type Player contains ID (INT), Name (CHAR), Surname (CHAR), Team_ID (INT), Position (ENUM), Medical_Certificate (CHAR), Date_Of_Birth (DATETIME). The primary key is ID while Team_ID is a foreign key contraint to the Team table. Every player must belong to some team and can be participant in the events. Medical_Certificate contains path to the file which represents Medical Certificate for a particular player.

5. Match

Entity Type Match contains ID (INT), Team1_ID (INT), Team2_ID (INT), Tournament_ID (INT), Team1_Score (INT), Team2_Score (INT), Result (CHAR), Referee (CHAR), Match_Date (DATETIME) and Is_Finished (BOOL). The primary key is ID while Team1_ID and Team2_ID are foreign keys contraint to the Team table. Tournament_ID is a foreign key contraint to the Tournament table. Every match must belong to some tournament, have up to two teams and can have multiple event associated to it.

6. Event

Entity Type Event contains ID (INT), Match_ID (INT), Player_ID (INT), Type (Enum) and Time (DATETIME). The primary key is ID while Match_ID is a foreign key contraint to the Match table and Player_ID is a foreign key contraint to the Player table. Every event belongs to one match and has one Player who made the event. Type of event can be Goal, Substitution or Faul.
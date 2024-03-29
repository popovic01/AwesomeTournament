| URI | Method | Description |
| :---------| :---------: | ------: |
| /users | POST | Create a new user |
| /users/[user] | GET | Return [user] fields |
| /users/[user] | PUT | Update [user] fields |
| /users/[user] | DELETE | Delete [user] |
| /tournaments | POST | Create a new tournament. The user must be logged. Includes all the tournament features that must be saved in the db |
| /tournaments | GET | Return a list of the existing tournaments |
| /tournaments/[tournament] | GET | Return [tournament] fields |
| /tournaments/[tournament] | PUT | Update [tournament] fields |
| /tournaments/[tournament] | DELETE | Delete [tournament] |
| /tournaments/[tournament]/teams | POST | Create a new team in [tournament]. The user must be logged |
| /tournaments/[tournament]/teams | GET | Return a list of the teams in [tournament] |
| /tournaments/[tournament]/teams/[team] | GET | Return [team] fields |
| /tournaments/[tournament]/teams/[team] | PUT | Update [team] fields |
| /tournaments/[tournament]/teams/[team] | DELETE | Delete [team] |
| /tournaments/[tournament]/teams/[team]/players | POST | Create a new player in [team]. Player fields must be filled within the [tournament] deadline |
| /tournaments/[tournament]/teams/[team]/players/[player] | GET | Return the fields of [player] |
| /tournaments/[tournament]/teams/[team]/players/[player] | PUT | Update [player] fields |
| /tournaments/[tournament]/teams/[team]/players/[player] | DELETE | Delete [player] |
| /tournaments/[tournament]/matches | GET | Return the list of all the matches in [tournament] |
| /tournaments/[tournament]/matches/[match] | GET | Return [match] fields |
| /tournaments/[tournament]/matches/[match] | PUT | Update [match] fields |
| /tournaments/[tournament]/matches/[match] | DELETE | Delete [match] |
| /tournaments/[tournament]/ranking | GET | Return the ranking of [tournament] |
| /tournaments/[tournament]/scorers_ranking | GET | Return the scorers ranking of [tournament] |
| /auth/login | POST | Send email and password to be checked |

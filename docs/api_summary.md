| URI | Method | Description |
| :---------| :---------: | ------: |
| awesometournament/[user] | POST | Create a new user |
| awesometournament/[user] | PUT | Update [user] fields |
| awesometournament/[user] | DELETE | Delete [user] |
| awesometournament/[tournament] | POST | Create a new tournament. The user must be logged. Includes all the tournament features that must be saved in the db |
| awesometournament/[tournament] | PUT | Update [tournament] fields |
| awesometournament/[tournament] | DELETE | Delete [tournament] |
| awesometournament/login | POST | Send email and password to be checked |
| awesometournament/list | GET | Return a list of the existing tournaments |
| [tournament]/[team] | POST | Create a new team in [tournament]. The user must be logged |
| [tournament]/[team] | GET | Return [team] fields |
| [tournament]/[team]/[player] | POST | Create a new player in [team]. Player fields must be filled within the [tournament] deadline |
| [tournament]/[team]/[player] | PUT | Update [player] fields |
| [tournament]/[team]/[player] | GET | Return the fields of [player]
| [tournament]/[match] | PUT | Update [match] fields |
| [tournament]/[match] | DELETE | Delete [match] |
| [tournament]/[match] | GET | Return [match] fields |
| [tournament]/ranking | GET | Return the ranking of [tournament] |
| [tournament]/scorers_ranking | GET | Return the scorers ranking of [tournament] |
| [tournament]/list_teams | GET | Return a list of the teams in [tournament] |
| [tournament]/list_matches | GET | Return the list of all the matches in [tournament] |

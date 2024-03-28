| URI | Method | Description |
| :---------| :---------: | ------: |
| /user | POST | Create a new user |
| /user/[user] | GET | Return [user] fields |
| /user/[user] | PUT | Update [user] fields |
| /user/[user] | DELETE | Delete [user] |
| /tournament | POST | Create a new tournament. The user must be logged. Includes all the tournament features that must be saved in the db |
| /tournament | GET | Return a list of the existing tournaments |
| /tournament/[tournament] | GET | Return [tournament] fields |
| /tournament/[tournament] | PUT | Update [tournament] fields |
| /tournament/[tournament] | DELETE | Delete [tournament] |
| /auth/login | POST | Send email and password to be checked |
| /[tournament]/team | POST | Create a new team in [tournament]. The user must be logged |
| /[tournament]/team | GET | Return a list of the teams in [tournament] |
| /[tournament]/[team] | GET | Return [team] fields |
| /[tournament]/[team] | PUT | Update [team] fields |
| /[tournament]/[team] | DELETE | Delete [team] |
| /[tournament]/[team]/player | POST | Create a new player in [team]. Player fields must be filled within the [tournament] deadline |
| /[tournament]/[team]/[player] | GET | Return the fields of [player] |
| /[tournament]/[team]/[player] | PUT | Update [player] fields |
| /[tournament]/[team]/[player] | DELETE | Delete [player] |
| /[tournament]/match | GET | Return the list of all the matches in [tournament] |
| /[tournament]/[match] | GET | Return [match] fields |
| /[tournament]/[match] | PUT | Update [match] fields |
| /[tournament]/[match] | DELETE | Delete [match] |
| /[tournament]/ranking | GET | Return the ranking of [tournament] |
| /[tournament]/scorers_ranking | GET | Return the scorers ranking of [tournament] |

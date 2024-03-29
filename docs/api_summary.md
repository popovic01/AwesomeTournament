| URI | Method | Description |
| :---------| :---------: | ------: |
| /users | POST | Creates a new user |
| /users/[user] | GET | Returns [user] fields |
| /users/[user] | PUT | Updates [user] fields |
| /users/[user] | DELETE | Deletes [user] |
| /tournaments | POST | Creates a new tournament connected to the user making the request. |
| /tournaments | GET | Returns a list of all tournaments |
| /tournaments/[tournament] | GET | Returns [tournament] fields |
| /tournaments/[tournament] | PUT | Updates [tournament] fields |
| /tournaments/[tournament] | DELETE | Deletes [tournament] |
| /tournaments/[tournament]/teams | POST | Creates a new team in [tournament] |
| /tournaments/[tournament]/teams | GET | Returns a list of all teams in [tournament] |
| /tournaments/[tournament]/teams/[team] | GET | Returns [team] fields |
| /tournaments/[tournament]/teams/[team] | PUT | Updates [team] fields |
| /tournaments/[tournament]/teams/[team] | DELETE | Deletes [team] |
| /tournaments/[tournament]/teams/[team]/players | POST | Creates a new player in [team]. |
| /tournaments/[tournament]/teams/[team]/players/[player] | GET | Returns the fields of [player] |
| /tournaments/[tournament]/teams/[team]/players/[player] | PUT | Updates [player] fields |
| /tournaments/[tournament]/teams/[team]/players/[player] | DELETE | Deletes [player] |
| /tournaments/[tournament]/matches | GET | Returns the list of all matches in [tournament] |
| /tournaments/[tournament]/matches/[match] | GET | Returns [match] fields |
| /tournaments/[tournament]/matches/[match] | PUT | Updates [match] fields |
| /tournaments/[tournament]/matches/[match] | DELETE | Deletes [match] |
| /tournaments/[tournament]/ranking | GET | Returns the ranking of [tournament] |
| /tournaments/[tournament]/scorers_ranking | GET | Returns the scorers ranking of [tournament] |
| /auth/login | POST | Sends email and password to be checked |

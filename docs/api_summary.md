| URI | Method | Description |
| :---------| :---------: | ------: |
| /auth/login | POST | Sends email and password to be checked |
| api/users | POST | Creates a new user |
| api/users/[user] | GET | Returns [user] fields |
| api/users/[user] | PUT | Updates [user] fields |
| api/users/[user] | DELETE | Deletes [user] |
| api/tournaments | GET | Returns a list of all tournaments |
| api/tournaments | POST | Creates a new tournament connected to the user making the request. |
| api/tournaments/[tournament] | GET | Returns [tournament] fields |
| api/tournaments/[tournament] | PUT | Updates [tournament] fields |
| api/tournaments/[tournament] | DELETE | Deletes [tournament] |
| api/tournaments/[tournament]/teams | GET | Returns a list of all teams in [tournament] |
| api/tournaments/[tournament]/teams | POST | Create a team connected to [tournament] |
| api/tournaments/[tournament]/matches | GET | Returns the list of all matches in [tournament] |
| api/tournaments/[tournament]/ranking | GET | Returns the ranking of [tournament] |
| api/tournaments/[tournament]/scorers_ranking | GET | Returns the scorers ranking of [tournament] |
| api/matches/[match] | GET | Returns [match] fields |
| api/matches/[match] | PUT | Updates [match] fields |
| api/matches/[match]/events | GET | Returns a list of all the events in [match] |
| api/matches/[match]/events | POST | Creates a new event connected to [match] |
| api/events/[event] | PUT | Updates an event |
| api/events/[event] | DELETE | Deletes an event |
| api/teams/[team] | GET | Returns [team] fields |
| api/teams/[team] | PUT | Updates [team] fields |
| api/teams/[team] | DELETE | Deletes [team] |
| api/teams/[team]/players | POST | Creates a new player in [team]. |
| api/teams/[team]/players | GET | Returns a list of all players in [team]. |
| api/players/[player] | GET | Returns the fields of [player] |
| api/players/[player] | PUT | Updates [player] fields |
| api/players/[player] | DELETE | Deletes [player] |

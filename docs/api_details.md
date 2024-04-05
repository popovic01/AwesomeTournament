# Create a player in [team]

The following endpoint allows the [team] owner to add a new player to the team

- **URL**: api/teams/[team]/players
- **Method**: POST
- **URL Params**:
  - [team]: the identifier of the team to which the player will belong
- **Data Params**:
  - ```
    {
      "name": player_name,
      "surname": player_surname,
      "team_id": player_team_id,
      "position": player_position,
      "medical_certificate": player_medical_certificate,
      "date_of_birth": player_date_of_birth
    }
    ```
- **Success Response**:
  - Code: 200
  - Content: 
    - ```
      {
        "result": "successful"
      }
      ```
- **Error Response**: **TODO**

# Get a list of all the players in [team]

The following endpoint returns the list of all the players belonging to [team]

- **URL**: api/teams/[team]/players
- **Method**: GET
- **URL Params**:
  - [team]: the identifier of the team
- **Data Params**: No data params needed
- **Success Response**:
  - Code: 200
  - Content: 
    - ```
      [
        {
          "ID": player1_ID,
          "name": player1_name,
          "surname": player1_surname,
          "team_id": player1_team_id,
          "position": player1_position,
          "medical_certificate": player1_medical_certificate,
          "date_of_birth": player1_date_of_birth
        },
        ...
      ]
      ```
- **Error Response**: **TODO**

# Get [players] fields

The following endpoint returns [player] fields

- **URL**: api/players/[player]
- **Method**: GET
- **URL Params**:
  - [player]: the identifier of the player
- **Data Params**: No data params needed
- **Success Response**:
  - Code: 200
  - Content: 
    - ```
      {
        "ID": player_ID,
        "name": player_name,
        "surname": player_surname,
        "team_id": player_team_id,
        "position": player_position,
        "medical_certificate": player_medical_certificate,
        "date_of_birth": player_date_of_birth
      }
      ```
- **Error Response**: **TODO**

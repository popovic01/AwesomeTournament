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
  - Code: 201
  - Content: 
    - ```
      {
        "result": "Resource created"
      }
      ```
- **Error Response**:
  - Code: 302
  - Content:
    - ```
      {
        "error": {
          "code": 302,
          "message": "User not logged in"
        }
      }
      ```
  - Code: 400
  - Content:
    - ```
      {
        "error": {
          "code": 400,
          "message": "Wrong request format"
        }
      }
      ```
  - Code: 403
  - Content:
    - ```
      {
        "error": {
          "code": 403,
          "message": "User doesn't have the necessary permissions"
        }
      }
      ```
  - Code: 503
  - Content:
    - ```
      {
        "error": {
          "code": 503,
          "message": "Server not ready"
        }
      }
      ```

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
- **Error Response**:
  - Code: 204
  - Content:
    - ```
      {
        "error": {
          "code": 204,
          "message": "No content"
        }
      }
      ```
  - Code: 503
  - Content:
    - ```
      {
        "error": {
          "code": 503,
          "message": "Server not ready"
        }
      }
      ```

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
- **Error Response**:
  - Code: 404
  - Content:
    - ```
      {
        "error": {
          "code": 404,
          "message": "Not found"
        }
      }
      ```
  - Code: 503
  - Content:
    - ```
      {
        "error": {
          "code": 503,
          "message": "Server not ready"
        }
      }
      ```

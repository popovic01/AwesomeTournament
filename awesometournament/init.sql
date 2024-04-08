CREATE USER awesome;
CREATE DATABASE awesome;
GRANT ALL PRIVILEGES ON DATABASE awesome TO awesome;

\c awesome;

CREATE TABLE "user" (
        id SERIAL PRIMARY KEY,
        email VARCHAR UNIQUE NOT NULL,
        password VARCHAR NOT NULL
);

CREATE TABLE "tournament" (
        id SERIAL PRIMARY KEY,
        name VARCHAR NOT NULL,
        token VARCHAR NOT NULL,
        creator_user_id INT NOT NULL,
        max_teams INT NOT NULL,
        max_players INT NOT NULL,
        min_players INT NOT NULL,
        starting_players INT NOT NULL,
        max_substitutions INT NOT NULL,
        deadline TIMESTAMP WITH TIME ZONE,
        start_date TIMESTAMP WITH TIME ZONE,
        creation_date TIMESTAMP WITH TIME ZONE,
        logo VARCHAR NULL,
        is_finished BOOLEAN NOT NULL,
        FOREIGN KEY (creator_user_id) REFERENCES "user" (id)
);

CREATE TABLE "team" (
        id SERIAL PRIMARY KEY,
        name VARCHAR NOT NULL,
        logo VARCHAR NULL,
        creator_user_id INT NOT NULL,
        tournament_id INT NOT NULL,
        FOREIGN KEY (creator_user_id) REFERENCES "user" (id),
        FOREIGN KEY (tournament_id) REFERENCES "tournament" (id)
);

CREATE TYPE match_result as ENUM ('team1', 'team2', 'draw');

CREATE TABLE "match" (
        id SERIAL PRIMARY KEY,
        team1_id INT NOT NULL,
        team2_id INT NOT NULL,
        tournament_id INT NOT NULL,
        team1_score INT NULL,
        team2_score INT NULL,
        result match_result NULL,
        referee VARCHAR NOT NULL,
        match_date TIMESTAMP WITH TIME ZONE,
        is_finished BOOLEAN NOT NULL, -- we could also check "result IS NULL"
        FOREIGN KEY (team1_id) REFERENCES "team" (id),
        FOREIGN KEY (team2_id) REFERENCES "team" (id),
        FOREIGN KEY (tournament_id) REFERENCES "tournament" (id)
);

CREATE TYPE player_position as ENUM ('goalkeeper', 'defender', 'midfielder', 'striker');

CREATE TABLE "player" (
        id SERIAL PRIMARY KEY,
        name VARCHAR NOT NULL,
        surname VARCHAR NOT NULL,
        team_id INT NOT NULL,
        position player_position NULL,
        medical_certificate VARCHAR NULL,
        date_of_birth DATE NOT NULL,
        FOREIGN KEY (team_id) REFERENCES "team" (id)
);

CREATE TYPE event_type as ENUM ('goal', 'yellow card', 'red card');

CREATE TABLE "event" (
        id SERIAL PRIMARY KEY,
        match_id INT NOT NULL,
        player_id INT NOT NULL,
        type event_type NOT NULL,
        time INT NOT NULL,
        FOREIGN KEY (match_id) REFERENCES "match" (id),
        FOREIGN KEY (player_id) REFERENCES "player" (id)
);

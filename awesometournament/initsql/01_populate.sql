\c awesome;

-- Inserting users
INSERT INTO public.users (email, password) VALUES
  ('user1@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user2@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user3@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user4@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user5@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user6@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user7@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user8@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user9@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8'),
  ('user10@example.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');

-- Inserting Serie A championship
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, creation_date, is_finished)
VALUES
  ('Serie A 2024/2025', 'seriea2425', 1, 20, 11, 7, 11, 5, '2024-08-01', NOW(), false);

-- Inserting teams for Serie A, each inserted by a different user
INSERT INTO public.teams (name, creator_user_id, tournament_id) VALUES
  ('AC Milan', 1, 1),
  ('Inter Milan', 2, 1),
  ('Juventus', 3, 1),
  ('AS Roma', 4, 1),
  ('Napoli', 5, 1),
  ('Lazio', 6, 1),
  ('Atalanta', 7, 1),
  ('Fiorentina', 8, 1),
  ('Sampdoria', 9, 1),
  ('Torino', 10, 1);

-- Inserting players for each Serie A team
-- You can add more players as needed
INSERT INTO public.players (name, surname, team_id, position, date_of_birth) VALUES
  -- AC Milan players
  ('Gianluigi', 'Donnarumma', 1, 'goalkeeper', '1999-02-25'),
  ('Theo', 'Hernández', 1, 'defender', '1997-10-06'),
  ('Franck', 'Kessié', 1, 'midfielder', '1996-12-19'),
  ('Zlatan', 'Ibrahimović', 1, 'striker', '1981-10-03'),
  -- Inter Milan players
  ('Samir', 'Handanović', 2, 'goalkeeper', '1984-07-14'),
  ('Stefan', 'de Vrij', 2, 'defender', '1992-02-05'),
  ('Nicolo', 'Barella', 2, 'midfielder', '1997-02-27'),
  ('Romelu', 'Lukaku', 2, 'striker', '1993-05-13'),
  -- Juventus players
  ('Wojciech', 'Szczęsny', 3, 'goalkeeper', '1990-04-18'),
  ('Giorgio', 'Chiellini', 3, 'defender', '1984-08-14'),
  ('Paulo', 'Dybala', 3, 'striker', '1993-11-15'),
  ('Federico', 'Chiesa', 3, 'striker', '1997-10-25'),
  -- AS Roma players
  ('Pau', 'López', 4, 'goalkeeper', '1994-12-13'),
  ('Leonardo', 'Spinazzola', 4, 'defender', '1993-03-25'),
  ('Henrikh', 'Mkhitaryan', 4, 'midfielder', '1989-01-21'),
  ('Tammy', 'Abraham', 4, 'striker', '1997-10-02'),
  -- Napoli players
  ('Alex', 'Meret', 5, 'goalkeeper', '1997-03-22'),
  ('Kalidou', 'Koulibaly', 5, 'defender', '1991-06-20'),
  ('Lorenzo', 'Insigne', 5, 'striker', '1991-06-04'),
  ('Victor', 'Osimhen', 5, 'striker', '1998-12-29'),
  -- Lazio players
  ('Thomas', 'Strakosha', 6, 'goalkeeper', '1995-03-19'),
  ('Francesco', 'Acerbi', 6, 'defender', '1988-02-10'),
  ('Sergej', 'Milinković-Savić', 6, 'midfielder', '1995-02-27'),
  ('Ciro', 'Immobile', 6, 'striker', '1990-02-20'),
  -- Atalanta players
  ('Pierluigi', 'Gollini', 7, 'goalkeeper', '1995-03-18'),
  ('José', 'Luis Palomino', 7, 'defender', '1990-01-05'),
  ('Remo', 'Freuler', 7, 'midfielder', '1992-04-15'),
  ('Duván', 'Zapata', 7, 'striker', '1991-04-01'),
  -- Fiorentina players
  ('Bartłomiej', 'Dragowski', 8, 'goalkeeper', '1997-07-06'),
  ('Nikola', 'Milenković', 8, 'defender', '1997-10-12'),
  ('Gaetano', 'Castrovilli', 8, 'midfielder', '1997-01-17'),
  ('Dusan', 'Vlahovic', 8, 'striker', '2000-01-28'),
  -- Sampdoria players
  ('Emil', 'Audero', 9, 'goalkeeper', '1997-01-18'),
  ('Maya', 'Yoshida', 9, 'defender', '1988-08-24'),
  ('Morten', 'Thorsby', 9, 'midfielder', '1996-05-05'),
  ('Fabio', 'Quagliarella', 9, 'striker', '1983-01-31'),
  -- Torino players
  ('Salvatore', 'Sirigu', 10, 'goalkeeper', '1987-01-12'),
  ('Bremer', 'Rodrigues', 10, 'defender', '1997-03-03'),
  ('Sasa', 'Lukic', 10, 'midfielder', '1996-08-25'),
  ('Andrea', 'Belotti', 10, 'striker', '1993-12-20');

-- Inserting matches for Serie A based on provided events
INSERT INTO public.matches (team1_id, team2_id, tournament_id, team1_score, team2_score, result, referee, match_date, is_finished) VALUES
  -- Match 1: AC Milan vs. Inter Milan
  (1, 2, 1, 2, 1, 'team1', 'Michael Fabbri', '2023-08-15 18:00:00', true),
  -- Match 2: Juventus vs. AS Roma
  (3, 4, 1, 2, 1, 'team1', 'Daniele Doveri', '2023-08-16 15:30:00', true),
  -- Match 3: Napoli vs. Lazio
  (5, 6, 1, 3, 1, 'team1', 'Gianpaolo Calvarese', '2023-08-17 16:45:00', true),
  -- Match 4: Atalanta vs. Fiorentina
  (7, 8, 1, 3, 1, 'team1', 'Martin Atkinson', '2023-08-18 14:00:00', true),
  -- Match 5: Sampdoria vs. Torino
  (9, 10, 1, 2, 2, 'draw', 'Anthony Taylor', '2023-08-19 17:30:00', true),
  -- Match 6: Inter Milan vs. Juventus
  (2, 3, 1, 2, 2, 'draw', 'Michael Fabbri', '2023-08-21 16:00:00', true),
  -- Match 7: AS Roma vs. Napoli
  (4, 5, 1, 2, 2, 'draw', 'Daniele Doveri', '2023-08-22 14:30:00', true),
  -- Match 8: Lazio vs. Atalanta
  (6, 7, 1, 2, 2, 'draw', 'Gianpaolo Calvarese', '2023-08-23 15:15:00', true),
  -- Match 9: Fiorentina vs. Sampdoria
  (8, 9, 1, 1, 3, 'team2', 'Martin Atkinson', '2023-08-24 17:45:00', true),
  -- Match 10: Torino vs. AC Milan
  (10, 1, 1, 2, 1, 'team1', 'Anthony Taylor', '2023-08-25 18:30:00', true);

-- Inserting scheduled but not yet played matches with existing referees
INSERT INTO public.matches (team1_id, team2_id, tournament_id, match_date, referee, is_finished) VALUES
  -- Scheduled match 11: AC Milan vs. Juventus
  (1, 3, 1, '2023-09-01 17:00:00', 'Michael Fabbri', false),
  -- Scheduled match 12: AS Roma vs. Inter Milan
  (4, 2, 1, '2023-09-02 19:45:00', 'Daniele Doveri', false),
  -- Scheduled match 13: Lazio vs. Napoli
  (6, 5, 1, '2023-09-03 15:30:00', 'Gianpaolo Calvarese', false),
  -- Scheduled match 14: Fiorentina vs. Torino
  (8, 10, 1, '2023-09-04 20:00:00', 'Martin Atkinson', false),
  -- Scheduled match 15: Sampdoria vs. Atalanta
  (9, 7, 1, '2023-09-05 18:15:00', 'Anthony Taylor', false),
  -- Scheduled match 16: Inter Milan vs. Lazio
  (2, 6, 1, '2023-09-06 16:30:00', 'Michael Fabbri', false);

-- Inserting matches that aren't scheduled yet with existing referees
INSERT INTO public.matches (team1_id, team2_id, tournament_id, referee, is_finished) VALUES
  -- Unscheduled match 17: Juventus vs. Napoli
  (3, 5, 1, 'Daniele Doveri', false),
  -- Unscheduled match 18: Atalanta vs. AC Milan
  (7, 1, 1, 'Gianpaolo Calvarese', false),
  -- Unscheduled match 19: Torino vs. AS Roma
  (10, 4, 1, 'Martin Atkinson', false),
  -- Unscheduled match 20: Inter Milan vs. Fiorentina
  (2, 8, 1, 'Anthony Taylor', false),
  -- Unscheduled match 21: Lazio vs. Sampdoria
  (6, 9, 1, 'Michael Fabbri', false);

-- Inserting events for Serie A matches
INSERT INTO public.events (match_id, player_id, type, time) VALUES
  -- Match 1: AC Milan vs. Inter Milan
  (1, 4, 'goal', 15),  -- Zlatan Ibrahimović (AC Milan) scores
  (1, 1, 'goal', 30),  -- Gianluigi Donnarumma (AC Milan) scores
  (1, 8, 'goal', 70),  -- Romelu Lukaku (Inter Milan) scores
  -- Match 2: Juventus vs. AS Roma
  (2, 11, 'goal', 25),  -- Giorgio Chiellini (Juventus) scores
  (2, 12, 'goal', 60),  -- Paulo Dybala (Juventus) scores
  (2, 16, 'goal', 75),  -- Tammy Abraham (AS Roma) scores
  -- Match 3: Napoli vs. Lazio
  (3, 17, 'goal', 20),  -- Lorenzo Insigne (Napoli) scores
  (3, 18, 'goal', 30),  -- Kalidou Koulibaly (Napoli) scores
  (3, 22, 'goal', 65),  -- Ciro Immobile (Lazio) scores
  (3, 21, 'goal', 70),  -- Victor Osimhen (Napoli) scores
  -- Match 4: Atalanta vs. Fiorentina
  (4, 24, 'goal', 10),  -- Josip Ilicic (Atalanta) scores
  (4, 23, 'goal', 45),  -- Remo Freuler (Atalanta) scores
  (4, 28, 'goal', 70),  -- Dusan Vlahovic (Fiorentina) scores
  (4, 27, 'goal', 80),  -- Emil Audero (Atalanta) scores
  -- Match 5: Sampdoria vs. Torino
  (5, 29, 'goal', 5),   -- Fabio Quagliarella (Sampdoria) scores
  (5, 30, 'goal', 30),  -- Maya Yoshida (Sampdoria) scores
  (5, 34, 'goal', 60),  -- Salvatore Sirigu (Torino) scores
  (5, 31, 'goal', 70),  -- Andrea Belotti (Torino) scores
  -- Match 6: Inter Milan vs. Juventus
  (6, 7, 'goal', 20),   -- Wojciech Szczęsny (Juventus) scores
  (6, 13, 'goal', 55),  -- Nicolo Barella (Inter Milan) scores
  (6, 14, 'goal', 75),  -- Federico Chiesa (Juventus) scores
  (6, 10, 'goal', 85),  -- Dejan Kulusevski (Inter Milan) scores
  -- Match 7: AS Roma vs. Napoli
  (7, 16, 'goal', 10),  -- Stephan El Shaarawy (AS Roma) scores
  (7, 17, 'goal', 35),  -- Henrikh Mkhitaryan (Napoli) scores
  (7, 20, 'goal', 50),  -- Jordan Veretout (Napoli) scores
  (7, 19, 'goal', 65),  -- Giovanni Di Lorenzo (Napoli) scores
  -- Match 8: Lazio vs. Atalanta
  (8, 24, 'goal', 15),  -- Manuel Lazzari (Lazio) scores
  (8, 23, 'goal', 40),  -- Luis Alberto (Lazio) scores
  (8, 26, 'goal', 60),  -- Duván Zapata (Atalanta) scores
  (8, 27, 'goal', 75),  -- Josip Ilicic (Atalanta) scores
  -- Match 9: Fiorentina vs. Sampdoria
  (9, 33, 'goal', 20),  -- Christian Kouamé (Fiorentina) scores
  (9, 30, 'goal', 25),  -- Maya Yoshida (Sampdoria) scores
  (9, 37, 'goal', 55),  -- Giacomo Bonaventura (Fiorentina) scores
  (9, 29, 'goal', 80),  -- Fabio Quagliarella (Sampdoria) scores
  -- Match 10: Torino vs. AC Milan
  (10, 31, 'goal', 30),  -- Andrea Belotti (Torino) scores
  (10, 33, 'goal', 50),  -- Bremer Rodrigues (Torino) scores
  (10, 35, 'goal', 65),  -- Hakan Çalhanoğlu (AC Milan) scores
  (10, 36, 'goal', 75);  -- Rafael Leão (AC Milan) scores

-- Inserting Premier League championship
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, creation_date, is_finished)
VALUES
  ('Premier League 2024/2025', 'premierleague2425', 1, 20, 11, 7, 11, 5, '2024-08-01', NOW(), false);

-- Inserting teams for Premier League, each inserted by a different user
INSERT INTO public.teams (name, creator_user_id, tournament_id) VALUES
  ('Manchester City', 1, 2),
  ('Manchester United', 2, 2),
  ('Liverpool', 3, 2),
  ('Chelsea', 4, 2),
  ('Arsenal', 5, 2),
  ('Tottenham Hotspur', 6, 2),
  ('Leicester City', 7, 2),
  ('West Ham United', 8, 2),
  ('Everton', 9, 2),
  ('Wolverhampton Wanderers', 10, 2);

-- Inserting players for each Premier League team
-- You can add more players as needed
INSERT INTO public.players (name, surname, team_id, position, date_of_birth) VALUES
  -- Manchester City players
  ('Ederson', 'Moraes', 11, 'goalkeeper', '1993-08-17'),
  ('Ruben', 'Dias', 11, 'defender', '1997-05-14'),
  ('Kevin', 'De Bruyne', 11, 'midfielder', '1991-06-28'),
  ('Sergio', 'Agüero', 11, 'striker', '1988-06-02'),
  -- Manchester United players
  ('David', 'De Gea', 12, 'goalkeeper', '1990-11-07'),
  ('Harry', 'Maguire', 12, 'defender', '1993-03-05'),
  ('Bruno', 'Fernandes', 12, 'midfielder', '1994-09-08'),
  ('Marcus', 'Rashford', 12, 'striker', '1997-10-31'),
  -- Liverpool players
  ('Alisson', 'Becker', 13, 'goalkeeper', '1992-10-02'),
  ('Virgil', 'van Dijk', 13, 'defender', '1991-07-08'),
  ('Jordan', 'Henderson', 13, 'midfielder', '1990-06-17'),
  ('Mohamed', 'Salah', 13, 'striker', '1992-06-15'),
  -- Chelsea players
  ('Édouard', 'Mendy', 14, 'goalkeeper', '1992-03-01'),
  ('Thiago', 'Silva', 14, 'defender', '1984-09-22'),
  ('NGolo', 'Kanté', 14, 'midfielder', '1991-03-29'),
  ('Timo', 'Werner', 14, 'striker', '1996-03-06'),
  -- Arsenal players
  ('Bernd', 'Leno', 15, 'goalkeeper', '1992-03-04'),
  ('Gabriel', 'Magalhães', 15, 'defender', '1997-12-19'),
  ('Thomas', 'Partey', 15, 'midfielder', '1993-06-13'),
  ('Pierre-Emerick', 'Aubameyang', 15, 'striker', '1989-06-18'),
  -- Tottenham Hotspur players
  ('Hugo', 'Lloris', 16, 'goalkeeper', '1986-12-26'),
  ('Toby', 'Alderweireld', 16, 'defender', '1989-03-02'),
  ('Harry', 'Winks', 16, 'midfielder', '1996-02-02'),
  ('Harry', 'Kane', 16, 'striker', '1993-07-28'),
  -- Leicester City players
  ('Kasper', 'Schmeichel', 17, 'goalkeeper', '1986-11-05'),
  ('Wesley', 'Fofana', 17, 'defender', '2000-12-17'),
  ('James', 'Maddison', 17, 'midfielder', '1996-11-23'),
  ('Jamie', 'Vardy', 17, 'striker', '1987-01-11'),
  -- West Ham United players
  ('Łukasz', 'Fabiański', 18, 'goalkeeper', '1985-04-18'),
  ('Angelo', 'Ogbonna', 18, 'defender', '1988-05-23'),
  ('Declan', 'Rice', 18, 'midfielder', '1999-01-14'),
  ('Michail', 'Antonio', 18, 'striker', '1990-03-28'),
  -- Everton players
  ('Jordan', 'Pickford', 19, 'goalkeeper', '1994-03-07'),
  ('Lucas', 'Digne', 19, 'defender', '1993-07-20'),
  ('Allan', 'Marques Loureiro', 19, 'midfielder', '1991-01-08'),
  ('Richarlison', 'de Andrade', 19, 'striker', '1997-05-10'),
  -- Wolverhampton Wanderers players
  ('Rui', 'Patrício', 20, 'goalkeeper', '1988-02-15'),
  ('Conor', 'Coady', 20, 'defender', '1993-02-25'),
  ('João', 'Moutinho', 20, 'midfielder', '1986-09-08'),
  ('Raúl', 'Jiménez', 20, 'striker', '1991-05-05');

-- Inserting matches for Premier League (will add more as needed)
INSERT INTO public.matches (team1_id, team2_id, tournament_id, team1_score, team2_score, result, referee, match_date, is_finished) VALUES
  -- Match 1: Manchester City vs. Manchester United
  (11, 12, 2, 3, 2, 'team1', 'Michael Oliver', '2024-08-15 18:00:00', true),
  -- Match 2: Liverpool vs. Chelsea
  (13, 14, 2, 2, 1, 'team1', 'Martin Atkinson', '2024-08-16 15:30:00', true),
  -- Match 3: Arsenal vs. Tottenham Hotspur
  (15, 16, 2, 1, 1, 'draw', 'Anthony Taylor', '2024-08-17 16:45:00', true),
  -- Match 4: Leicester City vs. West Ham United
  (17, 18, 2, 3, 1, 'team1', 'Andre Marriner', '2024-08-18 14:00:00', true),
  -- Match 5: Everton vs. Wolverhampton Wanderers
  (19, 20, 2, 2, 2, 'draw', 'Chris Kavanagh', '2024-08-19 17:30:00', true);

-- Inserting scheduled but not yet played matches with existing referees
INSERT INTO public.matches (team1_id, team2_id, tournament_id, match_date, referee, is_finished) VALUES
  -- Scheduled match 11: AC Milan vs. Juventus
  (11, 13, 2, '2024-09-01 17:00:00', 'Michael Fabbri', false),
  -- Scheduled match 12: AS Roma vs. Inter Milan
  (14, 12, 2, '2024-09-02 19:45:00', 'Daniele Doveri', false),
  -- Scheduled match 13: Lazio vs. Napoli
  (16, 15, 2, '2024-09-03 15:30:00', 'Gianpaolo Calvarese', false),
  -- Scheduled match 14: Fiorentina vs. Torino
  (18, 20, 2, '2024-09-04 20:00:00', 'Martin Atkinson', false),
  -- Scheduled match 15: Sampdoria vs. Atalanta
  (19, 17, 2, '2024-09-05 18:15:00', 'Anthony Taylor', false),
  -- Scheduled match 16: Inter Milan vs. Lazio
  (12, 16, 2, '2024-09-06 16:30:00', 'Michael Fabbri', false);

-- Inserting matches that aren't scheduled yet with existing referees
INSERT INTO public.matches (team1_id, team2_id, tournament_id, referee, is_finished) VALUES
  -- Unscheduled match 17: Juventus vs. Napoli
  (13, 15, 2, 'Daniele Doveri', false),
  -- Unscheduled match 18: Atalanta vs. AC Milan
  (17, 11, 2, 'Gianpaolo Calvarese', false),
  -- Unscheduled match 19: Torino vs. AS Roma
  (11, 14, 2, 'Martin Atkinson', false),
  -- Unscheduled match 20: Inter Milan vs. Fiorentina
  (12, 18, 2, 'Anthony Taylor', false),
  -- Unscheduled match 21: Lazio vs. Sampdoria
  (16, 19, 2, 'Michael Fabbri', false);

-- Inserting events for Premier League matches (will add more as needed)
INSERT INTO public.events (match_id, player_id, type, time) VALUES
  -- Match 1: Manchester City vs. Manchester United
  (22, 43, 'goal', 10),  -- Kevin De Bruyne (Manchester City) scores
  (22, 44, 'goal', 30),  -- Sergio Agüero (Manchester City) scores
  (22, 42, 'goal', 45),  -- Ruben Dias (Manchester City) scores
  (22, 46, 'goal', 55),  -- Harry Maguire (Manchester United) scores
  (22, 47, 'goal', 70),  -- Bruno Fernandes (Manchester United) scores
  -- Match 2: Liverpool vs. Chelsea
  (23, 50, 'goal', 25),  -- Virgil van Dijk (Liverpool) scores
  (23, 52, 'goal', 60),  -- Mohamed Salah (Liverpool) scores
  (23, 55, 'goal', 75),  -- N'Golo Kanté (Chelsea) scores
  -- Match 3: Arsenal vs. Tottenham Hotspur
  (24, 60, 'goal', 20),  -- Pierre-Emerick Aubameyang (Arsenal) scores
  (24, 64, 'goal', 65),  -- Harry Kane (Tottenham Hotspur) scores
  -- Match 4: Leicester City vs. West Ham United
  (25, 66, 'goal', 15),  -- Wesley Fofana (Leicester City) scores
  (25, 67, 'goal', 30),  -- James Maddison (Leicester City) scores
  (25, 72, 'goal', 40),  -- Michail Antonio (West Ham United) scores
  (25, 68, 'goal', 70),  -- Jamie Vardy (Leicester City) scores
  -- Match 5: Everton vs. Wolverhampton Wanderers
  (26, 73, 'goal', 5),   -- Jordan Pickford (Everton) scores
  (26, 74, 'goal', 30),  -- Lucas Digne (Everton) scores
  (26, 79, 'goal', 60),  -- João Moutinho (Wolverhampton Wanderers) scores
  (26, 80, 'goal', 70);  -- Raúl Jiménez (Wolverhampton Wanderers) scores

-- Inserting a new tournament without matches and with a deadline within 24 hours
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, creation_date, is_finished)
VALUES ('Empty Tournament', 'empty', 3, 20, 11, 7, 11, 5, NOW() - INTERVAL '1 hour', NOW(), false);

-- Inserting fake teams for Premier League tournament
INSERT INTO public.teams (name, creator_user_id, tournament_id)
VALUES
  ('Red Dragons FC', 1, 3),
  ('Blue Lions FC', 2, 3),
  ('Golden Tigers FC', 3, 3),
  ('Silver Eagles FC', 4, 3),
  ('Green Gators FC', 5, 3),
  ('Black Panthers FC', 6, 3),
  ('White Wolves FC', 7, 3),
  ('Orange Owls FC', 8, 3),
  ('Purple Pumas FC', 9, 3),
  ('Yellow Yaks FC', 10, 3);

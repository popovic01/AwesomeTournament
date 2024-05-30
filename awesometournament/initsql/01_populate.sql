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
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, start_date, creation_date, is_finished)
VALUES
('Serie A 2024/2025', 'seriea2425', 1, 20, 11, 7, 11, 5, '2024-08-01', '2024-09-01', NOW(), false);

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

-- Inserting Serie A players
INSERT INTO public.players (name, surname, team_id, position, date_of_birth) VALUES
-- AC Milan players
('Mike', 'Maignan', 1, 'goalkeeper', '1995-07-03'), --1
('Fikayo', 'Tomori', 1, 'defender', '1997-12-19'),
('Theo', 'Hernandez', 1, 'defender', '1997-10-06'),
('Matteo', 'Gabbia', 1, 'defender', '1999-10-21'),
('Davide', 'Calabria', 1, 'defender', '1996-12-06'),
('Rafael', 'Leão', 1, 'striker', '1999-06-10'),
('Ismaël', 'Bennacer', 1, 'midfielder', '1997-12-01'),
('Ante', 'Rebić', 1, 'striker', '1993-09-21'),

-- Inter Milan players
('Yann', 'Sommer', 2, 'goalkeeper', '1988-12-17'), --9
('Francesco', 'Acerbi', 2, 'defender', '1988-02-10'),
('Matteo', 'Darmian', 2, 'defender', '1989-12-02'),
('Denzel', 'Dumfries', 2, 'defender', '1996-04-18'),
('Hakan', 'Çalhanoğlu', 2, 'midfielder', '1994-02-08'),
('Lautaro', 'Martínez', 2, 'striker', '1997-08-22'),
('Nicolò', 'Barella', 2, 'midfielder', '1997-02-07'),
('Marcus', 'Thuram', 2, 'striker', '1997-08-06'),

-- Juventus players
('Mattia', 'Perin', 3, 'goalkeeper', '1992-11-10'), --17
('Matthijs', 'de Ligt', 3, 'defender', '1999-08-12'),
('Danilo', 'Luiz da Silva', 3, 'defender', '1991-07-15'),
('Manuel', 'Locatelli', 3, 'midfielder', '1998-01-08'),
('Adrien', 'Rabiot', 3, 'midfielder', '1995-04-03'),
('Álvaro', 'Morata', 3, 'striker', '1992-10-23'),
('Dusan', 'Vlahovic', 3, 'striker', '2000-01-28'),

-- AS Roma players
('Antonio', 'Mirante', 4, 'goalkeeper', '1983-07-08'), --24
('Gianluca', 'Mancini', 4, 'defender', '1996-04-17'),
('Marash', 'Kumbulla', 4, 'defender', '2000-02-08'),
('Lorenzo', 'Pellegrini', 4, 'midfielder', '1996-06-19'),
('Leandro', 'Paredes', 4, 'midfielder', '1994-06-29'),
('Romelu', 'Lukaku', 4, 'striker', '1993-05-13'),
('Paulo', 'Dybala', 4, 'striker', '1993-11-15'),

-- Napoli players
('David', 'Ospina', 5, 'goalkeeper', '1988-08-31'), --31
('Giovanni', 'Di Lorenzo', 5, 'defender', '1993-08-04'),
('Piotr', 'Zieliński', 5, 'midfielder', '1994-05-20'),
('Dries', 'Mertens', 5, 'striker', '1987-05-06'),
('Amir', 'Rrahmani', 5, 'defender', '1994-02-24'),
('Diego', 'Demme', 5, 'midfielder', '1991-11-21'),
('Matteo', 'Politano', 5, 'striker', '1993-08-03'),

-- Lazio players
('Pepe', 'Reina', 6, 'goalkeeper', '1982-08-31'), --38
('Luiz', 'Felipe', 6, 'defender', '1997-03-22'),
('Luis', 'Alberto', 6, 'midfielder', '1992-09-28'),
('Felipe', 'Anderson', 6, 'striker', '1993-04-15'),
('Patric', 'Gabarrón', 6, 'defender', '1993-04-17'),
('Daichi', 'Kamada', 6, 'midfielder', '1996-08-05'),
('Mattia', 'Zaccagni', 6, 'striker', '1995-06-16'),

-- Atalanta players
('Marco', 'Sportiello', 7, 'goalkeeper', '1992-05-10'), --45
('Rafael', 'Tolói', 7, 'defender', '1990-10-10'),
('Marten', 'de Roon', 7, 'midfielder', '1991-03-29'),
('Gianluca', 'Scamacca', 7, 'striker', '1999-01-01'),
('Hans', 'Hateboer', 7, 'defender', '1994-01-09'),
('Mario', 'Pašalić', 7, 'midfielder', '1995-02-09'),
('Aleksei', 'Miranchuk', 7, 'striker', '1995-10-17'),

-- Fiorentina players
('Pietro', 'Terracciano', 8, 'goalkeeper', '1990-03-08'), --52
('Cristiano', 'Biraghi', 8, 'defender', '1992-09-01'),
('Lucas', 'Martínez Quarta', 8, 'defender', '1996-05-10'),
('Giacomo', 'Bonaventura', 8, 'midfielder', '1989-08-22'),
('Rolando', 'Mandragora', 8, 'midfielder', '1997-06-29'),
('José', 'Callejón', 8, 'striker', '1987-02-11'),
('Christian', 'Kouamé', 8, 'striker', '1997-12-06'),

-- Sampdoria players
('Wladimiro', 'Falcone', 9, 'goalkeeper', '1995-04-12'), --59
('Omar', 'Colley', 9, 'defender', '1992-10-24'),
('Tommaso', 'Augello', 9, 'defender', '1994-08-30'),
('Valerio', 'Verre', 9, 'midfielder', '1994-01-11'),
('Jakub', 'Jankto', 9, 'midfielder', '1996-01-19'),
('Antonio', 'Candreva', 9, 'striker', '1987-02-28'),
('Keita', 'Baldé', 9, 'striker', '1995-03-08'),

-- Torino players
('Vanja', 'Milinković-Savić', 10, 'goalkeeper', '1997-02-20'), --66
('Armando', 'Izzo', 10, 'defender', '1992-03-02'),
('Koffi', 'Djiji', 10, 'defender', '1996-01-15'),
('Karol', 'Linetty', 10, 'midfielder', '1995-02-02'),
('Duvan', 'Zapata', 10, 'striker', '1997-06-29'),
('Simone', 'Zaza', 10, 'striker', '1991-06-25'),
('Simone', 'Verdi', 10, 'striker', '1992-07-12'); --71


-- Inserting matches for Serie A based on provided events
INSERT INTO public.matches (team1_id, team2_id, tournament_id, team1_score, team2_score, result, referee, match_date, is_finished) VALUES
-- Match 1: AC Milan vs. Inter Milan
(1, 2, 1, 1, 2, 'team2', 'Michael Fabbri', '2023-08-15 18:00:00', true),
-- Match 2: Juventus vs. AS Roma
(3, 4, 1, 1, 0, 'team1', 'Daniele Doveri', '2023-08-16 15:30:00', true),
-- Match 3: Napoli vs. Lazio
(5, 6, 1, 1, 2, 'team2', 'Gianpaolo Calvarese', '2023-08-17 16:45:00', true),
-- Match 4: Atalanta vs. Fiorentina
(7, 8, 1, 0, 1, 'team2', 'Martin Atkinson', '2023-08-18 14:00:00', true),
-- Match 5: Sampdoria vs. Torino
(9, 10, 1, 2, 2, 'draw', 'Anthony Taylor', '2023-08-19 17:30:00', true),
-- Match 6: Inter Milan vs. Juventus
(2, 3, 1, 1, 0, 'team1', 'Michael Fabbri', '2023-08-21 16:00:00', true),
-- Match 7: AS Roma vs. Napoli
(4, 5, 1, 2, 0, 'team1', 'Daniele Doveri', '2023-08-22 14:30:00', true),
-- Match 8: Lazio vs. Atalanta
(6, 7, 1, 3, 2, 'team1', 'Gianpaolo Calvarese', '2023-08-23 15:15:00', true),
-- Match 9: Fiorentina vs. Sampdoria
(8, 9, 1, 1, 3, 'team2', 'Martin Atkinson', '2023-08-24 17:45:00', true),
-- Match 10: Torino vs. AC Milan
(10, 1, 1, 3, 1, 'team1', 'Anthony Taylor', '2023-08-25 18:30:00', true);

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
-- Match 1: AC Milan vs. Inter Milan 1-2
(1, 10, 'goal', 18),  -- Goal Acerbi
(1, 15, 'yellow card', 22),  -- Yellow Barella
(1, 14, 'yellow card', 32),  -- Yellow Lautaro
(1, 3, 'yellow card', 36),  -- Yellow Theo
(1, 16, 'goal', 49),  -- Goal Thuram
(1, 2, 'goal', 80),  -- Goal Tomori
(1, 4, 'yellow card', 90),  -- Yellow Gabbia
(1, 2, 'yellow card', 92),  -- Yellow Tomori
(1, 12, 'red card', 93),  -- Red Dumfries
(1, 3, 'red card', 93),  -- Red Theo
(1, 5, 'red card', 97),  -- Red Calabria

-- Match 2: Juventus vs. AS Roma 1-0
(2, 21, 'goal', 47),  -- Goal Rabiot
(2, 28, 'yellow card', 68),  -- Yellow Paredes
(2, 20, 'yellow card', 75),  -- Yellow Locatelli

-- Match 3: Napoli vs. Lazio 1-2
(3, 44, 'yellow card', 26),  -- Yellow Zaccagni
(3, 40, 'goal', 30),  -- Goal Luis Alberto
(3, 33, 'goal', 32),  -- Goal Zielinski
(3, 43, 'goal', 52),  -- Goal Kamada
(3, 40, 'yellow card', 60),  -- Yellow Luis Alberto

-- Match 4: Atalanta vs. Fiorentina 0-1
(4, 51, 'yellow card', 29),  -- Yellow Miranchuk
(4, 56, 'goal', 31),  -- Gol Mandragora
(4, 56, 'yellow card', 55),  -- Yellow Mandragora
(4, 47, 'yellow card', 78),  -- Yellow De Roon
(4, 58, 'yellow card', 81),  -- Yellow Kouamé
(4, 48, 'yellow card', 89),  -- Yellow Scamacca

-- Match 5: Sampdoria vs. Torino 2-2
(5, 62, 'goal', 5),   -- Gol Verre
(5, 62, 'goal', 30),  -- Gol Verre
(5, 69, 'goal', 60),  -- Gol Zapata
(5, 62, 'yellow card', 62),  -- Yellow Verre
(5, 60, 'goal', 70),  -- Gol Zapata
(5, 61, 'red card', 30),  -- Red Augello

-- Match 6: Inter Milan vs. Juventus 1-0
(6, 23, 'yellow card', 18),   -- Yellow Vlahovic
(6, 19, 'yellow card', 32),  -- Yellow Danilo
(6, 14, 'goal', 37),   -- Gol Lautaro
(6, 15, 'yellow card', 51),  -- Yellow Barella
(6, 16, 'yellow card', 77),  -- Yellow Thuram
(6, 18, 'yellow card', 87),  -- Yellow De Ligt

-- Match 7: AS Roma vs. Napoli  2-0
(7, 28, 'yellow card', 10),  -- Yellow Paredes
(7, 37, 'yellow card', 35),  -- Yellow Politano
(7, 27, 'goal', 76),  -- Gol Pellegrini
(7, 29, 'goal', 96),  -- Gol Lukaku

-- Match 8: Lazio vs. Atalanta 3-2
(8, 40, 'goal', 5),  -- Gol Luis Alberto
(8, 41, 'goal', 11),  -- Gol Felipe
(8, 42, 'goal', 33),  -- Gol Patric
(8, 49, 'goal', 63),  -- Gol Hataboer
(8, 43, 'yellow card', 79),  -- Yellow Kamada
(8, 50, 'goal', 83),  -- Goal Pasalic
(8, 47, 'yellow card', 83),  -- Yellow De Roon

-- Match 9: Fiorentina vs. Sampdoria 1-3
(9, 55, 'goal', 20),  -- Gol Jack
(9, 61, 'goal', 25),  -- Gol Augello
(9, 61, 'goal', 55),  -- Gol Augello
(9, 62, 'goal', 80),  -- Gol Verre
(9, 55, 'yellow card', 83),  -- Yellow Jack
(9, 63, 'yellow card', 83),  -- Yellow Jankto

-- Match 10: Torino vs. AC Milan 3-1
(10, 70, 'goal', 30),  -- Gol Zaza
(10, 70, 'goal', 50),  -- Gol Zaza
(10, 70, 'goal', 65),  -- Gol Zaza
(10, 7, 'goal', 75),  -- Gol Bennacer
(10, 2, 'yellow card', 81),  -- Yellow Tomori
(10, 66, 'yellow card', 89);  -- Yellow Milinkovic

-- Inserting Premier League championship
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, start_date, creation_date, is_finished)
VALUES
('Premier League 2024/2025', 'premierleague2425', 1, 20, 11, 7, 11, 5, '2024-08-01', '2024-09-01', NOW(), false);

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

-- Inserting Premier League players
INSERT INTO public.players (name, surname, team_id, position, date_of_birth) VALUES
-- Manchester City players
('Ederson', 'Moraes', 11, 'goalkeeper', '1993-08-17'), --72
('Ruben', 'Dias', 11, 'defender', '1997-05-14'),
('Kyle', 'Walker', 11, 'defender', '1990-05-28'),
('John', 'Stones', 11, 'defender', '1994-05-28'),
('Rodrigo', 'Hernández', 11, 'midfielder', '1996-06-22'),
('Phil', 'Foden', 11, 'midfielder', '2000-05-28'),
('Kevin', 'De Bruyne', 11, 'midfielder', '1991-06-28'),
('Erling', 'Haaland', 11, 'striker', '2000-07-21'),
('Julian', 'Alvarez', 11, 'striker', '2000-01-31'),
('Jack', 'Grealish', 11, 'striker', '1995-09-10'),

-- Manchester United players
('David', 'De Gea', 12, 'goalkeeper', '1990-11-07'), --82
('Harry', 'Maguire', 12, 'defender', '1993-03-05'),
('Aaron', 'Wan-Bissaka', 12, 'defender', '1997-11-26'),
('Luke', 'Shaw', 12, 'defender', '1995-07-12'),
('Scott', 'McTominay', 12, 'midfielder', '1996-12-08'),
('Kobbie', 'Mainoo', 12, 'midfielder', '2005-04-19'),
('Bruno', 'Fernandes', 12, 'midfielder', '1994-09-08'),
('Marcus', 'Rashford', 12, 'striker', '1997-10-31'),
('Anthony', 'Martial', 12, 'striker', '1995-12-05'),
('Mason', 'Greenwood', 12, 'striker', '2001-10-01'),

-- Liverpool players
('Alisson', 'Becker', 13, 'goalkeeper', '1992-10-02'), --92
('Virgil', 'van Dijk', 13, 'defender', '1991-07-08'),
('Trent', 'Alexander-Arnold', 13, 'defender', '1998-10-07'),
('Andrew', 'Robertson', 13, 'defender', '1994-03-11'),
('Thiago', 'Alcântara', 13, 'midfielder', '1991-04-11'),
('Fabinho', 'Tavares', 13, 'midfielder', '1993-10-23'),
('Jordan', 'Henderson', 13, 'midfielder', '1990-06-17'),
('Mohamed', 'Salah', 13, 'striker', '1992-06-15'),
('Sadio', 'Mané', 13, 'striker', '1992-04-10'),
('Roberto', 'Firmino', 13, 'striker', '1991-10-02'),

-- Chelsea players
('Édouard', 'Mendy', 14, 'goalkeeper', '1992-03-01'), --102
('Thiago', 'Silva', 14, 'defender', '1984-09-22'),
('César', 'Azpilicueta', 14, 'defender', '1989-08-28'),
('Reece', 'James', 14, 'defender', '1999-12-08'),
('Hakim', 'Ziyech', 14, 'midfielder', '1993-03-19'),
('Mason', 'Mount', 14, 'midfielder', '1999-01-10'),
('Kai', 'Havertz', 14, 'midfielder', '1999-06-11'),
('NGolo', 'Kanté', 14, 'midfielder', '1991-03-29'),
('Timo', 'Werner', 14, 'striker', '1996-03-06'),
('Christian', 'Pulisic', 14, 'striker', '1998-09-18'),

-- Arsenal players
('Bernd', 'Leno', 15, 'goalkeeper', '1992-03-04'), --112
('Gabriel', 'Magalhães', 15, 'defender', '1997-12-19'),
('Ben', 'White', 15, 'defender', '1997-10-08'),
('Kieran', 'Tierney', 15, 'defender', '1997-06-05'),
('Bukayo', 'Saka', 15, 'midfielder', '2001-09-05'),
('Granit', 'Xhaka', 15, 'midfielder', '1992-09-27'),
('Thomas', 'Partey', 15, 'midfielder', '1993-06-13'),
('Pierre-Emerick', 'Aubameyang', 15, 'striker', '1989-06-18'),
('Alexandre', 'Lacazette', 15, 'striker', '1991-05-28'),
('Nicolas', 'Pépé', 15, 'striker', '1995-05-29'),

-- Tottenham Hotspur players
('Hugo', 'Lloris', 16, 'goalkeeper', '1986-12-26'), --122
('Toby', 'Alderweireld', 16, 'defender', '1989-03-02'),
('Davinson', 'Sánchez', 16, 'defender', '1996-06-12'),
('Eric', 'Dier', 16, 'defender', '1994-01-15'),
('Sergio', 'Reguilón', 16, 'defender', '1996-12-16'),
('Pierre-Emile', 'Højbjerg', 16, 'midfielder', '1995-08-05'),
('Harry', 'Winks', 16, 'midfielder', '1996-02-02'),
('Harry', 'Kane', 16, 'striker', '1993-07-28'),
('Heung-min', 'Son', 16, 'striker', '1992-07-08'),
('Steven', 'Bergwijn', 16, 'striker', '1997-10-08'),

-- Leicester City players
('Kasper', 'Schmeichel', 17, 'goalkeeper', '1986-11-05'), --132
('Wesley', 'Fofana', 17, 'defender', '2000-12-17'),
('Jonny', 'Evans', 17, 'defender', '1988-01-03'),
('Ricardo', 'Pereira', 17, 'defender', '1993-10-06'),
('Youri', 'Tielemans', 17, 'midfielder', '1997-05-07'),
('Harvey', 'Barnes', 17, 'midfielder', '1997-12-09'),
('Wilfred', 'Ndidi', 17, 'midfielder', '1996-12-16'),
('James', 'Maddison', 17, 'midfielder', '1996-11-23'),
('Jamie', 'Vardy', 17, 'striker', '1987-01-11'),
('Ayoze', 'Pérez', 17, 'striker', '1993-07-29'),

-- West Ham United players
('Łukasz', 'Fabiański', 18, 'goalkeeper', '1985-04-18'), --142
('Angelo', 'Ogbonna', 18, 'defender', '1988-05-23'),
('Craig', 'Dawson', 18, 'defender', '1990-05-06'),
('Vladimír', 'Coufal', 18, 'defender', '1992-08-22'),
('Tomáš', 'Souček', 18, 'midfielder', '1995-02-27'),
('Pablo', 'Fornals', 18, 'midfielder', '1996-02-22'),
('Declan', 'Rice', 18, 'midfielder', '1999-01-14'),
('Michail', 'Antonio', 18, 'striker', '1990-03-28'),
('Jarrod', 'Bowen', 18, 'striker', '1996-12-20'),
('Saïd', 'Benrahma', 18, 'striker', '1995-08-10'),

-- Everton players
('Jordan', 'Pickford', 19, 'goalkeeper', '1994-03-07'), --152
('Lucas', 'Digne', 19, 'defender', '1993-07-20'),
('Michael', 'Keane', 19, 'defender', '1993-01-11'),
('Yerry', 'Mina', 19, 'defender', '1994-09-23'),
('James', 'Rodríguez', 19, 'midfielder', '1991-07-12'),
('Abdoulaye', 'Doucouré', 19, 'midfielder', '1993-01-01'),
('André', 'Gomes', 19, 'midfielder', '1993-07-30'),
('Allan', 'Marques Loureiro', 19, 'midfielder', '1991-01-08'),
('Richarlison', 'de Andrade', 19, 'striker', '1997-05-10'),
('Dominic', 'Calvert-Lewin', 19, 'striker', '1997-03-16'),

-- Wolverhampton Wanderers players
('Rui', 'Patrício', 20, 'goalkeeper', '1988-02-15'), --162
('Conor', 'Coady', 20, 'defender', '1993-02-25'),
('Willy', 'Boly', 20, 'defender', '1991-02-03'),
('Nélson', 'Semedo', 20, 'defender', '1993-11-16'),
('Leander', 'Dendoncker', 20, 'midfielder', '1995-04-15'),
('João', 'Moutinho', 20, 'midfielder', '1986-09-08'),
('Rúben', 'Neves', 20, 'midfielder', '1997-03-13'),
('Raúl', 'Jiménez', 20, 'striker', '1991-05-05'),
('Adama', 'Traoré', 20, 'striker', '1996-01-25'),
('Pedro', 'Neto', 20, 'striker', '2000-03-09');

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
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, start_date, creation_date, is_finished)
VALUES ('Empty Tournament', 'empty', 3, 20, 11, 7, 11, 5, NOW() - INTERVAL '1 hour', '2024-09-01T00:00:00Z', NOW(), false);

-- Inserting fake teams for empty tournament
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

-- Bundesliga 2018-2019
INSERT INTO public.tournaments (name, token, creator_user_id, max_teams, max_players, min_players, starting_players, max_substitutions, deadline, start_date, creation_date, is_finished)
VALUES ('Bundesliga 2018-2019', 'bundesliga1819', 1, 18, 11, 11, 11, 3, '2024-05-18 00:00:00', '2018-08-01 00:00:00', '2018-07-01 00:00:00', TRUE);

-- Inserting teams
INSERT INTO public.teams (name, creator_user_id, tournament_id)
VALUES
    ('Bayern Monaco', 1, 4),
    ('Borussia Dortmund', 2, 4),
    ('RB Lipsia', 3, 4),
    ('Borussia Mönchengladbach', 4, 4),
    ('Bayer Leverkusen', 5, 4),
    ('Schalke 04', 6, 4),
    ('Eintracht Francoforte', 7, 4),
    ('Werder Brema', 8, 4),
    ('Hoffenheim', 9, 4),
    ('Fortuna Düsseldorf', 10, 4);

-- Inserting matches for Bundesliga 2018-2019
INSERT INTO public.matches (team1_id, team2_id, tournament_id, team1_score, team2_score, referee, match_date, is_finished)
VALUES
    (31, 32, 4, 5, 0, 'Referee 1', '2018-08-24 20:00:00', TRUE),
    (33, 34, 4, 2, 2, 'Referee 2', '2018-09-01 15:30:00', TRUE),
    (35, 36, 4, 1, 0, 'Referee 3', '2018-09-15 18:00:00', TRUE),
    (37, 38, 4, 2, 2, 'Referee 4', '2018-09-29 15:30:00', TRUE),
    (39, 40, 4, 2, 1, 'Referee 5', '2018-10-06 15:30:00', TRUE),
    (32, 33, 4, 1, 1, 'Referee 6', '2018-10-20 15:30:00', TRUE),
    (34, 35, 4, 0, 1, 'Referee 7', '2018-11-03 15:30:00', TRUE),
    (36, 37, 4, 1, 2, 'Referee 8', '2018-11-24 15:30:00', TRUE),
    (38, 39, 4, 2, 0, 'Referee 9', '2018-12-08 15:30:00', TRUE),
    (40, 31, 4, 0, 3, 'Referee 10', '2018-12-15 15:30:00', TRUE);

-- Inserting players for Bundesliga 2018-2019
-- Bayern Munich
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Manuel', 'Neuer', 31, 'goalkeeper', '1986-03-27'),
    ('Mats', 'Hummels', 31, 'defender', '1988-12-16'),
    ('David', 'Alaba', 31, 'defender', '1992-06-24'),
    ('Niklas', 'Süle', 31, 'defender', '1995-09-03'),
    ('Joshua', 'Kimmich', 31, 'midfielder', '1995-02-08'),
    ('Thiago', 'Alcántara', 31, 'midfielder', '1991-04-11'),
    ('Javi', 'Martinez', 31, 'midfielder', '1988-09-02'),
    ('Thomas', 'Müller', 31, 'forward', '1989-09-13'),
    ('Kingsley', 'Coman', 31, 'forward', '1996-06-13'),
    ('Serge', 'Gnabry', 31, 'forward', '1995-07-14'),
    ('Robert', 'Lewandowski', 31, 'striker', '1988-08-21');

-- Borussia Dortmund
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Roman', 'Bürki', 32, 'goalkeeper', '1990-11-14'),
    ('Marco', 'Reus', 32, 'midfielder', '1989-05-31'),
    ('Jadon', 'Sancho', 32, 'striker', '2000-03-25'),
    ('Axel', 'Witsel', 32, 'midfielder', '1989-01-12'),
    ('Mario', 'Götze', 32, 'midfielder', '1992-06-03'),
    ('Paco', 'Alcácer', 32, 'striker', '1993-08-30'),
    ('Julian', 'Weigl', 32, 'midfielder', '1995-09-08'),
    ('Raphaël', 'Guerreiro', 32, 'defender', '1993-12-22'),
    ('Dan-Axel', 'Zagadou', 32, 'defender', '1999-06-03'),
    ('Thomas', 'Delaney', 32, 'midfielder', '1991-09-03');

-- RB Leipzig
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Peter', 'Gulácsi', 33, 'goalkeeper', '1990-05-06'),
    ('Timo', 'Werner', 33, 'striker', '1996-03-06'),
    ('Marcel', 'Sabitzer', 33, 'midfielder', '1994-03-17'),
    ('Yussuf', 'Poulsen', 33, 'striker', '1994-06-15'),
    ('Emil', 'Forsberg', 33, 'midfielder', '1991-10-23'),
    ('Dayot', 'Upamecano', 33, 'defender', '1998-10-27'),
    ('Kevin', 'Kampl', 33, 'midfielder', '1990-10-09'),
    ('Willi', 'Orban', 33, 'defender', '1992-11-03');

-- Borussia Mönchengladbach
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Yann', 'Sommer', 34, 'goalkeeper', '1988-12-17'),
    ('Alassane', 'Pléa', 34, 'striker', '1993-03-10'),
    ('Denis', 'Zakaria', 34, 'midfielder', '1996-11-20'),
    ('Thorgan', 'Hazard', 34, 'midfielder', '1993-03-29'),
    ('Lars', 'Stindl', 34, 'forward', '1988-08-26'),
    ('Matthias', 'Ginter', 34, 'defender', '1994-01-19'),
    ('Nico', 'Elvedi', 34, 'defender', '1996-09-30'),
    ('Oscar', 'Wendt', 34, 'defender', '1985-10-24');

-- Bayer Leverkusen
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Lukáš', 'Hrádecký', 35, 'goalkeeper', '1989-11-24'),
    ('Kevin', 'Volland', 35, 'striker', '1992-07-30'),
    ('Kai', 'Havertz', 35, 'midfielder', '1999-06-11'),
    ('Julian', 'Brandt', 35, 'midfielder', '1996-05-02'),
    ('Charles', 'Aránguiz', 35, 'midfielder', '1989-04-17'),
    ('Leon', 'Bailey', 35, 'forward', '1997-08-09'),
    ('Jonathan', 'Tah', 35, 'defender', '1996-02-11'),
    ('Wendell', 'Nascimiento', 35, 'defender', '1993-07-20');

-- Schalke 04
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Ralf', 'Fährmann', 36, 'goalkeeper', '1988-09-27'),
    ('Guido', 'Burgstaller', 36, 'striker', '1989-04-29'),
    ('Suat', 'Serdar', 36, 'midfielder', '1997-04-11'),
    ('Nabil', 'Bentaleb', 36, 'midfielder', '1994-11-24'),
    ('Amine', 'Harit', 36, 'midfielder', '1997-06-18'),
    ('Weston', 'McKennie', 36, 'midfielder', '1998-08-28'),
    ('Sebastian', 'Rudy', 36, 'midfielder', '1990-02-28'),
    ('Matija', 'Nastasić', 36, 'defender', '1993-03-28');

-- Eintracht Frankfurt
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Kevin', 'Trapp', 37, 'goalkeeper', '1990-07-08'),
    ('Filip', 'Kostić', 37, 'midfielder', '1992-11-01'),
    ('André', 'Silva', 37, 'striker', '1995-11-06'),
    ('Sebastien', 'Haller', 37, 'striker', '1994-06-22'),
    ('Luka', 'Jovic', 37, 'striker', '1997-12-23'),
    ('Ante', 'Rebic', 37, 'forward', '1993-09-21'),
    ('Gelson', 'Fernandes', 37, 'midfielder', '1986-09-02'),
    ('Danny', 'da Costa', 37, 'defender', '1993-07-13');

-- Werder Bremen
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Jiří', 'Pavlenka', 38, 'goalkeeper', '1992-04-14'),
    ('Josh', 'Sargent', 38, 'striker', '2000-02-20'),
    ('Milot', 'Rashica', 38, 'midfielder', '1996-06-28'),
    ('Max', 'Kruse', 38, 'striker', '1988-03-19'),
    ('Davy', 'Klaassen', 38, 'midfielder', '1993-02-21'),
    ('Maximilian', 'Eggestein', 38, 'midfielder', '1996-12-08'),
    ('Niklas', 'Moisander', 38, 'defender', '1985-09-29'),
    ('Theodor', 'Gebre Selassie', 38, 'defender', '1986-12-24');

-- Hoffenheim
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Oliver', 'Baumann', 39, 'goalkeeper', '1990-06-02'),
    ('Andrej', 'Kramarić', 39, 'striker', '1991-06-19'),
    ('Florian', 'Grillitsch', 39, 'midfielder', '1995-08-07'),
    ('Kerem', 'Demirbay', 39, 'midfielder', '1993-07-03'),
    ('Joelinton', 'Cassio', 39, 'striker', '1996-08-14'),
    ('Nadiem', 'Amiri', 39, 'midfielder', '1996-10-27'),
    ('Kevin', 'Vogt', 39, 'defender', '1991-09-23'),
    ('Ermin', 'Bičakčić', 39, 'defender', '1990-01-24');

-- Fortuna Düsseldorf
INSERT INTO public.players (name, surname, team_id, position, date_of_birth)
VALUES
    ('Michael', 'Rensing', 40, 'goalkeeper', '1984-05-14'),
    ('Rouwen', 'Hennings', 40, 'striker', '1987-08-28'),
    ('Alfredo', 'Morales', 40, 'midfielder', '1990-05-12'),
    ('Dodi', 'Lukebakio', 40, 'forward', '1997-09-24'),
    ('Kaan', 'Ayhan', 40, 'defender', '1994-11-10'),
    ('Niko', 'Gießelmann', 40, 'defender', '1991-09-26'),
    ('Oliver', 'Fink', 40, 'midfielder', '1982-06-06'),
    ('Matthias', 'Zimmermann', 40, 'midfielder', '1992-06-16');

-- Inserting events
-- Match 1
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (38, , 'goal', 10), -- Goal Hummels
    (38, , 'goal', 17), -- Gol Lewa
    (38, , 'yellow card', 40), -- giallo zagadou
    (38, , 'goal', 41), -- gol Martinez
    (38, , 'goal', 43), -- gol Gnabry
    (38, , 'yellow card', 70), -- giallo Lewa
    (38, , 'yellow card', 70), -- giallo delaney
    (38, , 'goal', 89); -- gol Lewa

-- Match 2
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (39, 87, 'goal', 15),  -- Gol scored by Timo Werner
    (39, 88, 'red card', 70);  -- Red card for Marcel Sabitzer

-- Match 3
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (40, 89, 'goal', 37);  -- Gol scored by Alassane Pléa

-- Match 4
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (41, 92, 'yellow card', 20),  -- Yellow card for Denis Zakaria
    (41, 93, 'yellow card', 45);  -- Yellow card for Kevin Volland

-- Match 5
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (42, 95, 'goal', 50);  -- Gol scored by André Silva

-- Match 6
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (43, 96, 'goal', 80),  -- Gol scored by Josh Sargent
    (43, 97, 'yellow card', 35);  -- Yellow card for Milot Rashica

-- Match 7
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (44, 99, 'goal', 60),  -- Gol scored by Guido Burgstaller
    (44, 100, 'goal', 75);  -- Gol scored by Suat Serdar

-- Match 8
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (45, 102, 'goal', 10),  -- Gol scored by Filip Kostić
    (45, 103, 'yellow card', 30);  -- Yellow card for André Silva

-- Match 9
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (46, 105, 'goal', 45);  -- Gol scored by Andrej Kramarić

-- Match 10
INSERT INTO public.events (match_id, player_id, type, time)
VALUES
    (47, 107, 'goal', 5),  -- Gol scored by Rouwen Hennings
    (47, 108, 'yellow card', 40);  -- Yellow card for Alfredo Morales


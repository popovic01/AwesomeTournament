\c awesome;

-- Inserting users
INSERT INTO public.users (email, password) VALUES
  ('user1@example.com', 'password1'),
  ('user2@example.com', 'password2'),
  ('user3@example.com', 'password3'),
  ('user4@example.com', 'password4'),
  ('user5@example.com', 'password5'),
  ('user6@example.com', 'password6'),
  ('user7@example.com', 'password7'),
  ('user8@example.com', 'password8'),
  ('user9@example.com', 'password9'),
  ('user10@example.com', 'password10');

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
  (1, 2, 1, 2, 1, 'team1', 'Michael Fabbri', '2024-08-15 18:00:00', true),
  -- Match 2: Juventus vs. AS Roma
  (3, 4, 1, 2, 1, 'team1', 'Daniele Doveri', '2024-08-16 15:30:00', true),
  -- Match 3: Napoli vs. Lazio
  (5, 6, 1, 3, 1, 'team1', 'Gianpaolo Calvarese', '2024-08-17 16:45:00', true),
  -- Match 4: Atalanta vs. Fiorentina
  (7, 8, 1, 3, 1, 'team1', 'Martin Atkinson', '2024-08-18 14:00:00', true),
  -- Match 5: Sampdoria vs. Torino
  (9, 10, 1, 2, 2, 'draw', 'Anthony Taylor', '2024-08-19 17:30:00', true),
  -- Match 6: Inter Milan vs. Juventus
  (2, 3, 1, 2, 2, 'draw', 'Michael Fabbri', '2024-08-21 16:00:00', true),
  -- Match 7: AS Roma vs. Napoli
  (4, 5, 1, 2, 2, 'draw', 'Daniele Doveri', '2024-08-22 14:30:00', true),
  -- Match 8: Lazio vs. Atalanta
  (6, 7, 1, 2, 2, 'draw', 'Gianpaolo Calvarese', '2024-08-23 15:15:00', true),
  -- Match 9: Fiorentina vs. Sampdoria
  (8, 9, 1, 1, 3, 'team2', 'Martin Atkinson', '2024-08-24 17:45:00', true),
  -- Match 10: Torino vs. AC Milan
  (10, 1, 1, 2, 1, 'team1', 'Anthony Taylor', '2024-08-25 18:30:00', true);

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

\c awesome;

INSERT INTO public.users(email, password)
VALUES ('seriea_admin', 'password'),
('milan_admin', 'password'),
('inter_admin', 'password');

INSERT INTO public.tournaments(name, token,
creator_user_id, max_teams, max_players,
min_players, starting_players, max_substitutions,
is_finished, creation_date, deadline)
VALUES ('seriea', 'a1b2c3d4', 1, 20, 25, 15, 11,
5, false, '2024-04-17 18:00:00+03', '2025-04-17 18:00:00+03');

INSERT INTO public.teams(name, creator_user_id,
tournament_id)
VALUES ('milan', 2, 1), ('inter', 3, 1);

INSERT INTO public.matches(team1_id, team2_id,
tournament_id, team1_score, team2_score, result, referee, match_date, is_finished)
VALUES (1, 2, 1, 1, 3, 'team1', 'pierluigi collina', '2024-04-17 18:00:00+03', true),
(2, 1, 1, NULL, NULL, NULL, 'andrea stocco', '2024-04-20 18:00:00+03', false);

INSERT INTO public.players(name, surname,
team_id, date_of_birth)
VALUES ('fikayo', 'tomori', 1, '1997-12-19'),
('lautaro', 'martinez', 2, '1997-08-22');

INSERT INTO public.events(match_id, player_id,
type, time)
VALUES (1, 1, 'yellow card', 60),
(1, 2, 'goal', 25);
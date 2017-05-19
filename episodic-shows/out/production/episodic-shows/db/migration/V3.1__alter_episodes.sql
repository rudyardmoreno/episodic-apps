ALTER TABLE episodes
ADD UNIQUE INDEX episodes_idx01 (show_id ASC, season_number ASC, episode_number ASC);

create table viewings (
  id bigint not null auto_increment primary key,
  user_id bigint not null,
  show_id bigint not null,
  episode_id bigint not null,
  updated_at datetime,
  timecode int
);

ALTER TABLE viewings
ADD UNIQUE INDEX viewings_idx01 (user_id ASC, show_id ASC, episode_id ASC);

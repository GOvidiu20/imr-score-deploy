create user breakout;
alter user breakout with encrypted password 'breakout';
create database breakoutVR;
grant all privileges on database breakoutVR to breakout;

drop sequence if exists breakoutVR.player_scores_sequence;
drop table if exists breakout_player_scores;

create sequence if not exists breakoutVR.player_scores_sequence start 1 increment by 1;

create table if not exists breakoutVR.breakout_player_scores
(
    id       serial primary key,
    username varchar(300) unique not null,
    score    bigint              not null
);

delete
from breakoutVR.breakout_player_scores;

select *
from breakoutVR.breakout_player_scores;
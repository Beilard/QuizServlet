create table if not exists question
(
    question_id    int auto_increment
        primary key,
    body           varchar(300) not null,
    correct_answer varchar(100) not null,
    hint           varchar(200) not null,
    constraint question_body_uindex
        unique (body),
    constraint question_correct_answer_uindex
        unique (correct_answer)
);

create table if not exists role
(
    role_id   int auto_increment
        primary key,
    role_name varchar(32) not null,
    constraint role_role_name_uindex
        unique (role_name)
);

create table if not exists status
(
    status_id   int auto_increment
        primary key,
    status_name varchar(32) not null,
    constraint status_status_name_uindex
        unique (status_name)
);

create table if not exists team
(
    team_id   int auto_increment
        primary key,
    team_name varchar(32) not null
);

create table if not exists game
(
    game_id             int auto_increment
        primary key,
    number_of_questions int           not null,
    time_per_question   int           not null,
    current_phase       int           null,
    status_id           int default 1 not null,
    team_id             int           not null,
    constraint game_status_status_id_fk
        foreign key (status_id) references status (status_id),
    constraint game_team_team_id_fk
        foreign key (team_id) references team (team_id)
);

create table if not exists phase
(
    phase_id     int auto_increment
        primary key,
    question_id  int                  not null,
    start_time   timestamp            null,
    end_time     timestamp            null,
    deadline     timestamp            null,
    hint_used    tinyint(1) default 0 null,
    is_correct   tinyint(1)           null,
    given_answer varchar(300)         null,
    game_id      int                  not null,
    constraint phase_game_game_id_fk
        foreign key (game_id) references game (game_id),
    constraint phase_question_question_id_fk
        foreign key (question_id) references question (question_id)
);

create table if not exists user
(
    user_id    int auto_increment
        primary key,
    email      varchar(64)          not null,
    password   varchar(64)          not null,
    name       varchar(32)          not null,
    surname    varchar(32)          not null,
    is_captain tinyint(1) default 0 not null,
    team_id    int                  null,
    role_id    int        default 1 not null,
    constraint user_email_uindex
        unique (email),
    constraint user_role_role_id_fk
        foreign key (role_id) references role (role_id),
    constraint user_team_team_id_fk
        foreign key (team_id) references team (team_id)
);



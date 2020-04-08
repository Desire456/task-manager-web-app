create table IF NOT EXISTS users
(
    user_id            serial PRIMARY KEY,
    login              VARCHAR(20) UNIQUE,
    password           VARCHAR(30),
    role               VARCHAR(20),
    dateOfRegistration timestamptz
);
create table IF NOT EXISTS journals
(
    journal_id    serial PRIMARY KEY,
    user_id       INTEGER references users (user_id),
    name          VARCHAR(20),
    description   VARCHAR(50),
    creation_date timestamptz,
    isPrivate     boolean,
    UNIQUE (name, user_id)
);
CREATE TABLE IF NOT EXISTS tasks
(
    task_id      serial PRIMARY KEY,
    journal_id   INTEGER REFERENCES journals (journal_id) ON DELETE CASCADE,
    name         VARCHAR(20),
    description  VARCHAR(50),
    status       VARCHAR(20),
    planned_date timestamptz,
    date_of_done timestamptz,
    UNIQUE (name, journal_id)
);
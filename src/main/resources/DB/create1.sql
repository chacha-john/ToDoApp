CREATE DATABASE todo;

\c todo;
CREATE TABLE tasks (
id SERIAL PRIMARY KEY,
userid INT,
name VARCHAR,
description VARCHAR,
createdon timestamp,
completed BOOLEAN
);
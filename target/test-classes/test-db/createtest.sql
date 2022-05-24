CREATE DATABASE todo_test;

\c todo_test;
CREATE TABLE tasks (
id SERIAL PRIMARY KEY,
userid INT,
name VARCHAR,
description VARCHAR,
createdon BIGINT,
completed BOOLEAN
);
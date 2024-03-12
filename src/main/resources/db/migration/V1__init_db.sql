--Завдання №1

--worker - таблиця для працівників
CREATE TABLE IF NOT EXISTS worker (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(1000) NOT NULL CHECK(LENGTH(NAME) >= 2),
    BIRTHDAY DATE CHECK(YEAR(BIRTHDAY) > 1900),
    LEVEL VARCHAR(10) NOT NULL CHECK(LEVEL IN ('Trainee', 'Junior', 'Middle', 'Senior')),
    SALARY INT CHECK(SALARY BETWEEN 100 AND 100000)
);

--client - таблиця для клієнтів.
CREATE TABLE IF NOT EXISTS client (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(1000) NOT NULL CHECK(LENGTH(NAME) >= 2)
);

--project - таблиця для проєктів.
CREATE TABLE IF NOT EXISTS project (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CLIENT_ID INT,
    START_DATE DATE,
    FINISH_DATE DATE,
    FOREIGN KEY (CLIENT_ID) REFERENCES client(ID)
);

--project_worker - таблиця, що показує, які працівники над якими проєктами працюютью
CREATE TABLE IF NOT EXISTS project_worker (
    PROJECT_ID INT,
    WORKER_ID INT,
    PRIMARY KEY (PROJECT_ID, WORKER_ID),
    FOREIGN KEY (PROJECT_ID) REFERENCES project(ID),
    FOREIGN KEY (WORKER_ID) REFERENCES worker(ID)
);

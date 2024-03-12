--додано щонайменше 10 працівників, мінімальна зарплата має бути менше за 1000, максимальна - більше за 5 000,
-- мають бути всі технічні рівні (Trainee, Junior, Middle, Senior)

INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY)
VALUES
    ('John Doe', '1990-05-15', 'Trainee', 800),
    ('Jane Smith', '1985-08-20', 'Trainee', 850),
    ('Michael Johnson', '1988-03-10', 'Junior', 1200),
    ('Emily Brown', '1992-11-25', 'Junior', 1300),
    ('David Williams', '1983-07-07', 'Middle', 3000),
    ('Sarah Miller', '1995-02-18', 'Middle', 3200),
    ('Daniel Wilson', '1980-09-12', 'Senior', 6000),
    ('Olivia Taylor', '1977-06-30', 'Senior', 6500),
    ('Robert Anderson', '1986-04-05', 'Senior', 7000),
    ('Emma Martinez', '1998-10-22', 'Senior', 7200);


--додано щонайменше 5 клієнтів
INSERT INTO client (NAME)
VALUES
    ('Company A'),
    ('Company B'),
    ('Company C'),
    ('Company D'),
    ('Company E');



--додано щонайменше 10 проєктів. Тривалість кожного проєкту (різниця START_DATE та FINISH_DATE) має бути від 1 до 100 місяців.
INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE)
VALUES
    (1, DATEADD('MONTH', -10, CURRENT_DATE()), CURRENT_DATE()), -- Проєкт тривав 10 місяців
    (2, DATEADD('MONTH', -5, CURRENT_DATE()), CURRENT_DATE()),  -- Проєкт тривав 5 місяців
    (3, DATEADD('MONTH', -20, CURRENT_DATE()), CURRENT_DATE()), -- Проєкт тривав 20 місяців
    (4, DATEADD('MONTH', -8, CURRENT_DATE()), CURRENT_DATE()),  -- Проєкт тривав 8 місяців
    (5, DATEADD('MONTH', -15, CURRENT_DATE()), CURRENT_DATE()), -- Проєкт тривав 15 місяців
    (1, DATEADD('MONTH', -7, CURRENT_DATE()), CURRENT_DATE()),  -- Проєкт тривав 7 місяців
    (2, DATEADD('MONTH', -12, CURRENT_DATE()), CURRENT_DATE()), -- Проєкт тривав 12 місяців
    (3, DATEADD('MONTH', -3, CURRENT_DATE()), CURRENT_DATE()),  -- Проєкт тривав 3 місяці
    (4, DATEADD('MONTH', -18, CURRENT_DATE()), CURRENT_DATE()), -- Проєкт тривав 18 місяців
    (5, DATEADD('MONTH', -6, CURRENT_DATE()), CURRENT_DATE());  -- Проєкт тривав 6 місяців


--назначені працівники на кожен проєкт. Над кожним проєктом має працювати від 1 до 5 працівників.
INSERT INTO project_worker (PROJECT_ID, WORKER_ID)
SELECT
    rnd_project_workers.project_id AS PROJECT_ID,
    rnd_worker.rnd_worker_id AS WORKER_ID
FROM
    (SELECT project.ID AS project_id, ROUND(RAND() * 4) + 1 AS num_workers FROM project) AS rnd_project_workers
JOIN
    (SELECT ID AS rnd_worker_id FROM worker ORDER BY RAND() LIMIT 5) AS rnd_worker ON 1 = 1
ORDER BY
    rnd_project_workers.project_id;
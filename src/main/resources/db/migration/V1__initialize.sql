DROP TABLE IF EXISTS workstations;
CREATE TABLE workstations (id bigserial, owner varchar(255), computer varchar(255), serial_number varchar(255), location varchar(255), start_date varchar(255), file_path varchar(255));

INSERT INTO workstations (owner, computer, serial_number, location, start_date) VALUES
('Илюшина Татьяна Вячеславовна', 'Ноутбук HP ProBook 450 G5', '5CD732BKMW', 'Москва', '2018-10-12'),
('Чурзин Сергей Владимирович', 'Моноблок HP EliteOne 800 G2', 'CZC6267V4B', 'Москва', '2018-10-15'),
('Викторов Виктор Александрович', 'Ноутбук HP ProBook 450 G2', 'CND53939D3', 'Москва', '2018-10-15'),
('Ануфриев Никита Сергеевич', 'Ноутбук HP ProBook 450 G2', 'CND53939H7', 'Москва', '2018-10-15'),
('Гончарова Алина Андреевна', 'Моноблок HP EliteOne 800 G3', 'CZC74687MJ', 'Москва', '2018-10-19');
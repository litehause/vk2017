CREATE DATABASE vk2017;
CREATE USER pg_vk2017 WITH password 'dtlmvf';
GRANT ALL privileges ON DATABASE vk2017 TO pg_vk2017;

-- psql -h localhost vk2017 pg_vk2017

create sequence nextval_slick;

create TABLE users (
  id bigint primary key default nextval('nextval_slick'),
  vkId VARCHAR not NULL UNIQUE,
  name VARCHAR,
  gender VARCHAR(255),
  isGuide BOOLEAN NOT NULL DEFAULT false
);

--INSERT INTO users(vkId, name, gender) VALUES
-- SELECT id, vkId, name, gender, isGuide from users where id = ?


create TABLE location (
  user_id BIGINT NOT NULL PRIMARY KEY,
  lon FLOAT NOT NULL,
  lat FLOAT NOT NULL,
  route VARCHAR NOT NULL,
  last_Modification timestamp NOT NULL DEFAULT now()
);

--select user_id, lon, lat, route, last_Modification from location;


CREATE FUNCTION expire_table_delete_old_rows() RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
  DELETE FROM location WHERE location.last_Modification < NOW() - INTERVAL '10 minute';
  RETURN NEW;
END;
$$;

CREATE TRIGGER expire_table_delete_old_rows_trigger_in_insert
AFTER INSERT ON location
EXECUTE PROCEDURE expire_table_delete_old_rows();

CREATE TRIGGER expire_table_delete_old_rows_trigger_in_update
AFTER UPDATE ON location
EXECUTE PROCEDURE expire_table_delete_old_rows();

ALTER TABLE location drop COLUMN lon;
ALTER TABLE location drop COLUMN lat;
ALTER TABLE location DROP COLUMN route;

ALTER TABLE location ADD COLUMN coordinate text;

-------------------------
CREATE table files (
  id bigint primary key default nextval('nextval_slick'),
  name VARCHAR(255) not NULL,
  dtype VARCHAR(255) NOT NULL
);

-- экспонат
CREATE TABLE exhibit (
  id bigint primary key default nextval('nextval_slick'),
  name VARCHAR(255) NOT NULL,
  file BIGINT NOT NULL REFERENCES files(id),
  thumbmail BIGINT REFERENCES files(id),
  title VARCHAR(255),
  information text
);

--INSERT INTO exhibit(name, file, thumbmail, title, information) VALUES
-----------------------------------------------------------------------------------
-- выставки
CREATE TABLE exhibition (
  id bigint primary key default nextval('nextval_slick'),
  title VARCHAR(255) not NULL,
  information VARCHAR(255) NOT NULL,
  image BIGINT NOT NULL REFERENCES files(id)
);

--INSERT INTO exhibition(title, information, image) VALUES ()


--- связь экспонат --- выставка

CREATE TABLE exhibit_exhibition_link (
  exhibition_id BIGINT NOT NULL REFERENCES exhibition(id),
  exhibit_id BIGINT NOT NULL REFERENCES exhibit(id),
  index int not NULL DEFAULT 0
);

--INSERT INTO exhibit_exhibition_link(exhibition_id, exhibit_id, index) VALUES ()

---Экскурсия

CREATE TABLE excursion (
  id bigint primary key default nextval('nextval_slick'),
  title VARCHAR(255) NOT NULL,
  information text NOT NULL,
  image BIGINT NOT NULL REFERENCES files(id),
  file BIGINT NOT NULL REFERENCES files(id)
);

ALTER TABLE excursion ADD COLUMN exhibition_id BIGINT NOT NULL REFERENCES exhibition(id);
--INSERT INTO excursion(title, information, image, file) VALUES ()

CREATE TABLE exhibit_excursion_link (
  exhibit_id BIGINT NOT NULL REFERENCES exhibit(id),
  excursion_id BIGINT NOT NULL REFERENCES excursion(id),
  index INT NOT NULL DEFAULT 0
);


-- в админке тока загрузка файлов остальные вещи буду делать в ручную запросами
---Надо продумать систему экскурсий (аудио или видео трансляции с возможностью от клиентов задавать вопросы0)
---Надо както расказать информацию о том что за картина как она делалась или еще интересные файкты

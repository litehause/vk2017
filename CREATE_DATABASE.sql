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
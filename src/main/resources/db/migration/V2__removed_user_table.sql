ALTER TABLE trackers
    DROP CONSTRAINT fk_trackers_on_created_by;

DROP TABLE users CASCADE;

ALTER TABLE trackers
    DROP COLUMN created_by;
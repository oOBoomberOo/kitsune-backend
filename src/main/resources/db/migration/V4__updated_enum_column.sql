ALTER TABLE videos
    DROP COLUMN status;

ALTER TABLE videos
    DROP COLUMN type;

ALTER TABLE videos
    ADD status VARCHAR(255) NOT NULL default 'SCHEDULED';

ALTER TABLE videos
    ADD type VARCHAR(255) NOT NULL default 'UPLOAD';
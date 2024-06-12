CREATE TABLE records
(
    id         UUID NOT NULL,
    views      BIGINT,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    video_id   VARCHAR(255),
    CONSTRAINT pk_records PRIMARY KEY (id)
);

CREATE TABLE trackers
(
    id         UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    start_at   TIMESTAMP WITHOUT TIME ZONE,
    end_at     TIMESTAMP WITHOUT TIME ZONE,
    video_id   VARCHAR(255),
    created_by VARCHAR(255),
    CONSTRAINT pk_trackers PRIMARY KEY (id)
);

CREATE TABLE users
(
    id            VARCHAR(255) NOT NULL,
    access_token  VARCHAR(255),
    refresh_token VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE videos
(
    id       VARCHAR(255) NOT NULL,
    title    VARCHAR(255),
    added_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_videos PRIMARY KEY (id)
);

ALTER TABLE records
    ADD CONSTRAINT FK_RECORDS_ON_VIDEO FOREIGN KEY (video_id) REFERENCES videos (id);

ALTER TABLE trackers
    ADD CONSTRAINT FK_TRACKERS_ON_CREATED_BY FOREIGN KEY (created_by) REFERENCES users (id);

ALTER TABLE trackers
    ADD CONSTRAINT FK_TRACKERS_ON_VIDEO FOREIGN KEY (video_id) REFERENCES videos (id);
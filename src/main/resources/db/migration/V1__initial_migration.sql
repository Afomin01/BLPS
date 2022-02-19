CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE question
(
    id               UUID NOT NULL,
    title            VARCHAR(255),
    text             TEXT,
    creation_timeutc TIMESTAMP WITHOUT TIME ZONE,
    rating           INTEGER,
    needs_moderation BOOLEAN,
    user_id          UUID NOT NULL,
    CONSTRAINT pk_question PRIMARY KEY (id)
);

CREATE TABLE question_tag
(
    question_id UUID NOT NULL,
    tag_id      UUID NOT NULL,
    CONSTRAINT pk_question_tag PRIMARY KEY (question_id, tag_id)
);

CREATE TABLE tag
(
    id   UUID NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_tag PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id       UUID NOT NULL,
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    rating   BIGINT,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE question
    ADD CONSTRAINT FK_QUESTION_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE question_tag
    ADD CONSTRAINT fk_quetag_on_question FOREIGN KEY (question_id) REFERENCES question (id);

ALTER TABLE question_tag
    ADD CONSTRAINT fk_quetag_on_tag FOREIGN KEY (tag_id) REFERENCES tag (id);
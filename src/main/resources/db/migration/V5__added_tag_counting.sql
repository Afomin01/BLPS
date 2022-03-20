CREATE TABLE tag_counter
(
    tag_id  uuid REFERENCES tag (id),
    counter BIGINT CHECK ( counter > 0 ),
    PRIMARY KEY (tag_id)
);
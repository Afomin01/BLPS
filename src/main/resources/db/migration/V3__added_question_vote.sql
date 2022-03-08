CREATE TABLE user_question_vote (
    id UUID NOT NULL,
    question_id UUID NOT NULL REFERENCES question(id),
    user_id UUID NOT NULL REFERENCES  users(id),
    is_upvote BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);
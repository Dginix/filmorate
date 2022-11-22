CREATE TABLE IF NOT EXISTS MPA
(
    id integer NOT NULL AUTO_INCREMENT,
    rating character varying(50) NOT NULL,
    CONSTRAINT "MPA_pkey" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS film
(
    id bigint NOT NULL AUTO_INCREMENT,
    name character varying(100) NOT NULL,
    description text,
    release_date timestamp without time zone NOT NULL,
    duration integer NOT NULL,
    MPA_id integer NOT NULL,
    CONSTRAINT film_pkey PRIMARY KEY (id),
    CONSTRAINT MPA_fkey
        FOREIGN KEY (MPA_id) REFERENCES MPA (id)
);

CREATE TABLE IF NOT EXISTS "user"
(
    id bigint NOT NULL AUTO_INCREMENT,
    email character varying(320) NOT NULL,
    login character varying(100) NOT NULL,
    name character varying(200) NOT NULL,
    birthday timestamp without time zone,
    CONSTRAINT user_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS genre
(
    id integer NOT NULL,
    genre_name character varying(100) NOT NULL,
    CONSTRAINT genre_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS film_genre
(
    id bigint NOT NULL AUTO_INCREMENT,
    film_id bigint NOT NULL,
    genre_id integer NOT NULL,
    CONSTRAINT film_genre_pkey PRIMARY KEY (id),
    CONSTRAINT genre_fkey
        FOREIGN KEY (genre_id) REFERENCES genre (id),
    CONSTRAINT film_id_genre_fkey
        FOREIGN KEY (film_id) REFERENCES film (id)
);

CREATE TABLE IF NOT EXISTS film_like
(
    id bigint NOT NULL AUTO_INCREMENT,
    film_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT film_like_pkey PRIMARY KEY (id),
    CONSTRAINT user_like_fkey
        FOREIGN KEY (user_id) REFERENCES "user" (id),
    CONSTRAINT film_like_fkey
        FOREIGN KEY (film_id) REFERENCES film (id)
);

CREATE TABLE IF NOT EXISTS friend_status
(
    id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    friend_id bigint NOT NULL,
    status boolean NOT NULL,
    CONSTRAINT friend_status_pkey PRIMARY KEY (id),
    CONSTRAINT friend_status_fkey
        FOREIGN KEY (friend_id) REFERENCES "user" (id),
    CONSTRAINT user_status_fkey
        FOREIGN KEY (user_id) REFERENCES "user" (id)
);

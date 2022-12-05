create table if not exists message
(
    conversationID int                                 not null
        primary key,
    message        varchar(150)                        not null,
    timestamp      timestamp default CURRENT_TIMESTAMP not null
);

create table if not exists profile
(
    userID   int auto_increment
        primary key,
    bio      varchar(250) null,
    username varchar(20)  not null,
    password varchar(30)  not null,
    email    varchar(50)  not null,
    constraint profile_email_uindex
        unique (email),
    constraint profile_username_uindex
        unique (username)
);

create table if not exists conversation
(
    conversationID int auto_increment
        primary key,
    toUserID       int not null,
    fromUserID     int not null,
    constraint conversation_profile_userID_fk
        foreign key (toUserID) references profile (userID)
            on delete cascade,
    constraint conversation_profile_userID_fk_2
        foreign key (fromUserID) references profile (userID)
            on delete cascade
);

create table if not exists interactions
(
    fromUserId int           not null,
    toUserId   int           not null,
    `like`     int default 0 not null comment '1 (like), 0 (neither), -1 (dislike)',
    primary key (fromUserId, toUserId),
    constraint interactions_profile_userID_fk
        foreign key (fromUserId) references profile (userID)
            on delete cascade,
    constraint interactions_profile_userID_fk_2
        foreign key (toUserId) references profile (userID)
            on delete cascade
);

create table if not exists posts
(
    userID      int         not null,
    title       varchar(30) not null,
    description varchar(50) not null,
    postID      int auto_increment
        primary key,
    constraint posts_profile_userID_fk
        foreign key (userID) references profile (userID)
            on delete cascade
);

create table if not exists rating
(
    fromUserID int      not null,
    toUserID   int      not null,
    rating     smallint not null,
    primary key (fromUserID, toUserID),
    constraint rating_profile_userID_fk
        foreign key (fromUserID) references profile (userID)
            on delete cascade,
    constraint rating_profile_userID_fk_2
        foreign key (toUserID) references profile (userID)
            on delete cascade
);

create table if not exists skilltype
(
    skillTypeID int auto_increment
        primary key,
    skillType   varchar(30) not null,
    constraint skillType_skillType_uindex
        unique (skillType)
);

create table if not exists skill
(
    userID      int not null,
    skillTypeID int not null,
    primary key (userID, skillTypeID),
    constraint skill_profile_userID_fk
        foreign key (userID) references profile (userID)
            on delete cascade,
    constraint skill_skilltype_skillTypeID_fk
        foreign key (skillTypeID) references skilltype (skillTypeID)
            on delete cascade
);



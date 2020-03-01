INSERT INTO users
VALUES ('ABCDEFGK', 'd.selal@hotmail.com', 'abcd1234'),
       ('FJGKTOFS', 'jim.1924@hotmail.com', 'abcd1234'),
       ('FLTOALVG', 'testEmail@hotmail.com', 'abcd1234');



INSERT INTO units (unit_id, image_url, title, region, description, cancellation_policy, price, score, colonist_id,
                   total_reviews)
VALUES ('FJVLTO81', '/photos/12345/main.png', 'Blue Moon', 'Arcadia',
        'A description of the unit Blue Moon', 'Cancellation Policy of Blue Moon', 1500, 3,
        'ABCDEFGK', 1),
       ('FLSOW424', '/photos/12345/main1.png', 'Red Moon', 'Chryse',
        'A description of the unit Red Moon', 'Cancellation Policy of Red Moon', 1600, 4.5,
        'ABCDEFGK', 5),
       ('FJG8567A', '/photos/12345/main2.png', 'Green Moon', 'Argyre',
        'A description of the unit Green Moon', 'Cancellation Policy of Green Moon', 1400, 4.0,
        'FJGKTOFS', 10),
       ('CKLG59FH', '/photos/12345/main3.png', 'Purple Moon', 'Amazonis',
        'A description of the unit Purple Moon', 'Cancellation Policy of Purple Moon', 1300, 4.8,
        'FJGKTOFS', 2),
       ('VKN45OA0', '/photos/12345/main4.png', 'Yellow Moon', 'Alba Mons',
        'A description of the unit Yellow Moon', 'Cancellation Policy of Yellow Moon', 1200, 3.1,
        'FLTOALVG', 5),
       ('AL3491QP', '/photos/12345/main5.png', 'White Moon', 'Tempe Terra',
        'A description of the unit White Moon', 'Cancellation Policy of White Moon', 1100, 3.6,
        'FLTOALVG', 2),
       ('CM40IYTR', '/photos/12345/main6.png', 'Orange Moon', 'Elysium Mons',
        'A description of the unit Orange Moon', 'Cancellation Policy of Orange Moon', 1700, 4.1,
        'FLTOALVG', 3);

INSERT INTO reviews
VALUES ('731efc78-5738-11ea-8e2d-0242ac130003', 'FJVLTO81', 5, 'This place is amazing'),
       ('731efee4-5738-11ea-8e2d-0242ac130003', 'FJVLTO81', 4, 'Pretty good'),
       ('731efffc-5738-11ea-8e2d-0242ac130003', 'FLSOW424', 3, null),
       ('731f00d8-5738-11ea-8e2d-0242ac130003', 'FLSOW424', 2, 'Super dirty'),
       ('731f01a0-5738-11ea-8e2d-0242ac130003', 'FJG8567A', 5, 'Living paradise'),
       ('731f0272-5738-11ea-8e2d-0242ac130003', 'FJG8567A', 5, null),
       ('731f034e-5738-11ea-8e2d-0242ac130003', 'CKLG59FH', 4, 'Acceptable'),
       ('731f0416-5738-11ea-8e2d-0242ac130003', 'CKLG59FH', 2, 'Very bad location'),
       ('731f04d4-5738-11ea-8e2d-0242ac130003', 'VKN45OA0', 4, 'Bang for the buck'),
       ('731f059c-5738-11ea-8e2d-0242ac130003', 'VKN45OA0', 1, null),
       ('731f066e-5738-11ea-8e2d-0242ac130003', 'AL3491QP', 3, null),
       ('731f072c-5738-11ea-8e2d-0242ac130003', 'AL3491QP', 5, 'Simply outstanding'),
       ('731f081c-5738-11ea-8e2d-0242ac130003', 'CM40IYTR', 3, 'Just ok'),
       ('731f08da-5738-11ea-8e2d-0242ac130003', 'CM40IYTR', 5, 'I love this place');

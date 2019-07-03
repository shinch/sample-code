-- 회원 데이타 등록
INSERT INTO member (id, password, token, token_expire) VALUES ('user-01', '1CsHRfQPVzSA46EDPaZhCRQC0f0SmeQRf58m1Flttrc=', '00000000000000000000000000000000000000000000', CURRENT_TIMESTAMP);
INSERT INTO member (id, password, token, token_expire) VALUES ('user-02', 'lVR8SmO8Zr7LB/D8/6XoX8cyX228gC+INLK7Z3fz484=', '00000000000000000000000000000000000000000000', CURRENT_TIMESTAMP);
INSERT INTO member (id, password, token, token_expire) VALUES ('user-03', '01qYFixVHnlkkq8vvIe0pYZc/TuwoSbberMbPc4TvFY=', '00000000000000000000000000000000000000000000', CURRENT_TIMESTAMP);
INSERT INTO member (id, password, token, token_expire) VALUES ('user-04', 'Jm/f9GLurSL+ouG1h7+4Y1gdvXtXCz8JUbulna+ZQWI=', '00000000000000000000000000000000000000000000', CURRENT_TIMESTAMP);
INSERT INTO member (id, password, token, token_expire) VALUES ('uesr-05', 'T6gT0NrBzWiZ9HtJXtTQEzsPeP6a/Z286i6ZQIAHDPE=', '00000000000000000000000000000000000000000000', CURRENT_TIMESTAMP);

-- 검색 History 등록
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('투썸플레이스', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('커피빈', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('스타벅스', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('이디야커피', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('할리스커피', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('엔제리너스', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('카페베네', CURRENT_TIMESTAMP, 'user-01');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('커피빈', CURRENT_TIMESTAMP, 'user-02');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('스타벅스', CURRENT_TIMESTAMP, 'user-02');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('이디야커피', CURRENT_TIMESTAMP, 'user-02');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('할리스커피', CURRENT_TIMESTAMP, 'user-02');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('엔제리너스', CURRENT_TIMESTAMP, 'user-02');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('카페베네', CURRENT_TIMESTAMP, 'user-02');
INSERT INTO search_history (keyword, create_at, create_by) VALUES ('투썸플레이스', CURRENT_TIMESTAMP, 'user-02');

-- 최대 검색 수 등
INSERT INTO top_keyword (keyword, cnt) VALUES ('투썸플레이스', 24);
INSERT INTO top_keyword (keyword, cnt) VALUES ('커피빈', 100);
INSERT INTO top_keyword (keyword, cnt) VALUES ('스타벅스', 32);
INSERT INTO top_keyword (keyword, cnt) VALUES ('이디야커피', 17);
INSERT INTO top_keyword (keyword, cnt) VALUES ('할리스커피', 82);
INSERT INTO top_keyword (keyword, cnt) VALUES ('엔제리너스', 37);
INSERT INTO top_keyword (keyword, cnt) VALUES ('카페베네', 50);

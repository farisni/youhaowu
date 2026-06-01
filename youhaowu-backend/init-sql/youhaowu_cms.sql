-- 轮播图
CREATE TABLE cms_banner (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(100),
    img_url     VARCHAR(500) NOT NULL,
    link_url    VARCHAR(500),
    sort        INT DEFAULT 0,
    status      SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT NOW(),
    update_time TIMESTAMP DEFAULT NOW()
);

INSERT INTO cms_banner (title, img_url, link_url, sort) VALUES
('轮播1', '', '', 1),
('轮播2', '', '', 2),
('轮播3', '', '', 3),
('轮播4', '', '', 4);

-- 首页快报
CREATE TABLE cms_news (
    id          BIGSERIAL PRIMARY KEY,
    tag         VARCHAR(20),
    title       VARCHAR(200) NOT NULL,
    link_url    VARCHAR(500),
    sort        INT DEFAULT 0,
    status      SMALLINT DEFAULT 1,
    create_time TIMESTAMP DEFAULT NOW(),
    update_time TIMESTAMP DEFAULT NOW()
);

INSERT INTO cms_news (tag, title, sort) VALUES
('[特惠]', '备战开学季 全民半价购数码', 1),
('[公告]', '有好物APP新版本上线', 2),
('[特惠]', '端午节 粽情回馈', 3),
('[公告]', '有好物618年中大促', 4),
('[特惠]', '会员日 全场8折', 5);

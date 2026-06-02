-- 今日推荐表
CREATE TABLE IF NOT EXISTS cms_recommend (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200),
    img_url VARCHAR(500),
    file_name VARCHAR(200),
    link_url VARCHAR(500),
    sort INT DEFAULT 0,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE cms_recommend IS '今日推荐';
COMMENT ON COLUMN cms_recommend.title IS '推荐标题';
COMMENT ON COLUMN cms_recommend.img_url IS '图片URL（相对路径）';
COMMENT ON COLUMN cms_recommend.file_name IS '原始文件名';
COMMENT ON COLUMN cms_recommend.link_url IS '跳转链接';
COMMENT ON COLUMN cms_recommend.sort IS '排序';
COMMENT ON COLUMN cms_recommend.status IS '状态 1=启用 0=禁用';

DROP DATABASE IF EXISTS BookManageSYS;
CREATE DATABASE BookManageSYS DEFAULT CHARACTER SET utf8;
USE BookManageSYS;

-- 1. 管理员表
CREATE TABLE Manager(
                        manager_user VARCHAR(255) PRIMARY KEY,
                        manager_password VARCHAR(255)
);
INSERT INTO Manager VALUES ('admin', '123');

-- 2. 用户表
CREATE TABLE Customer (
                          customer_user VARCHAR(255) PRIMARY KEY,
                          customer_password VARCHAR(255)
);
INSERT INTO Customer VALUES ('admin', '123');

-- 3. 图书表
DROP TABLE IF EXISTS Book;
CREATE TABLE Book (
                      number VARCHAR(255) PRIMARY KEY COMMENT '书籍编号',
                      classnumber VARCHAR(255) COMMENT '类别编号',
                      name VARCHAR(255) COMMENT '书名',
                      classname VARCHAR(255) NOT NULL COMMENT '分类: Nature 或 Science',
                      price VARCHAR(255) COMMENT '价格',
                      state VARCHAR(255) DEFAULT 'in' COMMENT '状态: in(在馆)/out(借出)',
                      total VARCHAR(255) DEFAULT '1' COMMENT '总数',

                      current VARCHAR(255) DEFAULT NULL COMMENT '当前借阅人用户名',
                      dateon VARCHAR(255) DEFAULT NULL COMMENT '借出日期',
                      dateoff VARCHAR(255) DEFAULT NULL COMMENT '应还日期'
);

-- 4. 插入初始化数据
INSERT INTO Book (number, classnumber, name, classname, price, state, total, current, dateon, dateoff) VALUES
('N01', '1', 'Nature and Science',  'Nature', '25', 'in', '1', NULL, NULL, NULL),
('N02', '1', 'My Nature Science ',  'Nature', '23', 'in', '1', NULL, NULL, NULL),
('S01', '2', 'Sample Science Book', 'Science', '30', 'in', '1', NULL, NULL, NULL);

-- 5. 模拟借阅操作
UPDATE Book
SET state = 'out',
    current = 'admin',
    dateon = '2023-10-01',
    dateoff = '2023-11-01'
WHERE number = 'S01';

SELECT 'Database initialized. Borrow records merged into Book table.' AS status;
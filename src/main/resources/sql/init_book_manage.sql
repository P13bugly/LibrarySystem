/*
 * 数据库初始化脚本 - 图书管理系统 (BookManageSYS)
 */

-- 1. 创建并使用数据库
DROP DATABASE IF EXISTS BookManageSYS;
CREATE DATABASE BookManageSYS DEFAULT CHARACTER SET utf8;
USE BookManageSYS;

-- ----------------------------
-- 2. 管理员表 (Manager)
-- ----------------------------
DROP TABLE IF EXISTS Manager;
CREATE TABLE Manager(
  manager_user VARCHAR(255) PRIMARY KEY COMMENT '管理员用户名',
  manager_password VARCHAR(255) COMMENT '管理员密码'
);

-- 插入默认管理员数据 [cite: 164]
INSERT INTO Manager VALUES ('chenbowen', '12345678');


-- ----------------------------
-- 3. 用户表 (Customer)
-- ----------------------------
DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (
  customer_user VARCHAR(255) PRIMARY KEY COMMENT '用户名',
  customer_password VARCHAR(255) COMMENT '用户密码'
);

-- 插入默认用户数据 [cite: 166]
INSERT INTO Customer VALUES ('chenbowen', '12345678');
-- 更新用户密码操作 [cite: 167]
UPDATE Customer SET customer_password = '123' WHERE customer_user = 'chenbowen';


-- ----------------------------
-- 4. 自然类书籍表 (NatureBook)
-- 注意：已包含后续 ALTER TABLE 添加的 current, dateon, dateoff 字段 [cite: 215, 216]
-- ----------------------------
DROP TABLE IF EXISTS Nature;
CREATE TABLE Nature (
  number VARCHAR(255) PRIMARY KEY COMMENT '书籍编号',
  classnumber VARCHAR(255) COMMENT '类别编号',
  name VARCHAR(255) COMMENT '书名',
  classname VARCHAR(255) COMMENT '类别名称',
  price VARCHAR(255) COMMENT '价格',
  state VARCHAR(255) COMMENT '状态: in/out',
  total VARCHAR(255) COMMENT '总数',
  current VARCHAR(255) DEFAULT NULL COMMENT '当前借阅人',
  dateon VARCHAR(255) DEFAULT NULL COMMENT '借出日期',
  dateoff VARCHAR(255) DEFAULT NULL COMMENT '应还日期'
);

-- 插入初始化书籍数据 [cite: 169-173]
INSERT INTO Nature (number, classnumber, name, classname, price, state, total) VALUES
('N01', '1', 'Nature and Science',  'Nature', '25', 'in', '1'),
('N02', '1', 'My Nature Science ',  'Nature', '23', 'in', '1'),
('N03', '1', 'Wonderful Nature  ',  'Nature', '21', 'in', '1'),
('N04', '1', 'Amazing Nature    ',  'Nature', '22', 'in', '1'),
('N05', '1', 'Good Science Nature', 'Nature', '25', 'in', '1'),
('N06', '1', 'People and Nature ',  'Nature', '26', 'in', '1'),
('N07', '1', 'Hello Nature      ',  'Nature', '14', 'in', '1'),
('N08', '1', 'Goodbye Nature    ',  'Nature', '26', 'in', '1'),
('N09', '1', 'Great Nature      ',  'Nature', '20', 'in', '1'),
('N10', '1', 'Buzz Nature       ',  'Nature', '32', 'in', '1');
-- 文件中提到的 N11 [cite: 212]
INSERT INTO Nature (number, classnumber, name, classname, price, state, total) VALUES
('N11', '1', 'New World Nature',    'Nature', '18', 'in', '1');


-- ----------------------------
-- 5. 科学类书籍表 (ScienceBook)
-- ----------------------------
DROP TABLE IF EXISTS Science;
CREATE TABLE Science (
  number VARCHAR(255) PRIMARY KEY,
  classnumber VARCHAR(255),
  name VARCHAR(255),
  classname VARCHAR(255),
  price VARCHAR(255),
  state VARCHAR(255),
  total VARCHAR(255),
  current VARCHAR(255) DEFAULT NULL,
  dateon VARCHAR(255) DEFAULT NULL,
  dateoff VARCHAR(255) DEFAULT NULL
);

-- 初始化 ScienceBook 数据
INSERT INTO Science (number, classnumber, name, classname, price, state, total) VALUES
('S01', '2', 'Sample Science Book', 'Science', '30', 'in', '1');

-- 执行文件中提到的更新操作 [cite: 218, 219]
UPDATE Science SET current = '123' WHERE number = 'S01';
UPDATE Science SET dateoff = '20170920' WHERE number = 'S01';


-- ----------------------------
-- 6. 个人借阅记录表模板 (user)
-- 这里创建基础 user 表作为结构参考 [cite: 219]
-- ----------------------------
DROP TABLE IF EXISTS BorrowRecords;
CREATE TABLE BorrowRecords (
  number VARCHAR(255) PRIMARY KEY COMMENT '书籍编号',
  classname VARCHAR(255) COMMENT '类别名称',
  name VARCHAR(255) COMMENT '书名',
  dateoff VARCHAR(255) COMMENT '期限',
  username VARCHAR(255) COMMENT '借阅人'
);


-- ----------------------------
-- 7. 临时测试表 (temp)
-- ----------------------------
DROP TABLE IF EXISTS temp;
CREATE TABLE temp (
  number INT(11),
  name VARCHAR(255),
  location VARCHAR(255)
);

-- 插入测试数据 [cite: 163]
INSERT INTO temp VALUES ('123', 'Tom', 'Changsha');

-- 脚本结束
SELECT 'Database BookManageSYS initialized successfully.' AS status;
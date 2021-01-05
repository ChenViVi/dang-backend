-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1:3306
-- 生成日期： 2021-01-05 15:34:39
-- 服务器版本： 5.7.23
-- PHP 版本： 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `yellowzero`
--

-- --------------------------------------------------------

--
-- 表的结构 `music`
--

DROP TABLE IF EXISTS `music`;
CREATE TABLE IF NOT EXISTS `music` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `url` text NOT NULL,
  `link` text,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `music`
--

INSERT INTO `music` (`id`, `name`, `url`, `link`) VALUES
(1, '惊鸿一面\r\n', 'http://yellowzero.wblnb.com/b1.%E6%83%8A%E9%B8%BF%E4%B8%80%E9%9D%A2.wav', 'https://www.bilibili.com/video/BV1AE411L7BK'),
(2, '风月', 'http://yellowzero.wblnb.com/b2.%E9%A3%8E%E6%9C%88.wav', 'https://www.bilibili.com/video/BV1UE411V7Ud'),
(3, '等待', 'http://yellowzero.wblnb.com/b3.%E7%AD%89%E5%BE%85.wav', 'https://www.bilibili.com/video/BV157411d79B');

-- --------------------------------------------------------

--
-- 表的结构 `music_tag`
--

DROP TABLE IF EXISTS `music_tag`;
CREATE TABLE IF NOT EXISTS `music_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `music_tag`
--

INSERT INTO `music_tag` (`id`, `name`) VALUES
(1, 'B站'),
(2, '吉他');

-- --------------------------------------------------------

--
-- 表的结构 `music_tag_join`
--

DROP TABLE IF EXISTS `music_tag_join`;
CREATE TABLE IF NOT EXISTS `music_tag_join` (
  `music_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`music_id`,`tag_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- 转存表中的数据 `music_tag_join`
--

INSERT INTO `music_tag_join` (`music_id`, `tag_id`) VALUES
(1, 1),
(2, 1),
(3, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

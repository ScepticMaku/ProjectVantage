-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 10, 2025 at 03:41 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectvantage_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `creation_date` date NOT NULL,
  `due_date` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`id`, `name`, `description`, `creation_date`, `due_date`, `user_id`, `status_id`) VALUES
(1, 'Test Project', 'This is a test project', '2025-03-29', '2025-03-31', 44, 1),
(3, 'Pangilatan Peak', 'Manaka Pangilatan', '2025-04-16', '2025-04-25', 44, 1),
(5, 'testttttt', 'asdfsdaf', '2025-04-19', '2025-05-10', 48, 1),
(7, 'IM207 Project 2', 'Test', '2025-05-10', '2025-07-09', 44, 1);

-- --------------------------------------------------------

--
-- Table structure for table `project_status`
--

CREATE TABLE `project_status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project_status`
--

INSERT INTO `project_status` (`id`, `name`) VALUES
(1, 'not completed'),
(2, 'completed');

-- --------------------------------------------------------

--
-- Table structure for table `system_log`
--

CREATE TABLE `system_log` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `system_log`
--

INSERT INTO `system_log` (`id`, `user_id`, `action`, `description`, `timestamp`) VALUES
(3, 44, 'Login Failed', 'Password does not match.', '2025-03-26 05:50:16'),
(4, 44, 'Login Failed', 'Password does not match.', '2025-03-26 05:56:40'),
(5, 44, 'Login Success', 'Successfully logged in', '2025-03-26 05:56:43'),
(6, 44, 'Login Failed', 'Password does not match.', '2025-03-26 06:06:15'),
(7, 44, 'Login Success', 'Successfully logged in', '2025-03-26 06:06:20'),
(8, 44, 'Login Success', 'Successfully logged in', '2025-03-28 04:02:13'),
(9, 44, 'Login Success', 'Successfully logged in', '2025-03-28 04:03:01'),
(10, 44, 'Login Success', 'Successfully logged in', '2025-03-28 04:03:52'),
(11, 44, 'Login Success', 'Successfully logged in', '2025-03-28 07:44:50'),
(12, 45, 'Login Success', 'Successfully logged in', '2025-03-28 07:46:46'),
(13, 44, 'Login Success', 'Successfully logged in', '2025-03-28 07:46:58'),
(14, 44, 'Login Failed', 'Password does not match.', '2025-03-28 07:49:20'),
(15, 44, 'Login Success', 'Successfully logged in', '2025-03-28 07:49:24'),
(16, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:03:21'),
(17, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:05:18'),
(18, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:35:46'),
(19, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:37:08'),
(20, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:38:09'),
(21, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:38:44'),
(22, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:40:03'),
(23, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:41:28'),
(24, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:42:44'),
(25, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:43:48'),
(26, 44, 'Login Failed', 'Password does not match.', '2025-03-28 08:44:04'),
(27, 44, 'Login Failed', 'Password does not match.', '2025-03-28 08:44:05'),
(28, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:44:06'),
(29, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:44:30'),
(30, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:45:49'),
(31, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:46:35'),
(32, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:49:35'),
(33, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:50:20'),
(34, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:50:54'),
(35, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:51:55'),
(36, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:56:58'),
(37, 44, 'Login Success', 'Successfully logged in', '2025-03-28 08:58:23'),
(38, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:17:00'),
(39, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:17:40'),
(40, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:18:22'),
(41, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:22:14'),
(42, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:24:44'),
(43, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:25:31'),
(44, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:25:53'),
(45, 44, 'Login Failed', 'Password does not match.', '2025-03-28 09:26:19'),
(46, 44, 'Login Failed', 'Password does not match.', '2025-03-28 09:26:20'),
(47, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:26:22'),
(48, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:26:46'),
(49, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:28:52'),
(50, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:29:24'),
(51, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:30:32'),
(52, 44, 'Login Failed', 'Password does not match.', '2025-03-28 09:31:05'),
(53, 44, 'Login Failed', 'Password does not match.', '2025-03-28 09:31:05'),
(54, 44, 'Login Success', 'Successfully logged in', '2025-03-28 09:31:07'),
(55, 44, 'Login Success', 'Successfully logged in', '2025-03-28 12:43:50'),
(56, 44, 'Login Success', 'Successfully logged in', '2025-03-28 12:50:01'),
(57, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:37:17'),
(58, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:37:56'),
(59, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:39:52'),
(60, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:40:37'),
(61, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:42:41'),
(62, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:44:56'),
(63, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:45:18'),
(64, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:46:15'),
(65, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:46:49'),
(66, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:49:42'),
(67, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:49:56'),
(68, 44, 'Login Failed', 'Password does not match.', '2025-03-28 14:50:15'),
(69, 44, 'Login Failed', 'Password does not match.', '2025-03-28 14:50:17'),
(70, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:50:18'),
(71, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:52:01'),
(72, 44, 'Login Failed', 'Password does not match.', '2025-03-28 14:52:30'),
(73, 44, 'Login Failed', 'Password does not match.', '2025-03-28 14:52:31'),
(74, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:52:32'),
(75, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:53:38'),
(76, 44, 'Login Failed', 'Password does not match.', '2025-03-28 14:54:53'),
(77, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:54:55'),
(78, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:55:37'),
(79, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:56:02'),
(80, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:58:37'),
(81, 44, 'Login Success', 'Successfully logged in', '2025-03-28 14:59:35'),
(82, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:02:33'),
(83, 46, 'Login Failed', 'Account is not active yet.', '2025-03-28 15:11:27'),
(84, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:11:31'),
(85, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:13:34'),
(86, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:18:18'),
(87, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:18:55'),
(88, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:19:58'),
(89, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:21:24'),
(90, 44, 'Login Failed', 'Password does not match.', '2025-03-28 15:21:48'),
(91, 44, 'Login Failed', 'Password does not match.', '2025-03-28 15:21:49'),
(92, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:21:50'),
(93, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:25:00'),
(94, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:35:06'),
(95, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:36:19'),
(96, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:36:46'),
(97, 44, 'Login Failed', 'Password does not match.', '2025-03-28 15:43:39'),
(98, 44, 'Login Failed', 'Password does not match.', '2025-03-28 15:43:40'),
(99, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:43:41'),
(100, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:45:04'),
(101, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:49:40'),
(102, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:54:39'),
(103, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:56:17'),
(104, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:57:12'),
(105, 44, 'Login Success', 'Successfully logged in', '2025-03-28 15:59:07'),
(106, 44, 'Login Success', 'Successfully logged in', '2025-03-28 16:00:31'),
(107, 44, 'Login Success', 'Successfully logged in', '2025-03-28 22:54:33'),
(108, 44, 'Login Failed', 'Password does not match.', '2025-03-28 22:56:47'),
(109, 44, 'Login Failed', 'Password does not match.', '2025-03-28 22:56:49'),
(110, 44, 'Login Failed', 'Password does not match.', '2025-03-28 22:56:52'),
(111, 44, 'Login Success', 'Successfully logged in', '2025-03-28 22:56:56'),
(112, 44, 'Login Success', 'Successfully logged in', '2025-03-28 22:58:34'),
(113, 44, 'Login Failed', 'Password does not match.', '2025-03-28 22:59:44'),
(114, 44, 'Login Failed', 'Password does not match.', '2025-03-28 22:59:45'),
(115, 44, 'Login Success', 'Successfully logged in', '2025-03-28 22:59:47'),
(116, 44, 'Login Success', 'Successfully logged in', '2025-03-28 23:01:56'),
(117, 44, 'Login Success', 'Successfully logged in', '2025-03-28 23:02:10'),
(118, 44, 'Login Success', 'Successfully logged in', '2025-03-29 00:13:29'),
(119, 44, 'Login Success', 'Successfully logged in', '2025-03-29 00:28:15'),
(120, 44, 'Login Success', 'Successfully logged in', '2025-03-29 00:54:03'),
(121, 44, 'Login Failed', 'Password does not match.', '2025-03-29 01:15:49'),
(122, 44, 'Login Failed', 'Password does not match.', '2025-03-29 01:15:50'),
(123, 44, 'Login Success', 'Successfully logged in', '2025-03-29 01:15:51'),
(124, 44, 'Login Success', 'Successfully logged in', '2025-03-29 01:39:19'),
(125, 44, 'Login Success', 'Successfully logged in', '2025-03-29 01:48:14'),
(126, 44, 'Login Success', 'Successfully logged in', '2025-03-29 01:50:25'),
(127, 44, 'Login Success', 'Successfully logged in', '2025-03-29 12:14:09'),
(128, 44, 'Login Success', 'Successfully logged in', '2025-03-29 12:55:33'),
(129, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:01:14'),
(130, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:01:59'),
(131, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:03:16'),
(132, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:03:50'),
(133, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:05:49'),
(134, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:08:22'),
(135, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:09:25'),
(136, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:10:17'),
(137, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:12:56'),
(138, 44, 'Login Failed', 'Password does not match.', '2025-03-29 13:13:26'),
(139, 44, 'Login Failed', 'Password does not match.', '2025-03-29 13:13:27'),
(140, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:13:29'),
(141, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:13:47'),
(142, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:14:04'),
(143, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:14:40'),
(144, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:17:01'),
(145, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:20:24'),
(146, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:23:45'),
(147, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:26:43'),
(148, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:30:21'),
(149, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:30:45'),
(150, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:36:19'),
(151, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:37:06'),
(152, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:38:19'),
(153, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:39:09'),
(154, 44, 'Login Success', 'Successfully logged in', '2025-03-29 13:41:18'),
(155, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:21:02'),
(156, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:22:35'),
(157, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:28:05'),
(158, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:31:08'),
(159, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:33:44'),
(160, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:36:10'),
(161, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:39:46'),
(162, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:39:59'),
(163, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:41:00'),
(164, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:41:52'),
(165, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:43:21'),
(166, 44, 'Login Failed', 'Password does not match.', '2025-03-29 15:45:09'),
(167, 44, 'Login Failed', 'Password does not match.', '2025-03-29 15:45:10'),
(168, 44, 'Login Failed', 'Password does not match.', '2025-03-29 15:45:11'),
(169, 44, 'Login Failed', 'Password does not match.', '2025-03-29 15:45:12'),
(170, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:45:13'),
(171, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:46:53'),
(172, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:47:08'),
(173, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:47:30'),
(174, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:47:56'),
(175, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:48:51'),
(176, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:49:51'),
(177, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:50:52'),
(178, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:51:06'),
(179, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:53:31'),
(180, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:54:11'),
(181, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:56:52'),
(182, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:57:59'),
(183, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:59:11'),
(184, 44, 'Login Success', 'Successfully logged in', '2025-03-29 15:59:54'),
(185, 44, 'Login Success', 'Successfully logged in', '2025-03-29 16:00:16'),
(186, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:00:14'),
(187, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:31:33'),
(188, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:31:49'),
(189, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:32:32'),
(190, 44, 'Login Failed', 'Password does not match.', '2025-03-30 00:33:29'),
(191, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:33:31'),
(192, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:38:37'),
(193, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:39:31'),
(194, 44, 'Login Failed', 'Password does not match.', '2025-03-30 00:40:03'),
(195, 44, 'Login Failed', 'Password does not match.', '2025-03-30 00:40:03'),
(196, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:40:05'),
(197, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:40:46'),
(198, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:42:04'),
(199, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:42:20'),
(200, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:46:22'),
(201, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:47:22'),
(202, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:47:37'),
(203, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:47:56'),
(204, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:48:47'),
(205, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:49:09'),
(206, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:49:44'),
(207, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:50:01'),
(208, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:50:54'),
(209, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:51:18'),
(210, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:53:39'),
(211, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:54:17'),
(212, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:54:52'),
(213, 44, 'Login Success', 'Successfully logged in', '2025-03-30 00:57:57'),
(214, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:16:19'),
(215, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:24:35'),
(216, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:27:14'),
(217, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:27:49'),
(218, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:28:13'),
(219, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:33:47'),
(220, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:34:30'),
(221, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:34:53'),
(222, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:36:39'),
(223, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:38:27'),
(224, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:39:29'),
(225, 44, 'Login Failed', 'Password does not match.', '2025-03-30 01:43:31'),
(226, 44, 'Login Success', 'Successfully logged in', '2025-03-30 01:43:32'),
(227, 44, 'Login Success', 'Successfully logged in', '2025-03-30 02:21:50'),
(228, 44, 'Login Success', 'Successfully logged in', '2025-03-30 02:22:28'),
(229, 44, 'Login Success', 'Successfully logged in', '2025-03-30 02:22:48'),
(230, 44, 'Login Failed', 'Password does not match.', '2025-03-30 03:57:36'),
(231, 44, 'Login Failed', 'Password does not match.', '2025-03-30 03:57:37'),
(232, 44, 'Login Failed', 'Password does not match.', '2025-03-30 03:57:38'),
(233, 44, 'Login Success', 'Successfully logged in', '2025-03-30 03:57:39'),
(234, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:22:24'),
(235, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:22:45'),
(236, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:23:30'),
(237, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:25:11'),
(238, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:28:08'),
(239, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:31:34'),
(240, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:37:29'),
(241, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:37:51'),
(242, 44, 'Login Success', 'Successfully logged in', '2025-03-30 04:38:21'),
(243, 44, 'Login Success', 'Successfully logged in', '2025-03-30 12:34:16'),
(244, 44, 'Login Success', 'Successfully logged in', '2025-03-30 12:39:43'),
(245, 44, 'Login Success', 'Successfully logged in', '2025-03-30 12:40:17'),
(246, 44, 'Login Success', 'Successfully logged in', '2025-03-30 12:59:16'),
(247, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:04:44'),
(248, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:09:40'),
(249, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:10:15'),
(250, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:11:19'),
(251, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:11:49'),
(252, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:12:17'),
(253, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:13:05'),
(254, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:13:33'),
(255, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:14:08'),
(256, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:22:12'),
(257, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:24:42'),
(258, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:25:47'),
(259, 44, 'Login Success', 'Successfully logged in', '2025-03-30 13:27:23'),
(260, 44, 'Login Success', 'Successfully logged in', '2025-03-30 22:44:53'),
(261, 44, 'Login Failed', 'Password does not match.', '2025-03-30 22:51:12'),
(262, 44, 'Login Failed', 'Password does not match.', '2025-03-30 22:51:13'),
(263, 44, 'Login Success', 'Successfully logged in', '2025-03-30 22:51:14'),
(264, 44, 'Login Success', 'Successfully logged in', '2025-03-30 22:55:13'),
(265, 44, 'Login Success', 'Successfully logged in', '2025-03-30 22:57:45'),
(266, 44, 'Login Success', 'Successfully logged in', '2025-03-30 22:58:10'),
(267, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:10:44'),
(268, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:21:01'),
(269, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:28:34'),
(270, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:28:52'),
(271, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:29:10'),
(272, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:44:39'),
(273, 44, 'Login Success', 'Successfully logged in', '2025-03-30 23:57:11'),
(274, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:00:17'),
(275, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:12:32'),
(276, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:13:13'),
(277, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:14:08'),
(278, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:16:12'),
(279, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:16:54'),
(280, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:17:47'),
(281, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:17:49'),
(282, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:24:11'),
(283, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:24:12'),
(284, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:29:45'),
(285, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:30:15'),
(286, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:30:41'),
(287, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:30:43'),
(288, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:30:44'),
(289, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:33:26'),
(290, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:34:22'),
(291, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:34:47'),
(292, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:35:15'),
(293, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:35:46'),
(294, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:37:27'),
(295, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:38:03'),
(296, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:41:16'),
(297, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:42:33'),
(298, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:43:12'),
(299, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:43:38'),
(300, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:43:38'),
(301, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:43:41'),
(302, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:45:28'),
(303, 44, 'Login Failed', 'Password does not match.', '2025-03-31 00:45:29'),
(304, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:45:31'),
(305, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:45:50'),
(306, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:46:57'),
(307, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:47:33'),
(308, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:52:32'),
(309, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:53:12'),
(310, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:53:54'),
(311, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:55:16'),
(312, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:55:59'),
(313, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:57:16'),
(314, 44, 'Login Success', 'Successfully logged in', '2025-03-31 00:59:52'),
(315, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:00:47'),
(316, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:02:21'),
(317, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:02:47'),
(318, 44, 'Login Failed', 'Password does not match.', '2025-03-31 01:03:52'),
(319, 44, 'Login Failed', 'Password does not match.', '2025-03-31 01:03:54'),
(320, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:03:55'),
(321, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:04:33'),
(322, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:05:32'),
(323, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:06:17'),
(324, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:06:45'),
(325, 44, 'Login Failed', 'Password does not match.', '2025-03-31 01:07:04'),
(326, 44, 'Login Failed', 'Password does not match.', '2025-03-31 01:07:04'),
(327, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:07:06'),
(328, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:08:47'),
(329, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:09:06'),
(330, 44, 'Login Success', 'Successfully logged in', '2025-03-31 01:30:48'),
(331, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:54:14'),
(332, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:54:42'),
(333, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:56:21'),
(334, 44, 'Login Failed', 'Password does not match.', '2025-03-31 02:57:21'),
(335, 44, 'Login Failed', 'Password does not match.', '2025-03-31 02:57:21'),
(336, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:57:23'),
(337, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:59:10'),
(338, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:59:31'),
(339, 44, 'Login Success', 'Successfully logged in', '2025-03-31 02:59:48'),
(340, 44, 'Login Success', 'Successfully logged in', '2025-03-31 03:30:54'),
(341, 44, 'Login Failed', 'Password does not match.', '2025-03-31 03:37:22'),
(342, 44, 'Login Failed', 'Password does not match.', '2025-03-31 03:37:24'),
(343, 44, 'Login Success', 'Successfully logged in', '2025-03-31 03:37:28'),
(344, 44, 'Login Success', 'Successfully logged in', '2025-03-31 03:54:19'),
(345, 44, 'Login Success', 'Successfully logged in', '2025-03-31 03:54:46'),
(346, 44, 'Login Success', 'Successfully logged in', '2025-03-31 03:55:09'),
(347, 44, 'Login Success', 'Successfully logged in', '2025-03-31 03:56:07'),
(348, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:18:10'),
(349, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:22:12'),
(350, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:27:19'),
(351, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:35:35'),
(352, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:44:16'),
(353, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:44:57'),
(354, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:46:19'),
(355, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:48:49'),
(356, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:49:13'),
(357, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:50:41'),
(358, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:56:33'),
(359, 44, 'Login Success', 'Successfully logged in', '2025-03-31 04:59:53'),
(360, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:10:04'),
(361, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:13:48'),
(362, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:14:40'),
(363, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:27:52'),
(364, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:31:59'),
(365, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:32:45'),
(366, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:33:21'),
(367, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:42:34'),
(368, 44, 'Login Success', 'Successfully logged in', '2025-03-31 05:46:10'),
(369, 44, 'Login Success', 'Successfully logged in', '2025-03-31 07:24:35'),
(370, 44, 'Login Success', 'Successfully logged in', '2025-03-31 07:25:17'),
(371, 44, 'Login Failed', 'Password does not match.', '2025-03-31 07:26:06'),
(372, 44, 'Login Success', 'Successfully logged in', '2025-03-31 07:26:09'),
(373, 44, 'Login Success', 'Successfully logged in', '2025-03-31 07:26:56'),
(374, 44, 'Login Failed', 'Password does not match.', '2025-03-31 10:32:16'),
(375, 44, 'Login Failed', 'Password does not match.', '2025-03-31 10:32:17'),
(376, 44, 'Login Success', 'Successfully logged in', '2025-03-31 10:32:18'),
(377, 44, 'Login Success', 'Successfully logged in', '2025-03-31 10:35:36'),
(378, 44, 'Login Success', 'Successfully logged in', '2025-03-31 10:52:45'),
(379, 44, 'Login Success', 'Successfully logged in', '2025-04-03 00:43:38'),
(380, 44, 'Login Success', 'Successfully logged in', '2025-04-03 00:46:27'),
(381, 44, 'Login Success', 'Successfully logged in', '2025-04-03 00:46:50'),
(382, 44, 'Login Success', 'Successfully logged in', '2025-04-03 03:56:04'),
(383, 44, 'Login Success', 'Successfully logged in', '2025-04-03 03:57:21'),
(384, 44, 'Login Success', 'Successfully logged in', '2025-04-03 03:58:05'),
(385, 44, 'Login Success', 'Successfully logged in', '2025-04-03 14:35:48'),
(386, 44, 'Login Success', 'Successfully logged in', '2025-04-03 15:02:42'),
(387, 52, 'Login Failed', 'Account is not active yet.', '2025-04-03 15:03:38'),
(388, 52, 'Login Failed', 'Account is not active yet.', '2025-04-03 15:03:45'),
(389, 44, 'Login Success', 'Successfully logged in', '2025-04-03 15:03:47'),
(390, 52, 'Login Success', 'Successfully logged in', '2025-04-03 15:04:04'),
(391, 44, 'Login Success', 'Successfully logged in', '2025-04-03 15:14:33'),
(392, 44, 'Login Success', 'Successfully logged in', '2025-04-14 13:09:57'),
(393, 44, 'Login Success', 'Successfully logged in', '2025-04-16 06:32:37'),
(394, 44, 'Login Success', 'Successfully logged in', '2025-04-16 06:36:49'),
(395, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:30:12'),
(396, 53, 'Login Failed', 'Account is not active yet.', '2025-04-16 23:34:09'),
(397, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:34:30'),
(398, 53, 'Login Success', 'Successfully logged in', '2025-04-16 23:35:16'),
(399, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:54:45'),
(400, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:55:30'),
(401, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:55:54'),
(402, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:56:27'),
(403, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:57:00'),
(404, 44, 'Login Failed', 'Password does not match.', '2025-04-16 23:57:37'),
(405, 44, 'Login Failed', 'Password does not match.', '2025-04-16 23:57:37'),
(406, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:57:39'),
(407, 44, 'Login Success', 'Successfully logged in', '2025-04-16 23:58:12'),
(408, 44, 'Login Success', 'Successfully logged in', '2025-04-17 00:02:52'),
(409, 44, 'Login Success', 'Successfully logged in', '2025-04-17 00:07:29'),
(410, 44, 'Login Success', 'Successfully logged in', '2025-04-17 00:16:22'),
(411, 44, 'Login Success', 'Successfully logged in', '2025-04-17 00:25:52'),
(412, 44, 'Login Success', 'Successfully logged in', '2025-04-17 01:59:14'),
(413, 44, 'Login Success', 'Successfully logged in', '2025-04-17 02:07:17'),
(414, 44, 'Login Success', 'Successfully logged in', '2025-04-17 02:07:53'),
(415, 44, 'Login Success', 'Successfully logged in', '2025-04-17 02:30:22'),
(416, 44, 'Login Failed', 'Password does not match.', '2025-04-17 02:45:56'),
(417, 44, 'Login Success', 'Successfully logged in', '2025-04-17 02:45:58'),
(418, 44, 'Login Success', 'Successfully logged in', '2025-04-17 03:16:19'),
(419, 44, 'Login Success', 'Successfully logged in', '2025-04-17 03:22:12'),
(420, 44, 'Login Success', 'Successfully logged in', '2025-04-18 03:01:41'),
(421, 44, 'Login Success', 'Successfully logged in', '2025-04-18 03:02:00'),
(422, 44, 'Login Success', 'Successfully logged in', '2025-04-18 05:50:06'),
(423, 44, 'Login Success', 'Successfully logged in', '2025-04-18 05:51:26'),
(424, 44, 'Login Failed', 'Password does not match.', '2025-04-18 05:57:21'),
(425, 44, 'Login Failed', 'Password does not match.', '2025-04-18 05:57:22'),
(426, 44, 'Login Success', 'Successfully logged in', '2025-04-18 05:57:25'),
(427, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:08:04'),
(428, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:09:54'),
(429, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:25:09'),
(430, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:27:59'),
(431, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:28:52'),
(432, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:30:15'),
(433, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:31:49'),
(434, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:32:36'),
(435, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:34:47'),
(436, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:35:57'),
(437, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:36:08'),
(438, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:36:16'),
(439, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:37:02'),
(440, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:38:08'),
(441, 45, 'Login Failed', 'Password does not match.', '2025-04-18 06:45:11'),
(442, 45, 'Login Failed', 'Password does not match.', '2025-04-18 06:45:11'),
(443, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:45:14'),
(444, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:45:24'),
(445, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:47:03'),
(446, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:47:27'),
(447, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:48:00'),
(448, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:48:35'),
(449, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:49:08'),
(450, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:50:33'),
(451, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:51:16'),
(452, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:51:28'),
(453, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:51:59'),
(454, 44, 'Login Success', 'Successfully logged in', '2025-04-18 06:52:59'),
(455, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:53:06'),
(456, 45, 'Login Failed', 'Password does not match.', '2025-04-18 06:53:31'),
(457, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:53:33'),
(458, 45, 'Login Failed', 'Password does not match.', '2025-04-18 06:54:24'),
(459, 45, 'Login Success', 'Successfully logged in', '2025-04-18 06:54:25'),
(460, 44, 'Login Success', 'Successfully logged in', '2025-04-18 12:09:24'),
(461, 45, 'Login Success', 'Successfully logged in', '2025-04-18 12:09:32'),
(462, 44, 'Login Success', 'Successfully logged in', '2025-04-18 22:40:04'),
(463, 44, 'Login Success', 'Successfully logged in', '2025-04-18 22:41:46'),
(464, 45, 'Login Success', 'Successfully logged in', '2025-04-18 22:43:08'),
(465, 44, 'Login Success', 'Successfully logged in', '2025-04-18 22:43:29'),
(466, 44, 'Login Success', 'Successfully logged in', '2025-04-18 22:45:58'),
(467, 44, 'Login Success', 'Successfully logged in', '2025-04-18 22:47:00'),
(468, 44, 'Login Success', 'Successfully logged in', '2025-04-18 22:47:52'),
(469, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:21:38'),
(470, 47, 'Login Failed', 'Role not found.', '2025-04-18 23:22:01'),
(471, 47, 'Login Success', 'Successfully logged in', '2025-04-18 23:22:01'),
(472, 47, 'Login Failed', 'Role not found.', '2025-04-18 23:25:56'),
(473, 47, 'Login Success', 'Successfully logged in', '2025-04-18 23:25:56'),
(474, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:26:46'),
(475, 47, 'Login Failed', 'Role not found.', '2025-04-18 23:26:56'),
(476, 47, 'Login Success', 'Successfully logged in', '2025-04-18 23:26:56'),
(477, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:27:12'),
(478, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:31:10'),
(479, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:32:30'),
(480, 48, 'Login Failed', 'Password does not match.', '2025-04-18 23:32:37'),
(481, 48, 'Login Failed', 'Password does not match.', '2025-04-18 23:32:38'),
(482, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:32:39'),
(483, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:34:19'),
(484, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:34:24'),
(485, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:36:16'),
(486, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:44:13'),
(487, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:46:07'),
(488, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:54:11'),
(489, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:55:11'),
(490, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:56:19'),
(491, 48, 'Login Success', 'Successfully logged in', '2025-04-18 23:57:24'),
(492, 44, 'Login Success', 'Successfully logged in', '2025-04-18 23:59:54'),
(493, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:00:01'),
(494, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:01:47'),
(495, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:04:46'),
(496, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:05:25'),
(497, 44, 'Login Success', 'Successfully logged in', '2025-04-19 00:12:10'),
(498, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:12:15'),
(499, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:24:43'),
(500, 48, 'Login Success', 'Successfully logged in', '2025-04-19 00:28:26'),
(501, 48, 'Login Failed', 'Password does not match.', '2025-04-19 01:01:57'),
(502, 48, 'Login Success', 'Successfully logged in', '2025-04-19 01:01:59'),
(503, 44, 'Login Success', 'Successfully logged in', '2025-04-19 01:02:26'),
(504, 44, 'Login Success', 'Successfully logged in', '2025-04-19 01:04:31'),
(505, 48, 'Login Success', 'Successfully logged in', '2025-04-19 01:04:36'),
(506, 44, 'Login Failed', 'Password does not match.', '2025-04-19 02:16:57'),
(507, 44, 'Login Failed', 'Password does not match.', '2025-04-19 02:16:57'),
(508, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:17:01'),
(509, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:17:06'),
(510, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:17:42'),
(511, 48, 'Login Failed', 'Password does not match.', '2025-04-19 02:17:50'),
(512, 48, 'Login Failed', 'Password does not match.', '2025-04-19 02:17:51'),
(513, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:17:52'),
(514, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:19:37'),
(515, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:19:45'),
(516, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:25:36'),
(517, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:26:38'),
(518, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:27:24'),
(519, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:27:43'),
(520, 44, 'Login Failed', 'Password does not match.', '2025-04-19 02:28:05'),
(521, 44, 'Login Failed', 'Password does not match.', '2025-04-19 02:28:05'),
(522, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:28:07'),
(523, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:29:50'),
(524, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:31:11'),
(525, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:31:31'),
(526, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:31:51'),
(527, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:32:07'),
(528, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:32:30'),
(529, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:33:28'),
(530, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:33:45'),
(531, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:36:27'),
(532, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:36:59'),
(533, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:43:18'),
(534, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:43:50'),
(535, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:49:24'),
(536, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:49:57'),
(537, 48, 'Login Success', 'Successfully logged in', '2025-04-19 02:50:52'),
(538, 44, 'Login Success', 'Successfully logged in', '2025-04-19 02:51:29'),
(539, 44, 'Login Success', 'Successfully logged in', '2025-04-19 03:10:21'),
(540, 44, 'Login Failed', 'Password does not match.', '2025-04-19 03:28:06'),
(541, 44, 'Login Failed', 'Password does not match.', '2025-04-19 03:28:07'),
(542, 44, 'Login Success', 'Successfully logged in', '2025-04-19 03:28:10'),
(543, 44, 'Login Failed', 'Password does not match.', '2025-04-19 04:46:31'),
(544, 44, 'Login Failed', 'Password does not match.', '2025-04-19 04:46:33'),
(545, 44, 'Login Success', 'Successfully logged in', '2025-04-19 04:46:36'),
(546, 44, 'Login Success', 'Successfully logged in', '2025-04-19 05:22:31'),
(547, 44, 'Login Success', 'Successfully logged in', '2025-04-19 05:24:26'),
(548, 44, 'Login Success', 'Successfully logged in', '2025-04-19 05:28:35'),
(549, 44, 'Login Success', 'Successfully logged in', '2025-04-19 05:29:25'),
(550, 44, 'Login Success', 'Successfully logged in', '2025-04-19 06:04:35'),
(551, 44, 'Login Success', 'Successfully logged in', '2025-04-19 06:22:07'),
(552, 44, 'Login Failed', 'Password does not match.', '2025-04-19 06:22:49'),
(553, 44, 'Login Success', 'Successfully logged in', '2025-04-19 06:22:51'),
(554, 44, 'Login Success', 'Successfully logged in', '2025-04-19 06:57:29'),
(555, 48, 'Login Success', 'Successfully logged in', '2025-04-19 06:59:32'),
(556, 48, 'Login Success', 'Successfully logged in', '2025-04-19 07:02:56'),
(557, 44, 'Login Success', 'Successfully logged in', '2025-04-19 07:14:45'),
(558, 44, 'Login Success', 'Successfully logged in', '2025-04-19 07:18:05'),
(559, 47, 'Login Failed', 'Role not found.', '2025-04-19 07:18:17'),
(560, 47, 'Login Success', 'Successfully logged in', '2025-04-19 07:18:17'),
(561, 47, 'Login Success', 'Successfully logged in', '2025-04-19 07:21:03'),
(562, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:22:48'),
(563, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:52:23'),
(564, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:54:33'),
(565, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:55:03'),
(566, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:56:46'),
(567, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:57:37'),
(568, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:57:59'),
(569, 44, 'Login Success', 'Successfully logged in', '2025-04-19 12:59:46'),
(570, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:00:22'),
(571, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:01:03'),
(572, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:01:47'),
(573, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:21:26'),
(574, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:21:57'),
(575, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:35:06'),
(576, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:48:58'),
(577, 44, 'Login Failed', 'Password does not match.', '2025-04-19 13:57:39'),
(578, 44, 'Login Failed', 'Password does not match.', '2025-04-19 13:57:39'),
(579, 44, 'Login Success', 'Successfully logged in', '2025-04-19 13:57:41'),
(580, 44, 'Login Success', 'Successfully logged in', '2025-04-19 14:19:06'),
(581, 44, 'Login Success', 'Successfully logged in', '2025-04-20 01:45:44'),
(582, 44, 'Login Success', 'Successfully logged in', '2025-04-20 01:48:34'),
(583, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:14:02'),
(584, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:14:51'),
(585, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:19:55'),
(586, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:20:17'),
(587, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:21:09'),
(588, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:21:36'),
(589, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:22:06'),
(590, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:22:23'),
(591, 44, 'Login Failed', 'Password does not match.', '2025-04-20 02:23:46'),
(592, 44, 'Login Failed', 'Password does not match.', '2025-04-20 02:23:47'),
(593, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:23:48'),
(594, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:24:11'),
(595, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:24:30'),
(596, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:29:51'),
(597, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:30:10'),
(598, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:30:58'),
(599, 44, 'Login Failed', 'Password does not match.', '2025-04-20 02:32:41'),
(600, 44, 'Login Failed', 'Password does not match.', '2025-04-20 02:32:42'),
(601, 44, 'Login Failed', 'Password does not match.', '2025-04-20 02:32:44'),
(602, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:32:46'),
(603, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:58:05'),
(604, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:58:29'),
(605, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:59:26'),
(606, 44, 'Login Success', 'Successfully logged in', '2025-04-20 02:59:54'),
(607, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:00:26'),
(608, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:02:16'),
(609, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:03:41'),
(610, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:34:02'),
(611, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:34:53'),
(612, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:36:10'),
(613, 44, 'Login Failed', 'Password does not match.', '2025-04-20 03:38:21'),
(614, 44, 'Login Failed', 'Password does not match.', '2025-04-20 03:38:22'),
(615, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:38:24'),
(616, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:39:44'),
(617, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:53:04'),
(618, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:53:28'),
(619, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:54:14'),
(620, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:57:07'),
(621, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:58:28'),
(622, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:59:06'),
(623, 44, 'Login Success', 'Successfully logged in', '2025-04-20 03:59:38'),
(624, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:01:01'),
(625, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:03:19'),
(626, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:03:48'),
(627, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:07:17'),
(628, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:28:00'),
(629, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:29:58'),
(630, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:32:01'),
(631, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:32:19'),
(632, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:39:10'),
(633, 44, 'Login Success', 'Successfully logged in', '2025-04-20 04:41:24'),
(634, 44, 'Login Success', 'Successfully logged in', '2025-04-20 05:05:54'),
(635, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:24:25'),
(636, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:45:54'),
(637, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:47:02'),
(638, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:51:06'),
(639, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:53:34'),
(640, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:58:28'),
(641, 44, 'Login Success', 'Successfully logged in', '2025-04-20 10:59:28'),
(642, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:00:18'),
(643, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:00:46'),
(644, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:03:34'),
(645, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:05:32'),
(646, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:07:28'),
(647, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:09:03'),
(648, 45, 'Login Success', 'Successfully logged in', '2025-04-20 11:31:47'),
(649, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:33:29'),
(650, 44, 'Login Failed', 'Password does not match.', '2025-04-20 11:35:03'),
(651, 44, 'Login Failed', 'Password does not match.', '2025-04-20 11:35:04'),
(652, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:35:05'),
(653, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:41:43'),
(654, 45, 'Login Success', 'Successfully logged in', '2025-04-20 11:42:05'),
(655, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:43:06'),
(656, 45, 'Login Success', 'Successfully logged in', '2025-04-20 11:43:17'),
(657, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:51:49'),
(658, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:53:16'),
(659, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:53:34'),
(660, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:54:07'),
(661, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:58:07'),
(662, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:58:51'),
(663, 44, 'Login Success', 'Successfully logged in', '2025-04-20 11:59:13'),
(664, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:00:44'),
(665, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:09:44'),
(666, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:17:17'),
(667, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:17:48');
INSERT INTO `system_log` (`id`, `user_id`, `action`, `description`, `timestamp`) VALUES
(668, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:18:38'),
(669, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:19:19'),
(670, 44, 'Login Failed', 'Password does not match.', '2025-04-20 12:26:13'),
(671, 44, 'Login Failed', 'Password does not match.', '2025-04-20 12:26:13'),
(672, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:26:17'),
(673, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:27:00'),
(674, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:27:32'),
(675, 44, 'Login Success', 'Successfully logged in', '2025-04-20 12:43:18'),
(676, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:05:32'),
(677, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:08:09'),
(678, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:09:42'),
(679, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:10:39'),
(680, 44, 'Login Failed', 'Password does not match.', '2025-04-20 13:11:51'),
(681, 44, 'Login Failed', 'Password does not match.', '2025-04-20 13:11:52'),
(682, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:11:53'),
(683, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:13:20'),
(684, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:15:31'),
(685, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:16:05'),
(686, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:16:47'),
(687, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:21:53'),
(688, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:28:01'),
(689, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:30:29'),
(690, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:31:46'),
(691, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:34:22'),
(692, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:39:30'),
(693, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:40:47'),
(694, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:41:59'),
(695, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:42:31'),
(696, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:48:19'),
(697, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:48:51'),
(698, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:50:07'),
(699, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:53:47'),
(700, 44, 'Login Failed', 'Password does not match.', '2025-04-20 13:54:26'),
(701, 44, 'Login Failed', 'Password does not match.', '2025-04-20 13:54:26'),
(702, 44, 'Login Success', 'Successfully logged in', '2025-04-20 13:54:28'),
(703, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:26:49'),
(704, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:29:11'),
(705, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:33:25'),
(706, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:49:13'),
(707, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:50:28'),
(708, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:52:04'),
(709, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:53:27'),
(710, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:54:29'),
(711, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:57:17'),
(712, 44, 'Login Success', 'Successfully logged in', '2025-04-20 23:59:10'),
(713, 44, 'Login Success', 'Successfully logged in', '2025-04-21 00:41:40'),
(714, 44, 'Login Success', 'Successfully logged in', '2025-04-21 02:36:53'),
(715, 45, 'Login Success', 'Successfully logged in', '2025-04-21 03:58:05'),
(716, 44, 'Login Success', 'Successfully logged in', '2025-04-21 05:35:49'),
(717, 45, 'Login Failed', 'Password does not match.', '2025-04-21 05:40:45'),
(718, 45, 'Login Success', 'Successfully logged in', '2025-04-21 05:40:48'),
(719, 44, 'Login Success', 'Successfully logged in', '2025-04-21 05:41:01'),
(720, 56, 'Login Success', 'Successfully logged in', '2025-04-21 05:41:52'),
(721, 44, 'Login Success', 'Successfully logged in', '2025-04-21 06:10:29'),
(722, 45, 'Login Success', 'Successfully logged in', '2025-04-21 06:17:33'),
(723, 44, 'Login Success', 'Successfully logged in', '2025-04-21 06:18:21'),
(724, 44, 'Login Success', 'Successfully logged in', '2025-04-23 07:58:42'),
(725, 44, 'Login Success', 'Successfully logged in', '2025-04-27 13:53:53'),
(726, 44, 'Login Success', 'Successfully logged in', '2025-04-27 23:55:00'),
(727, 44, 'Login Failed', 'Password does not match.', '2025-04-28 06:50:03'),
(728, 44, 'Login Success', 'Successfully logged in', '2025-04-28 06:50:16'),
(729, 44, 'Login Failed', 'Password does not match.', '2025-04-28 06:52:26'),
(730, 44, 'Login Success', 'Successfully logged in', '2025-04-28 06:52:33'),
(731, 44, 'Login Success', 'Successfully logged in', '2025-04-28 07:41:56'),
(732, 44, 'Login Success', 'Successfully logged in', '2025-04-28 07:55:35'),
(733, 44, 'Login Success', 'Successfully logged in', '2025-04-28 08:51:58'),
(734, 45, 'Login Failed', 'Password does not match.', '2025-04-30 02:01:34'),
(735, 45, 'Login Failed', 'Password does not match.', '2025-04-30 02:01:37'),
(736, 45, 'Login Failed', 'Password does not match.', '2025-04-30 02:01:39'),
(737, 45, 'Login Success', 'Successfully logged in', '2025-04-30 02:01:41'),
(738, 44, 'Login Failed', 'Password does not match.', '2025-04-30 08:07:04'),
(739, 44, 'Login Failed', 'Password does not match.', '2025-04-30 08:07:05'),
(740, 44, 'Login Success', 'Successfully logged in', '2025-04-30 08:07:07'),
(741, 45, 'Login Success', 'Successfully logged in', '2025-04-30 08:10:07'),
(742, 44, 'Login Success', 'Successfully logged in', '2025-04-30 08:10:28'),
(743, 45, 'Login Success', 'Successfully logged in', '2025-04-30 08:11:26'),
(744, 44, 'Login Success', 'Successfully logged in', '2025-04-30 08:15:21'),
(745, 45, 'Login Success', 'Successfully logged in', '2025-04-30 08:41:29'),
(746, 44, 'Login Success', 'Successfully logged in', '2025-04-30 09:02:39'),
(747, 45, 'Login Success', 'Successfully logged in', '2025-04-30 09:03:12'),
(748, 45, 'Login Success', 'Successfully logged in', '2025-05-01 14:58:26'),
(749, 44, 'Login Success', 'Successfully logged in', '2025-05-01 14:59:41'),
(750, 45, 'Login Success', 'Successfully logged in', '2025-05-01 15:00:31'),
(751, 44, 'Login Success', 'Successfully logged in', '2025-05-01 15:01:54'),
(752, 45, 'Login Success', 'Successfully logged in', '2025-05-01 15:03:09'),
(753, 44, 'Login Success', 'Successfully logged in', '2025-05-01 15:06:47'),
(754, 45, 'Login Success', 'Successfully logged in', '2025-05-01 15:07:48'),
(755, 44, 'Login Success', 'Successfully logged in', '2025-05-01 15:09:54'),
(756, 45, 'Login Success', 'Successfully logged in', '2025-05-01 15:10:18'),
(757, 44, 'Login Success', 'Successfully logged in', '2025-05-01 15:14:27'),
(758, 45, 'Login Success', 'Successfully logged in', '2025-05-01 15:19:58'),
(759, 55, 'Login Failed', 'Account is not active yet.', '2025-05-01 15:20:12'),
(760, 55, 'Login Failed', 'Account is not active yet.', '2025-05-01 15:20:13'),
(761, 44, 'Login Failed', 'Password does not match.', '2025-05-01 15:20:21'),
(762, 44, 'Login Failed', 'Password does not match.', '2025-05-01 15:21:43'),
(763, 44, 'Login Success', 'Successfully logged in', '2025-05-01 15:21:45'),
(764, 45, 'Login Success', 'Successfully logged in', '2025-05-01 16:04:59'),
(765, 44, 'Login Success', 'Successfully logged in', '2025-05-01 16:14:56'),
(766, 45, 'Login Success', 'Successfully logged in', '2025-05-01 16:17:00'),
(767, 44, 'Login Success', 'Successfully logged in', '2025-05-01 16:17:21'),
(768, 56, 'Login Success', 'Successfully logged in', '2025-05-01 16:21:00'),
(769, 45, 'Login Failed', 'Password does not match.', '2025-05-01 16:21:19'),
(770, 45, 'Login Failed', 'Password does not match.', '2025-05-01 16:21:20'),
(771, 45, 'Login Success', 'Successfully logged in', '2025-05-01 16:21:22'),
(772, 56, 'Login Success', 'Successfully logged in', '2025-05-01 16:21:29'),
(773, 44, 'Login Success', 'Successfully logged in', '2025-05-01 16:38:50'),
(774, 56, 'Login Success', 'Successfully logged in', '2025-05-01 16:39:15'),
(775, 44, 'Login Success', 'Successfully logged in', '2025-05-01 16:42:59'),
(776, 56, 'Login Success', 'Successfully logged in', '2025-05-01 16:57:19'),
(777, 44, 'Login Success', 'Successfully logged in', '2025-05-01 16:57:29'),
(778, 45, 'Login Success', 'Successfully logged in', '2025-05-01 16:57:34'),
(779, 45, 'Login Success', 'Successfully logged in', '2025-05-01 16:57:54'),
(780, 56, 'Login Success', 'Successfully logged in', '2025-05-01 16:58:04'),
(781, 45, 'Login Success', 'Successfully logged in', '2025-05-01 17:54:17'),
(782, 56, 'Login Success', 'Successfully logged in', '2025-05-01 18:06:54'),
(783, 44, 'Login Success', 'Successfully logged in', '2025-05-01 18:07:03'),
(784, 45, 'Login Success', 'Successfully logged in', '2025-05-01 18:07:15'),
(785, 56, 'Login Success', 'Successfully logged in', '2025-05-01 18:13:39'),
(786, 55, 'Login Failed', 'Account is not active yet.', '2025-05-01 18:14:01'),
(787, 55, 'Login Failed', 'Account is not active yet.', '2025-05-01 18:14:02'),
(788, 45, 'Login Success', 'Successfully logged in', '2025-05-01 18:14:07'),
(789, 44, 'Login Failed', 'Password does not match.', '2025-05-01 18:14:34'),
(790, 44, 'Login Failed', 'Password does not match.', '2025-05-01 18:14:35'),
(791, 44, 'Login Success', 'Successfully logged in', '2025-05-01 18:14:37'),
(792, 45, 'Login Success', 'Successfully logged in', '2025-05-01 18:14:52'),
(793, 44, 'Login Success', 'Successfully logged in', '2025-05-01 18:18:20'),
(794, 45, 'Login Success', 'Successfully logged in', '2025-05-01 18:18:36'),
(795, 44, 'Login Success', 'Successfully logged in', '2025-05-01 18:21:39'),
(796, 55, 'Login Success', 'Successfully logged in', '2025-05-01 18:22:39'),
(797, 44, 'Login Success', 'Successfully logged in', '2025-05-01 18:37:50'),
(798, 55, 'Login Success', 'Successfully logged in', '2025-05-01 18:38:23'),
(799, 44, 'Login Success', 'Successfully logged in', '2025-05-01 18:55:15'),
(800, 45, 'Login Success', 'Successfully logged in', '2025-05-01 18:56:20'),
(801, 55, 'Login Success', 'Successfully logged in', '2025-05-01 18:56:31'),
(802, 44, 'Login Success', 'Successfully logged in', '2025-05-02 02:11:15'),
(803, 48, 'Login Success', 'Successfully logged in', '2025-05-02 02:11:29'),
(804, 44, 'Login Success', 'Successfully logged in', '2025-05-02 02:11:59'),
(805, 45, 'Login Success', 'Successfully logged in', '2025-05-02 02:13:29'),
(806, 44, 'Login Success', 'Successfully logged in', '2025-05-02 02:15:59'),
(807, 55, 'Login Success', 'Successfully logged in', '2025-05-02 02:24:21'),
(808, 45, 'Login Success', 'Successfully logged in', '2025-05-02 02:24:29'),
(809, 44, 'Login Success', 'Successfully logged in', '2025-05-02 02:27:53'),
(810, 55, 'Login Success', 'Successfully logged in', '2025-05-02 06:25:13'),
(811, 45, 'Login Success', 'Successfully logged in', '2025-05-02 06:25:47'),
(812, 44, 'Login Success', 'Successfully logged in', '2025-05-02 06:45:43'),
(813, 48, 'Login Success', 'Successfully logged in', '2025-05-03 13:48:20'),
(814, 44, 'Login Success', 'Successfully logged in', '2025-05-03 13:50:37'),
(815, 44, 'Login Success', 'Successfully logged in', '2025-05-08 23:58:20'),
(816, 44, 'Logout', 'User logged out', '2025-05-08 23:59:04'),
(817, 60, 'Register', 'User successully registered.', '2025-05-08 23:59:56'),
(818, 44, 'Login Success', 'Successfully logged in', '2025-05-09 00:00:09'),
(819, 44, 'Profile Edit', 'User updated profile information | OLD | First Name: Mark Jay, Middle Name: Sarzosas, Last Name: Cortes | NEW | First Name: Mark Jay, Middle Name: Sarzosa, Last Name: Cortes', '2025-05-09 00:22:29'),
(820, 44, 'Profile Edit', 'User updated profile information, changed:Nothing | First Name: Mark Jay Vincent | Middle Name: Sarzosa | Last Name: Cortes', '2025-05-09 00:34:33'),
(821, 44, 'Profile Edit', 'User updated profile information, changed: Nothing | Last Name: Cortez', '2025-05-09 00:37:00'),
(822, 44, 'Profile Edit', 'User updated profile information, changed: Nothing', '2025-05-09 00:40:17'),
(823, 44, 'Profile Edit', 'User updated profile information, changed: | First Name: Mark Jay  | Last Name: Cortes', '2025-05-09 00:44:44'),
(824, 44, 'Logout', 'User logged out', '2025-05-09 03:23:33'),
(825, 44, 'Login Success', 'Successfully logged in', '2025-05-09 03:23:36'),
(826, 44, 'Logout', 'User logged out', '2025-05-09 03:23:38'),
(827, 45, 'Login Success', 'Successfully logged in', '2025-05-09 03:23:42'),
(828, 0, 'Logout', 'User logged out', '2025-05-09 03:28:31'),
(829, 44, 'Login Success', 'Successfully logged in', '2025-05-09 03:28:33'),
(830, 44, 'Logout', 'User logged out', '2025-05-10 01:13:46'),
(831, 48, 'Login Success', 'Successfully logged in', '2025-05-10 01:13:50'),
(832, 0, 'Logout', 'User logged out', '2025-05-10 01:14:12'),
(833, 44, 'Login Failed', 'Password does not match.', '2025-05-10 01:16:57'),
(834, 44, 'Login Failed', 'Password does not match.', '2025-05-10 01:16:58'),
(835, 44, 'Login Failed', 'Password does not match.', '2025-05-10 01:17:00'),
(836, 44, 'Login Success', 'Successfully logged in', '2025-05-10 01:17:02'),
(837, 44, 'Logout', 'User logged out', '2025-05-10 01:17:24'),
(838, 48, 'Login Success', 'Successfully logged in', '2025-05-10 01:17:27'),
(839, 0, 'Logout', 'User logged out', '2025-05-10 01:17:48'),
(840, 44, 'Login Success', 'Successfully logged in', '2025-05-10 01:17:53'),
(841, 44, 'Logout', 'User logged out', '2025-05-10 01:20:27'),
(842, 44, 'Login Success', 'Successfully logged in', '2025-05-10 01:20:33'),
(843, 44, 'Logout', 'User logged out', '2025-05-10 01:20:36'),
(844, 48, 'Login Success', 'Successfully logged in', '2025-05-10 01:20:39'),
(845, 48, 'Logout', 'User logged out', '2025-05-10 01:21:12'),
(846, 44, 'Login Success', 'Successfully logged in', '2025-05-10 01:21:14'),
(847, 44, 'Add Project', 'Project added: IM207 Project', '2025-05-10 01:37:29'),
(848, 7, 'Edit Project', 'Updated project information, changed:  | Project Name: IM207 Project 2 | Description: adsfasdfdas | Due date: 07/09/2025 | Status ID: 2', '2025-05-10 01:37:50'),
(849, 7, 'Edit Project', 'Updated project information, changed:  | Project Name: IM207 Project  | Description: adsfasdfdas', '2025-05-10 01:38:38'),
(850, 44, 'Edit Project', 'Updated project information, changed:  | Description: adsfasdfdas | Status ID: 1', '2025-05-10 01:39:29'),
(851, 44, 'Edit Project', 'Updated project information, changed:  | Description: Test', '2025-05-10 01:39:57'),
(852, 44, 'Edit Project', 'Updated project information, changed:  | Project Name: IM207 Project 2 | Description: Test', '2025-05-10 01:40:10'),
(853, 44, 'Delete Project', 'Project deleted: Laravel Project', '2025-05-10 01:40:36');

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `date_created` date NOT NULL,
  `due_date` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `team_member_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `task`
--

INSERT INTO `task` (`id`, `name`, `description`, `date_created`, `due_date`, `user_id`, `team_member_id`, `project_id`, `status_id`) VALUES
(1, 'test task', 'test ', '2025-04-25', '2025-04-30', 44, NULL, 1, 2),
(5, 'LMS Task', 'Sample Desc', '2025-04-28', '2025-04-30', 44, 19, 3, 2),
(7, 'Another Task Name', 'This desc', '2025-04-30', '2025-05-10', 44, NULL, 1, 2),
(8, 'bleeeggghh', 'asdfasfasdf', '2025-04-30', '2025-05-07', 44, NULL, 1, 1),
(9, 'test', 'asa', '2025-05-02', '2025-06-06', 44, NULL, 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `task_status`
--

CREATE TABLE `task_status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `task_status`
--

INSERT INTO `task_status` (`id`, `name`) VALUES
(1, 'not completed'),
(2, 'completed');

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `project_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team`
--

INSERT INTO `team` (`id`, `name`, `project_id`) VALUES
(10, 'team 2', 5),
(23, 'Team Aerodite', 3);

-- --------------------------------------------------------

--
-- Table structure for table `team_log`
--

CREATE TABLE `team_log` (
  `id` int(11) NOT NULL,
  `action` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `team_member_id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team_member`
--

CREATE TABLE `team_member` (
  `id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL DEFAULT 1,
  `status_id` int(11) NOT NULL DEFAULT 2
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team_member`
--

INSERT INTO `team_member` (`id`, `team_id`, `user_id`, `role_id`, `status_id`) VALUES
(15, 23, 57, 1, 2),
(16, 23, 55, 1, 2),
(17, 23, 49, 1, 2),
(18, 23, 46, 1, 2),
(19, 23, 45, 2, 2),
(21, 10, 54, 1, 2),
(22, 23, 52, 1, 2),
(23, 23, 56, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `team_member_role`
--

CREATE TABLE `team_member_role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team_member_role`
--

INSERT INTO `team_member_role` (`id`, `name`) VALUES
(1, 'team member'),
(2, 'team leader');

-- --------------------------------------------------------

--
-- Table structure for table `team_member_status`
--

CREATE TABLE `team_member_status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `team_member_status`
--

INSERT INTO `team_member_status` (`id`, `name`) VALUES
(1, 'unavailable'),
(2, 'available');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  `role_id` int(11) DEFAULT 2,
  `status_id` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone_number`, `username`, `salt`, `password`, `secret_key`, `role_id`, `status_id`) VALUES
(44, 'Mark Jay ', 'Sarzosa', 'Cortes', 'markj@gmail.com', '09574463982', 'admin', 'KDYvivG0hSljZBZPfIaK/A==', '256f1360c83bb1102d9c4e8a2c39e6d66f5443a0fa6efb8fb484032d97c7daf1', 'NSLWJIUCKH3ZB3VP', 1, 2),
(45, 'Jane', 'Devora', 'Smith', 'jane.smith@gmail.com', '09856697059', 'janesmith', '5SVh/IqSe/+b0Hq7XmdKRQ==', '2f3edade16e0a5d43e9ba8aeb9409957c6a01cec4dcdfd2941a5b3a3b645f348', 'NANHNLDR4T23GF52', 2, 2),
(46, 'Aljon', '', 'Paragoso', 'aljon.paragoso@gmail.com', '09452213265', 'aljon', 'NOV6cAYQD/BQyaIMVIBywg==', '490fd32237ea889e802f623209642711118d3bf04b3da6dc5910d5a92469e1e2', '', 2, 2),
(47, 'Jan Klyde', '', 'Bulagao', 'jan.bulagao@gmail.com', '09543321652', 'janklyde', 'BzQhRU6gcLUMGKjbuBTUYg==', 'ac7eac4f902ed6ed50f45995fb0ee421b06544e1ddc5bc69dd009fcf812f0c71', '', 4, 2),
(48, 'Rasheed', '', 'Tapales', 'rasheed@gmail.com', '09512236526', 'rasheed', '8yPOxTTWI6SyCgF0f5xWAQ==', '20d2acaff05185c6b0e81175bb7da32e1ce86eac78cb68c4b202af0723638963', '', 5, 2),
(49, 'Axcee', '', 'Cabusas', 'axcee@gmail.com', '09366695326', 'axcee', 'b90hNfAiNghPHtgVubut2Q==', '09d159469ef04cbdc89f1c4bf8f15d3ca02434278fcdcfe6731caa9630ee1108', '', 2, 2),
(52, 'Jerame', '', 'Abing', 'abing@gmail.com', '09995869948', 'abing', '47Kqnf10dZChUb61Du0ZRg==', 'd3509ea4d31128813966c6a6f0b0a121ede09a2a092916a72d446073ba930fbb', '', 2, 2),
(54, 'Jerkean', '', 'Gabrina', 'jerkean@gmail.com', '+639456231125', 'jerkean', 'g/ijzYC78+saQF2yowAr0A==', '6455887fa925f89f81bb4f4f8bafba3007adec9353921035a6310afdafd11759', '', 2, 2),
(55, 'Joseph', '', 'Arambala', 'joseph@gmail.com', '09985743398', 'joseph', 'CVOr2+pbovf9QJ2R4zXhaQ==', '670314e892b48b056d56eb4943aa7bf133f3e5121ecede90efb73ebd0374d9a1', 'F43CACLQ6MJLP2PH', 2, 2),
(56, 'Mark Joseph', '', 'Canedo', 'markcanedo@gmail.com', '09854493822', 'markcanedo', 'FluJSq6qHsq5OIYZU93pIA==', 'bebaf46405df72b8c37134fb54b8f8d8f3c9acffff6b0cebf7314f6af4caf85d', '', 2, 2),
(57, 'Mark Christian', '', 'Canedo', 'christiancanedo@gmail.com', '09584494033', 'christian', '04g912E+uaYcvJNNRb/dTQ==', '4a0d5c85b5fed0ed109894160df6d42294d3561749cd5af6f74447730b69f2e9', 'PANRKPTGODGETZR6', 2, 1),
(58, 'mark', '', 'mark', 'markjay@gmail.com', '09857389982', 'markj', 'lVTpzVpWlF7bKAKBP7V8Hg==', 'b8aacf7c1eb6e7867197bd10112b1ad942379fcac378720a3078ab8778630e8d', 'EIR6ZU2LF7RD52S5', 2, 1),
(59, 'Arl', '', 'Sison', 'arl@gmail.com', '09586685567', 'arl', 'WwOxvlvknzIG4iiTXjHjDA==', '60448b23233b124403498dbdd25107dab6438f0a67e82202e46bc1b602ed3f4c', NULL, 2, 1),
(60, 'ahlde', '', 'geonzon', 'ahlde@gmail.com', '09586695567', 'ahlde', 'f2XZyKlAa36GBebv4yLF+g==', 'c44130fa860b0f8de554a665e49db733c97a47efa1dccab3e02b57acaa22b1ae', NULL, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_image`
--

CREATE TABLE `user_image` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `image_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_image`
--

INSERT INTO `user_image` (`id`, `user_id`, `image_path`) VALUES
(7, 44, 'pfp\\user_admin.jpg'),
(8, 45, 'pfp\\user_janesmith.png'),
(10, 46, 'pfp\\user_aljon.jpg'),
(11, 47, 'pfp\\user_janklyde.jpg'),
(12, 48, 'pfp\\user_rasheed.png'),
(13, 49, 'pfp\\user_axcee.jpg'),
(14, 54, 'pfp\\user_jerkean.png'),
(15, 56, 'pfp\\user_markcanedo.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id`, `name`) VALUES
(1, 'admin'),
(5, 'project manager'),
(2, 'standard'),
(4, 'team manager');

-- --------------------------------------------------------

--
-- Table structure for table `user_status`
--

CREATE TABLE `user_status` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_status`
--

INSERT INTO `user_status` (`id`, `name`) VALUES
(2, 'active'),
(1, 'inactive');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`),
  ADD KEY `project_manager_id` (`user_id`),
  ADD KEY `status_id` (`status_id`);

--
-- Indexes for table `project_status`
--
ALTER TABLE `project_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `system_log`
--
ALTER TABLE `system_log`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_by` (`user_id`),
  ADD KEY `assigned_to` (`team_member_id`),
  ADD KEY `assigned_project` (`project_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `project_id` (`project_id`);

--
-- Indexes for table `task_status`
--
ALTER TABLE `task_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`id`),
  ADD KEY `project_id` (`project_id`);

--
-- Indexes for table `team_log`
--
ALTER TABLE `team_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team_member_id` (`team_member_id`),
  ADD KEY `team_id` (`team_id`);

--
-- Indexes for table `team_member`
--
ALTER TABLE `team_member`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team_id` (`team_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `status_id` (`status_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `role_id_2` (`role_id`);

--
-- Indexes for table `team_member_role`
--
ALTER TABLE `team_member_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `team_member_status`
--
ALTER TABLE `team_member_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `status_id` (`status_id`);

--
-- Indexes for table `user_image`
--
ALTER TABLE `user_image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `role` (`name`);

--
-- Indexes for table `user_status`
--
ALTER TABLE `user_status`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `status` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `project_status`
--
ALTER TABLE `project_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `system_log`
--
ALTER TABLE `system_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=854;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `task_status`
--
ALTER TABLE `task_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `team`
--
ALTER TABLE `team`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `team_log`
--
ALTER TABLE `team_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `team_member`
--
ALTER TABLE `team_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `team_member_role`
--
ALTER TABLE `team_member_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=61;

--
-- AUTO_INCREMENT for table `user_image`
--
ALTER TABLE `user_image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `user_status`
--
ALTER TABLE `user_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `project_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `project_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `project_status` (`id`);

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`team_member_id`) REFERENCES `team_member` (`id`),
  ADD CONSTRAINT `task_ibfk_3` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  ADD CONSTRAINT `task_ibfk_4` FOREIGN KEY (`status_id`) REFERENCES `task_status` (`id`);

--
-- Constraints for table `team`
--
ALTER TABLE `team`
  ADD CONSTRAINT `team_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`);

--
-- Constraints for table `team_log`
--
ALTER TABLE `team_log`
  ADD CONSTRAINT `team_log_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `team_log_ibfk_2` FOREIGN KEY (`team_member_id`) REFERENCES `team_member` (`id`);

--
-- Constraints for table `team_member`
--
ALTER TABLE `team_member`
  ADD CONSTRAINT `team_member_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `team_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `team_member_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `team_member_status` (`id`),
  ADD CONSTRAINT `team_member_ibfk_4` FOREIGN KEY (`role_id`) REFERENCES `team_member_role` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `user_status` (`id`);

--
-- Constraints for table `user_image`
--
ALTER TABLE `user_image`
  ADD CONSTRAINT `user_image_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

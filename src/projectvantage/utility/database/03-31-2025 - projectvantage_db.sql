-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2025 at 02:33 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

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
  `name` text NOT NULL,
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
(1, 'Test Project', 'This is a test project', '2025-03-29', '2025-03-12', 44, 2),
(4, 'test', 'asdfdfdadfsf', '2025-03-31', '2025-04-02', 44, 1);

-- --------------------------------------------------------

--
-- Table structure for table `project_status`
--

CREATE TABLE `project_status` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `project_status`
--

INSERT INTO `project_status` (`id`, `name`) VALUES
(1, 'not completed'),
(2, 'completed');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
(1, 'admin'),
(2, 'team member'),
(3, 'team leader'),
(4, 'team manager'),
(5, 'project manager');

-- --------------------------------------------------------

--
-- Table structure for table `system_log`
--

CREATE TABLE `system_log` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` text NOT NULL,
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
(369, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:04:34'),
(370, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:06:17'),
(371, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:20:52'),
(372, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:33:43'),
(373, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:35:07'),
(374, 44, 'Login Failed', 'Password does not match.', '2025-03-31 11:35:28'),
(375, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:35:31'),
(376, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:36:38'),
(377, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:37:02'),
(378, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:53:27'),
(379, 44, 'Login Success', 'Successfully logged in', '2025-03-31 11:56:14'),
(380, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:05:40'),
(381, 44, 'Login Failed', 'Password does not match.', '2025-03-31 12:06:18'),
(382, 44, 'Login Failed', 'Password does not match.', '2025-03-31 12:06:20'),
(383, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:06:22'),
(384, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:17:37'),
(385, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:21:07'),
(386, 44, 'Login Failed', 'Password does not match.', '2025-03-31 12:21:59'),
(387, 44, 'Login Failed', 'Password does not match.', '2025-03-31 12:22:00'),
(388, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:22:02'),
(389, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:22:27'),
(390, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:26:40'),
(391, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:27:26'),
(392, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:29:46'),
(393, 44, 'Login Success', 'Successfully logged in', '2025-03-31 12:30:24');

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `task_name` text NOT NULL,
  `description` text NOT NULL,
  `date_created` date NOT NULL,
  `due_date` date NOT NULL,
  `user_id` int(11) NOT NULL,
  `team_member_id` int(11) DEFAULT NULL,
  `project_id` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `task_status`
--

CREATE TABLE `task_status` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `task_status`
--

INSERT INTO `task_status` (`id`, `name`) VALUES
(1, 'not started'),
(2, 'in progress'),
(3, 'completed');

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `team_name` text NOT NULL,
  `description` text NOT NULL,
  `project_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team_member`
--

CREATE TABLE `team_member` (
  `id` int(11) NOT NULL,
  `team_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `status_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team_member_status`
--

CREATE TABLE `team_member_status` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
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
  `first_name` text NOT NULL,
  `middle_name` text DEFAULT NULL,
  `last_name` text NOT NULL,
  `email` text NOT NULL,
  `phone_number` text NOT NULL,
  `username` text NOT NULL,
  `salt` text NOT NULL,
  `password` text NOT NULL,
  `secret_key` text NOT NULL,
  `role_id` int(11) DEFAULT 2,
  `status_id` int(11) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone_number`, `username`, `salt`, `password`, `secret_key`, `role_id`, `status_id`) VALUES
(44, 'Mark Jay', 'Sarzosas', 'Cortes', 'markj@gmail.com', '09574463982', 'admin', 'KDYvivG0hSljZBZPfIaK/A==', '256f1360c83bb1102d9c4e8a2c39e6d66f5443a0fa6efb8fb484032d97c7daf1', 'NSLWJIUCKH3ZB3VP', 1, 2),
(45, 'Jane', 'Devora', 'Smith', 'jane.smith@gmail.com', '09856697059', 'janesmith', '5SVh/IqSe/+b0Hq7XmdKRQ==', '2f3edade16e0a5d43e9ba8aeb9409957c6a01cec4dcdfd2941a5b3a3b645f348', 'NANHNLDR4T23GF52', 2, 2),
(46, 'Aljon', '', 'Paragoso', 'aljon.paragoso@gmail.com', '09452213265', 'aljon', 'NOV6cAYQD/BQyaIMVIBywg==', '490fd32237ea889e802f623209642711118d3bf04b3da6dc5910d5a92469e1e2', '', 3, 2),
(47, 'Jan Klyde', '', 'Bulagao', 'jan.bulagao@gmail.com', '09543321652', 'janklyde', 'BzQhRU6gcLUMGKjbuBTUYg==', 'ac7eac4f902ed6ed50f45995fb0ee421b06544e1ddc5bc69dd009fcf812f0c71', '', 4, 2),
(48, 'Rasheed', '', 'Tapales', 'rasheed@gmail.com', '09512236526', 'rasheed', '8yPOxTTWI6SyCgF0f5xWAQ==', '20d2acaff05185c6b0e81175bb7da32e1ce86eac78cb68c4b202af0723638963', '', 5, 2),
(49, 'Axcee', '', 'Cabusas', 'axcee@gmail.com', '09366695326', 'axcee', 'b90hNfAiNghPHtgVubut2Q==', '09d159469ef04cbdc89f1c4bf8f15d3ca02434278fcdcfe6731caa9630ee1108', '', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_image`
--

CREATE TABLE `user_image` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `image_path` text NOT NULL
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
(13, 49, 'pfp\\user_axcee.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `user_status`
--

CREATE TABLE `user_status` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user_status`
--

INSERT INTO `user_status` (`id`, `name`) VALUES
(1, 'inactive'),
(2, 'active');

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
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `role` (`name`) USING HASH;

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
-- Indexes for table `team_member`
--
ALTER TABLE `team_member`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team_id` (`team_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `status_id` (`status_id`);

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
-- Indexes for table `user_status`
--
ALTER TABLE `user_status`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `status` (`name`) USING HASH;

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `project_status`
--
ALTER TABLE `project_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `system_log`
--
ALTER TABLE `system_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=394;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `task_status`
--
ALTER TABLE `task_status`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `team`
--
ALTER TABLE `team`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `team_member`
--
ALTER TABLE `team_member`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `user_image`
--
ALTER TABLE `user_image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

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
-- Constraints for table `team_member`
--
ALTER TABLE `team_member`
  ADD CONSTRAINT `team_member_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `team_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `team_member_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `team_member_status` (`id`),
  ADD CONSTRAINT `team_member_ibfk_4` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`status_id`) REFERENCES `user_status` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

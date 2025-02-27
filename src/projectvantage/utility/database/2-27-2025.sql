-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 27, 2025 at 01:30 PM
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
  `project_name` text NOT NULL,
  `description` text NOT NULL,
  `date_created` date NOT NULL,
  `due_date` date NOT NULL,
  `project_manager_id` int(11) NOT NULL,
  `status` text NOT NULL DEFAULT 'planned'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

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
  `project_manager_id` int(11) NOT NULL,
  `team_member_id` int(11) DEFAULT NULL,
  `assigned_project` int(11) DEFAULT NULL,
  `status` text NOT NULL DEFAULT 'not started'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `team_name` text NOT NULL,
  `team_member_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `team_member`
--

CREATE TABLE `team_member` (
  `id` int(11) NOT NULL,
  `member_name` text NOT NULL,
  `team_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role` text NOT NULL,
  `status` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `first_name` text NOT NULL,
  `last_name` text NOT NULL,
  `email` text NOT NULL,
  `phone_number` text NOT NULL,
  `username` text NOT NULL,
  `password` text NOT NULL,
  `role` text NOT NULL DEFAULT 'team_member',
  `status` text NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `phone_number`, `username`, `password`, `role`, `status`) VALUES
(1, 'Mark Jay Vincent', 'Cortes', 'markjayvincentcortes@gmail.com', '+639995424479', 'ScepticMaku', '12345678', 'admin', 'active'),
(2, 'John', 'Doe', 'john.doe@example.com', '1234567890', 'johndoe', 'password123', 'admin', 'active'),
(3, 'Jane', 'Smith', 'jane.smith@example.com', '9876543210', 'janesmith', 'securepass', 'team member', 'active'),
(4, 'Michael', 'Johnson', 'michael.johnson@example.com', '1231231234', 'michaelj', 'mypass456', 'user', 'active'),
(5, 'Emily', 'Williams', 'emily.williams@example.com', '4564564567', 'emilyw', 'emilysecure', 'moderator', 'active'),
(6, 'David', 'Brown', 'david.brown@example.com', '7897897890', 'davidbrown', 'pass789', 'user', 'active'),
(7, 'Sarah', 'Miller', 'sarah.miller@example.com', '3213213210', 'sarahm', 'mypassword', 'admin', 'active'),
(8, 'Robert', 'Davis', 'robert.davis@example.com', '6546546543', 'robertd', 'robertpass', 'user', 'active'),
(9, 'Jessica', 'Garcia', 'jessica.garcia@example.com', '9879879876', 'jessicag', 'jessicasecure', 'moderator', 'active'),
(10, 'Daniel', 'Martinez', 'daniel.martinez@example.com', '7417417412', 'danielm', 'danielpass', 'user', 'active'),
(11, 'Sophia', 'Hernandez', 'sophia.hernandez@example.com', '8528528523', 'sophiah', 'sophiasecure', 'admin', 'active');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`),
  ADD KEY `project_manager_id` (`project_manager_id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `created_by` (`project_manager_id`),
  ADD KEY `assigned_to` (`team_member_id`),
  ADD KEY `assigned_project` (`assigned_project`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`id`),
  ADD KEY `project_id` (`project_id`),
  ADD KEY `team_member_id` (`team_member_id`);

--
-- Indexes for table `team_member`
--
ALTER TABLE `team_member`
  ADD PRIMARY KEY (`id`),
  ADD KEY `team_id` (`team_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `project`
--
ALTER TABLE `project`
  ADD CONSTRAINT `project_ibfk_1` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`);

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `task_ibfk_1` FOREIGN KEY (`project_manager_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `task_ibfk_2` FOREIGN KEY (`team_member_id`) REFERENCES `team_member` (`id`),
  ADD CONSTRAINT `task_ibfk_3` FOREIGN KEY (`assigned_project`) REFERENCES `project` (`id`);

--
-- Constraints for table `team`
--
ALTER TABLE `team`
  ADD CONSTRAINT `team_ibfk_1` FOREIGN KEY (`team_member_id`) REFERENCES `team_member` (`id`),
  ADD CONSTRAINT `team_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`);

--
-- Constraints for table `team_member`
--
ALTER TABLE `team_member`
  ADD CONSTRAINT `team_member_ibfk_1` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
  ADD CONSTRAINT `team_member_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

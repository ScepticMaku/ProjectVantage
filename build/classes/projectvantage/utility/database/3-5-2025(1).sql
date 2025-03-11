-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2025 at 10:00 AM
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
  `status` text NOT NULL DEFAULT 'active',
  `middle_name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `phone_number`, `username`, `password`, `role`, `status`, `middle_name`) VALUES
(1, 'Mark Jay Vincent', 'Cortes', 'markjayvincentcortes@gmail.com', '+639995424479', 'ScepticMaku', '12345678', 'admin', 'active', ''),
(2, 'John', 'Doe', 'john.doe@example.com', '1234567890', 'johndoe', 'password123', 'admin', 'active', ''),
(3, 'Jane', 'Smith', 'jane.smith@example.com', '9876543210', 'janesmith', 'securepass', 'team member', 'active', ''),
(4, 'Michael', 'Johnson', 'michael.johnson@example.com', '1231231234', 'michaelj', 'mypass456', 'user', 'active', ''),
(5, 'Emily', 'Williams', 'emily.williams@example.com', '4564564567', 'emilyw', 'emilysecure', 'moderator', 'active', ''),
(6, 'David', 'Brown', 'david.brown@example.com', '7897897890', 'davidbrown', 'pass789', 'user', 'active', ''),
(7, 'Sarah', 'Miller', 'sarah.miller@example.com', '3213213210', 'sarahm', 'mypassword', 'admin', 'active', ''),
(8, 'Robert', 'Davis', 'robert.davis@example.com', '6546546543', 'robertd', 'robertpass', 'user', 'active', ''),
(9, 'Jessica', 'Garcia', 'jessica.garcia@example.com', '9879879876', 'jessicag', 'jessicasecure', 'moderator', 'active', ''),
(10, 'Daniel', 'Martinez', 'daniel.martinez@example.com', '7417417412', 'danielm', 'danielpass', 'user', 'active', ''),
(11, 'Sophia', 'Hernandez', 'sophia.hernandez@example.com', '8528528523', 'sophiah', 'sophiasecure', 'admin', 'active', ''),
(15, 'Jane', 'Doe', 'janedoe@email.com', '9995685211', 'janedoe', '12345678', 'team member', 'active', ''),
(16, 'test', 'test', 'test@scc.edu.ph', '0945454545', 'test', 'testtest', 'moderator', 'active', 'test');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

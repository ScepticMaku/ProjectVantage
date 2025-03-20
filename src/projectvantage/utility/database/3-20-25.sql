-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2025 at 11:17 AM
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
  `middle_name` text DEFAULT NULL,
  `last_name` text NOT NULL,
  `email` text NOT NULL,
  `phone_number` text NOT NULL,
  `username` text NOT NULL,
  `salt` text NOT NULL,
  `password` text NOT NULL,
  `secret_key` text NOT NULL,
  `role` text NOT NULL DEFAULT 'team_member',
  `status` text NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone_number`, `username`, `salt`, `password`, `secret_key`, `role`, `status`) VALUES
(1, 'Mark', '', 'Cortes', 'test@gmail.com', '9996542215', 'admin', 'mKbyx+07bJd2S1V0gjUe5g==', 'bd02eeaadff76b0ba352e19816b477ec09e412a807d346fec8c4b75e79f6508a', '', 'admin', 'active'),
(19, 'Arl', 'Sibuyas', 'Sison', 'sison@email.com', '9995642231', 'arl', 'fIGZ1/PLVCMjjK2sGKRNlw==', '65e8d51cb9b880e993d5741095aab9eed3ac7acb459890fede381aef05319e4e', '', 'team member', 'active'),
(21, 'Juan', 'Dela Cruz', 'Santos', 'juan.santos@gmail.com', '9123456789', 'juansantos', 'nX0nvJ42gaNxbvMXdCTUJQ==', '717b31598916d368b72ec382031342a95b30d32640102916f8e37135f1642480', '', 'project manager', 'inactive'),
(22, 'Maria', 'Consuelo', 'Reyes', 'maria.reyes@gmail.com', '9234567890', 'mariareyes', 'lOC9KIHgjsFBL+NHTqqhAQ==', '51f401b0cdb591fa16d35f10c06d352a0188cc969a82e4d2edc5fe2bb9e694c9', '', 'team leader', 'inactive'),
(23, 'Carlo', 'Miguel', 'Rodriguez', 'carlo.rodriguez@gmail.com', '9345678901', 'carlorod', 'a+o/WjI7xGBGhJG4TOvAOQ==', '85365586bddcdf69b8f4469345b11921da04fee04b2f169ecce458bc6b9d861d', '', 'team member', 'inactive'),
(24, 'Angela', 'Faye', 'Bautista', 'angela.bautista@gmail.com', '9456789012', 'angelab', 'JI332KGgyr4YtXgELxhnxg==', 'bcd254a97c8bd790cd2148aea5b6f1bddf0219a4e67a939f14311fc88e9d3d8d', '', 'project manager', 'inactive'),
(25, 'Miguel', 'Antonio', 'Cruz', 'miguel.cruz@gmail.com', '9567890123', 'miguelc', '1LsPfmnGJdLaS+3P7Eb7RA==', '26a763105737a0710ce547ecbe498e87535e6800ec5bf43b80de24b3ae83771c', '', 'team manager', 'inactive'),
(26, 'Jessica', 'Louise', 'Mendoza', 'jessica.mendoza@gmail.com', '9678901234', 'jessicam', 'kRciac8O5KuRjId3yDCzIg==', 'bc2fee4e1b7b9b90ea5ccbebee82154c472ca1b11a613e87d5b2698ebac22d68', '', 'team member', 'inactive'),
(27, 'Patrick', 'Gabriel', 'Lim', 'patrick.lim@gmail.com', '9789012345', 'patrickl', 'c54JVfpg+BCsZcDXJFNrYQ==', '3054fe3dea8614dea6b65d803091329a97bc8bc069cab4aa7841dca33d206373', '', 'team leader', 'inactive'),
(28, 'Erika', 'Anne', 'Dizon', 'erika.dizon@gmail.com', '9890123456', 'erikad', 'XubxucxRXDTWBcCVdmkhIw==', 'cd9626c935f1f5dbf95db667c1dc0f2a8121d06fcb590a33fa3a697f73a6f3d8', '', 'project manager', 'inactive'),
(43, 'Jane', '', 'Doe', 'jan.edoe@gmail.com', '9556425564', 'janedoe', 'D+0YqsSOraw/hpiQuF6ogg==', '1eaf925630bdc62d0c8eb05bb72c250f7501ab86cd4f04e236bd47e31dbfa7a2', 'XH7OJHHEC6SBOSQ2', 'team member', 'active');

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
  ADD KEY `project_id` (`project_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

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

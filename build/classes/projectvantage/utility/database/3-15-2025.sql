-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 15, 2025 at 04:00 AM
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
  `middle_name` text DEFAULT NULL,
  `last_name` text NOT NULL,
  `email` text NOT NULL,
  `phone_number` text NOT NULL,
  `username` text NOT NULL,
  `salt` text NOT NULL,
  `password` text NOT NULL,
  `role` text NOT NULL DEFAULT 'team_member',
  `status` text NOT NULL DEFAULT 'active'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `first_name`, `middle_name`, `last_name`, `email`, `phone_number`, `username`, `salt`, `password`, `role`, `status`) VALUES
(1, 'Mark', '', 'Cortes', 'test@gmail.com', '9996542215', 'admin', 'mKbyx+07bJd2S1V0gjUe5g==', 'bd02eeaadff76b0ba352e19816b477ec09e412a807d346fec8c4b75e79f6508a', 'admin', 'active'),
(19, 'Arl', '', 'Sison', 'sison@email.com', '9995642231', 'arl', 'fIGZ1/PLVCMjjK2sGKRNlw==', '65e8d51cb9b880e993d5741095aab9eed3ac7acb459890fede381aef05319e4e', 'team member', 'active'),
(21, 'Juan', 'Dela Cruz', 'Santos', 'juan.santos@gmail.com', '9123456789', 'juansantos', 'nX0nvJ42gaNxbvMXdCTUJQ==', '717b31598916d368b72ec382031342a95b30d32640102916f8e37135f1642480', 'project manager', 'inactive'),
(22, 'Maria', 'Consuelo', 'Reyes', 'maria.reyes@gmail.com', '9234567890', 'mariareyes', 'lOC9KIHgjsFBL+NHTqqhAQ==', '51f401b0cdb591fa16d35f10c06d352a0188cc969a82e4d2edc5fe2bb9e694c9', 'team leader', 'inactive'),
(23, 'Carlo', 'Miguel', 'Rodriguez', 'carlo.rodriguez@gmail.com', '9345678901', 'carlorod', 'a+o/WjI7xGBGhJG4TOvAOQ==', '85365586bddcdf69b8f4469345b11921da04fee04b2f169ecce458bc6b9d861d', 'team member', 'inactive'),
(24, 'Angela', 'Faye', 'Bautista', 'angela.bautista@gmail.com', '9456789012', 'angelab', 'JI332KGgyr4YtXgELxhnxg==', 'bcd254a97c8bd790cd2148aea5b6f1bddf0219a4e67a939f14311fc88e9d3d8d', 'project manager', 'inactive'),
(25, 'Miguel', 'Antonio', 'Cruz', 'miguel.cruz@gmail.com', '9567890123', 'miguelc', '1LsPfmnGJdLaS+3P7Eb7RA==', '26a763105737a0710ce547ecbe498e87535e6800ec5bf43b80de24b3ae83771c', 'team manager', 'inactive'),
(26, 'Jessica', 'Louise', 'Mendoza', 'jessica.mendoza@gmail.com', '9678901234', 'jessicam', 'kRciac8O5KuRjId3yDCzIg==', 'bc2fee4e1b7b9b90ea5ccbebee82154c472ca1b11a613e87d5b2698ebac22d68', 'team member', 'inactive'),
(27, 'Patrick', 'Gabriel', 'Lim', 'patrick.lim@gmail.com', '9789012345', 'patrickl', 'c54JVfpg+BCsZcDXJFNrYQ==', '3054fe3dea8614dea6b65d803091329a97bc8bc069cab4aa7841dca33d206373', 'team leader', 'inactive'),
(28, 'Erika', 'Anne', 'Dizon', 'erika.dizon@gmail.com', '9890123456', 'erikad', 'XubxucxRXDTWBcCVdmkhIw==', 'cd9626c935f1f5dbf95db667c1dc0f2a8121d06fcb590a33fa3a697f73a6f3d8', 'project manager', 'inactive');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

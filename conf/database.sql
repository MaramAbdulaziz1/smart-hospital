-- Create the main database if it doesn't already exist
CREATE DATABASE IF NOT EXISTS `smart_hospital` CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- Switch to the smart_hospital database
USE `smart_hospital`;

-- ===============================
-- Table: User
-- Description: Stores general user information and login credentials.
-- ===============================
CREATE TABLE `User` (
                      `user_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                      `first_name` VARCHAR(50) NOT NULL,
                      `last_name` VARCHAR(50) NOT NULL,
                      `gender` ENUM('MALE', 'FEMALE', 'OTHER') NOT NULL,
                      `email` VARCHAR(100) NOT NULL,
                      `password` VARCHAR(255) NOT NULL,
                      `role` ENUM('PATIENT', 'DOCTOR', 'NURSE') NOT NULL,
                      `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                      `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      PRIMARY KEY (`user_id`),
                      UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: Patient
-- Description: Stores medical and demographic data specific to patients.
-- ===============================
CREATE TABLE `Patient` (
                         `user_id` CHAR(36) NOT NULL,
                         `patient_code` VARCHAR(20) NOT NULL,
                         `birth` DATE NOT NULL,
                         `height` DECIMAL(5,2) NOT NULL,
                         `weight` DECIMAL(5,2) NOT NULL,
                         `blood_type` ENUM('A','B','AB','O') NOT NULL,
                         `address` VARCHAR(255) NOT NULL,
                         `national_id` CHAR(9) NOT NULL,
                         `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                         `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`user_id`),
                         UNIQUE KEY `patient_code` (`patient_code`),
                         UNIQUE KEY `national_id` (`national_id`),
                         CONSTRAINT `Patient_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: Doctor
-- Description: Stores doctor-specific employment details and department.
-- ===============================
CREATE TABLE `Doctor` (
                        `user_id` CHAR(36) NOT NULL,
                        `department` ENUM('INTERNAL_MEDICINE','SURGERY','OBSTETRICS_AND_GYNECOLOGY','PEDIATRICS','ENT') NOT NULL,
                        `employee_id` CHAR(9) NOT NULL,
                        `location` VARCHAR(50) NOT NULL,
                        `work_number` CHAR(9) NOT NULL,
                        `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                        `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                        PRIMARY KEY (`user_id`),
                        UNIQUE KEY `employee_id` (`employee_id`),
                        UNIQUE KEY `work_number` (`work_number`),
                        CONSTRAINT `Doctor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: Nurse
-- Description: Stores nurse-specific employment details.
-- ===============================
CREATE TABLE `Nurse` (
                       `user_id` CHAR(36) NOT NULL,
                       `department` VARCHAR(50) NOT NULL DEFAULT 'NURSE',
                       `employee_id` CHAR(9) NOT NULL,
                       `location` VARCHAR(50) NOT NULL,
                       `work_number` CHAR(9) NOT NULL,
                       `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                       `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       PRIMARY KEY (`user_id`),
                       UNIQUE KEY `employee_id` (`employee_id`),
                       UNIQUE KEY `work_number` (`work_number`),
                       CONSTRAINT `Nurse_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: Appointment
-- Description: Stores appointment details between patients and healthcare providers.
-- ===============================
CREATE TABLE `Appointment` (
                             `appointment_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                             `patient_id` CHAR(36) NOT NULL,
                             `provider_id` CHAR(36) NOT NULL,
                             `start_time` DATETIME NOT NULL,
                             `end_time` DATETIME NOT NULL,
                             `status` ENUM('PENDING','COMPLETED','CANCELLED') NOT NULL DEFAULT 'PENDING',
                             `cancellation_reason` VARCHAR(255) DEFAULT NULL,
                             `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                             `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`appointment_id`),
                             UNIQUE KEY `uk_patient_time` (`patient_id`, `start_time`, `end_time`),
                             UNIQUE KEY `uk_provider_time` (`provider_id`, `start_time`, `end_time`),
                             CONSTRAINT `Appointment_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `User` (`user_id`),
                             CONSTRAINT `Appointment_ibfk_2` FOREIGN KEY (`provider_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: NurseVital
-- Description: Records nurse observations and vital signs during appointments.
-- ===============================
CREATE TABLE `NurseVital` (
                            `vital_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                            `appointment_id` CHAR(36) NOT NULL,
                            `heart_rate` INT DEFAULT NULL,
                            `blood_pressure` VARCHAR(20) DEFAULT NULL,
                            `temperature` DECIMAL(4,2) DEFAULT NULL,
                            `weight` DECIMAL(5,2) DEFAULT NULL,
                            `height` DECIMAL(5,2) DEFAULT NULL,
                            `bmi` DECIMAL(5,2) DEFAULT NULL,
                            `notes` TEXT,
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`vital_id`),
                            KEY `idx_appointment_id` (`appointment_id`),
                            CONSTRAINT `NurseVital_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `Appointment` (`appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: Prescription
-- Description: Stores diagnoses and drug allergy information related to appointments.
-- ===============================
CREATE TABLE `Prescription` (
                              `prescription_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                              `appointment_id` CHAR(36) NOT NULL,
                              `diagnosis` TEXT,
                              `drug_allergy` TEXT,
                              `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                              `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`prescription_id`),
                              KEY `idx_appointment_id` (`appointment_id`),
                              CONSTRAINT `Prescription_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `Appointment` (`appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: Medication
-- Description: Stores specific medications prescribed to patients.
-- ===============================
CREATE TABLE `Medication` (
                            `medication_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                            `prescription_id` CHAR(36) NOT NULL,
                            `name` VARCHAR(100) NOT NULL,
                            `dosage` VARCHAR(50) DEFAULT NULL,
                            `frequency` VARCHAR(50) DEFAULT NULL,
                            `note` TEXT,
                            `route` VARCHAR(50) DEFAULT NULL,
                            `duration` VARCHAR(50) DEFAULT NULL,
                            `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                            `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`medication_id`),
                            KEY `idx_prescription_id` (`prescription_id`),
                            CONSTRAINT `Medication_ibfk_1` FOREIGN KEY (`prescription_id`) REFERENCES `Prescription` (`prescription_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: PatientIntake
-- Description: Records patient complaints, doctor notes, and diagnosis.
-- ===============================
CREATE TABLE `PatientIntake` (
                               `intake_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                               `appointment_id` CHAR(36) NOT NULL,
                               `complaint` TEXT,
                               `notes` TEXT,
                               `diagnosis` TEXT,
                               `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                               `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`intake_id`),
                               KEY `idx_appointment_id` (`appointment_id`),
                               CONSTRAINT `PatientIntake_ibfk_1` FOREIGN KEY (`appointment_id`) REFERENCES `Appointment` (`appointment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ===============================
-- Table: HealthQA
-- Description: Stores forum posts or health questions submitted by users.
-- ===============================
CREATE TABLE `HealthQA` (
                          `health_id` CHAR(36) NOT NULL DEFAULT (UUID()),
                          `user_id` CHAR(36) NOT NULL,
                          `title` VARCHAR(200) NOT NULL,
                          `content` TEXT NOT NULL,
                          `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
                          `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`health_id`),
                          KEY `idx_user_id` (`user_id`),
                          CONSTRAINT `HealthQA_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP SCHEMA IF EXISTS LED;
CREATE SCHEMA IF NOT EXISTS `LED` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `LED` ;

-- -----------------------------------------------------
-- Table `LED`.`CASE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LED`.`CASE` (
  `CaseNumber` INT NOT NULL,
  `Perpetrator` VARCHAR(45) NOT NULL,
  `Victim` VARCHAR(45) NOT NULL,
  `DATE` VARCHAR(45) NOT NULL,
  `Description` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`CaseNumber`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LED`.`DMV`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LED`.`DMV` (
  `DLNumber` INT NOT NULL,
  `State` VARCHAR(2) NOT NULL,
  `FirstName` VARCHAR(45) NOT NULL,
  `LastName` VARCHAR(45) NOT NULL,
  `DOB` VARCHAR(45) NOT NULL,
  `Height` VARCHAR(45) NOT NULL,
  `Eyes` VARCHAR(45) NOT NULL,
  `Address` VARCHAR(45) NOT NULL,
  `DateIssued` VARCHAR(45) NOT NULL,
  `Sex` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`DLNumber`, `State`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LED`.`CRIMINAL_RECORD`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LED`.`CRIMINAL_RECORD` (
  `DLNumber` INT NOT NULL,
  `State` VARCHAR(2) NOT NULL,
  `CaseNumber` INT NOT NULL,
  INDEX `fk_CRIMINAL_RECORD_DMV1_idx` (`DLNumber` ASC, `State` ASC) VISIBLE,
  INDEX `fk_CRIMINAL_RECORD_CASE1_idx` (`CaseNumber` ASC) VISIBLE,
  CONSTRAINT `fk_CRIMINAL_RECORD_DMV1`
    FOREIGN KEY (`DLNumber` , `State`)
    REFERENCES `LED`.`DMV` (`DLNumber` , `State`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CRIMINAL_RECORD_CASE1`
    FOREIGN KEY (`CaseNumber`)
    REFERENCES `LED`.`CASE` (`CaseNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LED`.`EMPLOYEE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LED`.`EMPLOYEE` (
  `EmployeeID` INT NOT NULL AUTO_INCREMENT,
  `AccessLevel` VARCHAR(45) NOT NULL,
  `UserLogin` VARCHAR(45) NOT NULL,
  `PassLogin` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`EmployeeID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LED`.`VEHICLE`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LED`.`VEHICLE` (
  `PlateNumber` VARCHAR(45) NOT NULL,
  `Make` VARCHAR(45) NOT NULL,
  `Model` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`PlateNumber`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LED`.`VEHICLE_OWNERSHIP`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `LED`.`VEHICLE_OWNERSHIP` (
  `DLNumber` INT NOT NULL,
  `PlateNumber` VARCHAR(45) NOT NULL,
  INDEX `fk_VEHICLE OWNERSHIP_dmv1_idx` (`DLNumber` ASC) VISIBLE,
  INDEX `fk_VEHICLE_OWNERSHIP_VEHICLE1_idx` (`PlateNumber` ASC) VISIBLE,
  CONSTRAINT `fk_VEHICLE OWNERSHIP_dmv1`
    FOREIGN KEY (`DLNumber`)
    REFERENCES `LED`.`DMV` (`DLNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_VEHICLE_OWNERSHIP_VEHICLE1`
    FOREIGN KEY (`PlateNumber`)
    REFERENCES `LED`.`VEHICLE` (`PlateNumber`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
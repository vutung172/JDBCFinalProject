use warehouse_manager;
SELECT * FROM accounts WHERE User_name = 'tungvu' , Password = '12345';

DELIMITER //
CREATE PROCEDURE account_authenticator(IN user varchar(30),IN pass varchar(30),
                                       OUT accId int,OUT userName varchar(30),OUT empId char(5),OUT permission bit,OUT accStatus bit)
BEGIN
    SELECT * FROM accounts WHERE User_name = user AND Password = pass;
    SET accId = (SELECT accounts.Acc_id FROM accounts WHERE User_name = user AND Password = pass);
    SET userName = (SELECT accounts.User_name FROM accounts WHERE User_name = user AND Password = pass);
    SET empId = (SELECT accounts.Emp_id FROM accounts WHERE User_name = user AND Password = pass);
    SET permission = (SELECT accounts.Permission FROM accounts WHERE User_name = user AND Password = pass);
    SET accStatus = (SELECT accounts.Acc_status FROM accounts WHERE User_name = user AND Password = pass);
END; //
DELIMITER ;
DROP PROCEDURE account_authenticator;

call account_authenticator('tungvu','12345',@accID,@user,@empId,@permission,@accStatus);

INSERT INTO employees (Emp_Id, Emp_Name, Birth_Of_Date, Email, Phone, Address, Emp_Status)
SELECT
    CONCAT(CHAR(65 + FLOOR(RAND() * 26)), SUBSTRING(MD5(RAND()) FROM 1 FOR 4)) as Emp_Id,
    CONCAT('Employee ', SUBSTRING(MD5(RAND()) FROM 1 FOR 5)) as Emp_Name,
    DATE_ADD('1989-01-01', INTERVAL FLOOR(RAND() * 4383) DAY) as Birth_Of_Date,
    CONCAT('Employee', SUBSTRING(MD5(RAND()) FROM 1 FOR 5), '@mpv.com') as Email,
    CONCAT('0', FLOOR(8 + RAND() * 2), FLOOR(RAND() * 1000000000)) as Phone,
    CASE WHEN RAND() > 0.5 THEN 'Hanoi' ELSE 'HoChiMinh' END as Address,
    FLOOR(RAND() * 3) as Emp_Status
FROM
    information_schema.tables LIMIT 100;

CREATE VIEW vw_accounts_employees AS
SELECT acc_id, user_name, password, permission, accounts.emp_id as Emp_Id, acc_status, emp_name, birth_of_date, email, phone, address, emp_status
FROM accounts
    JOIN employees ON accounts.Emp_id = employees.Emp_Id;

CREATE VIEW vw_bills_billDetails AS
SELECT bills.Bill_id as Bill_id, Bill_Code, Bill_Type, Emp_id_created, Created, Emp_id_auth, Auth_date, Bill_Status, Bill_Detail_Id, Product_Id, Quantity, Price FROM bills
JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id;

CREATE VIEW vw_billDetails_products AS
    SELECT  Bill_Id, Bill_Detail_Id,  bill_details.Product_Id AS Product_ID, bill_details.Quantity AS Quantity, Price, Product_Name, Manufacturer, Created, Batch, products.Quantity AS Total_Quantity, Product_Status FROM bill_details
JOIN products ON bill_details.Product_Id = products.Product_Id;

//
    SELECT sum(bill_details.Price*bill_details.Quantity) FROM bills
        JOIN bill_details ON bills.Bill_id = bill_details.Bill_Id
    WHERE Bill_Type = true AND Bill_Status = 2
    GROUP BY bills.Created
    HAVING YEAR(bills.Created) = ?

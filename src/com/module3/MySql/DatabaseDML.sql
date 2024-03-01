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

DELIMITER //
CREATE PROCEDURE Insert100FakeProducts()
BEGIN
    DECLARE i INT DEFAULT 1;

    WHILE i <= 100 DO
            INSERT INTO products (
                Product_Id,
                Product_Name,
                Manufacturer,
                Batch,
                Quantity,
                Product_Status
            ) VALUES (
                         CONCAT('P', LPAD(i, 4, '0')),
                         CONCAT('Sản phẩm ', i),
                         CONCAT('Nhà sản xuất ', i),
                         FLOOR(RAND() * 100) + 1,
                         FLOOR(RAND() * 1000),
                         i % 2
                     );

            SET i = i + 1;
        END WHILE;
END; //
DELIMITER ;

CALL Insert100FakeProducts();

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
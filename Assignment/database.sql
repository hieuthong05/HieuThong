USE master;
GO

CREATE DATABASE Assignment;
GO

USE Assignment;
GO
CREATE TABLE Users (
    userId INT IDENTITY(1,1) PRIMARY KEY,
    userName VARCHAR(50) NOT NULL UNIQUE,
    name NVARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL ,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NULL ,
    createdAt DATE DEFAULT GETDATE()
);
INSERT INTO Users (userName, name, email, password, role, createdAt)
VALUES 
-- Admins
(N'admin1', N'Nguyễn Quang Minh', N'quang.admin@laptopstore.vn', N'ad1', N'admin', '2024-01-05'),
(N'admin2', N'Trần Ngọc Linh', N'linh.tran@laptopstore.vn', N'ad2', N'admin', '2024-02-10'),

-- Customers
(N'user1', N'Lê Văn Khoa', N'khoa.le98@gmail.com', N'u1', N'customer', '2024-03-12'),
(N'thao.nguyen21', N'Nguyễn Thị Thảo', N'thao.nguyen21@yahoo.com', N'NTT', N'customer', '2024-04-18'),
(N'tuan.pham87', N'Phạm Tuấn Anh', N'tuan.pham87@hotmail.com', N'PTA', N'customer', '2024-05-05'),
(N'ha.tran92', N'Trần Minh Hà', N'ha.tran92@gmail.com', N'TMH', N'customer', '2024-06-01'),
(N'ngoc.bui95', N'Bùi Thị Ngọc', N'ngoc.bui95@outlook.com', N'BTN', N'customer', '2024-06-15'),
(N'duy.vo88', N'Võ Duy Khánh', N'duy.vo88@gmail.com', N'VDK', N'customer', '2024-06-22'),
(N'quyen.do99', N'Đỗ Thị Quyên', N'quyen.do99@gmail.com', N'DTQ', N'customer', '2024-07-01'),
(N'dat.nguyen', N'Nguyễn Hữu Đạt', N'dat.nguyen@gmail.com', N'NHD', N'customer', '2024-07-05');

CREATE TABLE Brand (
    brandId INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    origin VARCHAR(100),
    foundedYear INT,
	description NVARCHAR(500) NULL
);

-- Insert 8 brands vào bảng Brand
INSERT INTO Brand (name, origin, foundedYear, description) VALUES
(N'Apple', N'United States', 1976, N'Công ty công nghệ hàng đầu nổi tiếng với iPhone, MacBook, và các thiết bị cao cấp.'),
(N'Samsung', N'South Korea', 1938, N'Tập đoàn đa quốc gia nổi bật với điện thoại Galaxy, TV, và thiết bị điện tử.'),
(N'Dell', N'United States', 1984, N'Chuyên sản xuất laptop, máy tính cá nhân và các thiết bị văn phòng.'),
(N'HP', N'United States', 1939, N'Cung cấp các giải pháp máy tính, laptop và máy in toàn cầu.'),
(N'Lenovo', N'China', 1984, N'Nhà sản xuất laptop ThinkPad, IdeaPad và thiết bị điện tử hàng đầu tại Trung Quốc.'),
(N'Asus', N'Taiwan', 1989, N'Nổi tiếng với laptop gaming ROG, bo mạch chủ và linh kiện máy tính.'),
(N'Sony', N'Japan', 1946, N'Tập đoàn đa ngành nổi bật trong lĩnh vực điện tử, giải trí và máy chơi game.'),
(N'Xiaomi', N'China', 2010, N'Nhà sản xuất điện thoại thông minh, laptop và thiết bị gia dụng giá tốt, chất lượng cao.');

CREATE TABLE Category (
    categoryId INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(100) NOT NULL,
    description NVARCHAR(500) NULL
);

-- Insert 7 categories vào bảng Category
INSERT INTO Category (name, description) VALUES
(N'Laptop', N'Các dòng máy tính xách tay phục vụ học tập, làm việc, gaming và thiết kế.'),
(N'Smartphone', N'Điện thoại thông minh với nhiều tính năng hiện đại, hỗ trợ kết nối và giải trí.'),
(N'Tablet', N'Máy tính bảng với màn hình cảm ứng, phù hợp cho giải trí và công việc di động.'),
(N'Smartwatch', N'Đồng hồ thông minh theo dõi sức khỏe, kết nối thông báo và hỗ trợ thể thao.'),
(N'Desktop PC', N'Máy tính để bàn dành cho văn phòng, học tập, thiết kế đồ họa hoặc chơi game.'),
(N'Monitor', N'Màn hình hiển thị chất lượng cao cho máy tính, hỗ trợ làm việc và giải trí.'),
(N'Accessories', N'Phụ kiện đi kèm như chuột, bàn phím, tai nghe, sạc, balo laptop,...');

CREATE TABLE Product (
    productId INT PRIMARY KEY IDENTITY(1,1),
    name VARCHAR(255) NOT NULL,
    brandId INT,
    categoryId INT, 
    price DECIMAL(12,2),
    stock INT,
    description NVARCHAR(1000),
    imageUrl VARCHAR(500),
    FOREIGN KEY (brandId) REFERENCES Brand(brandId),
    FOREIGN KEY (categoryId) REFERENCES Category(categoryId)
);

-- 1-5: Laptop
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'MacBook Air M2 13"', 1, 1, 28990000, 150, N'Laptop mỏng nhẹ, chip Apple M2, pin lâu, phù hợp cho học tập và văn phòng.', ''),
(N'Dell Inspiron 15 3520', 3, 1, 18990000, 120, N'Laptop phổ thông, màn 15.6", Intel Core i5, phù hợp sinh viên, nhân viên.', ''),
(N'HP Pavilion 14', 4, 1, 17490000, 130, N'Thiết kế hiện đại, Intel Core i5 Gen12, màn 14", pin lâu.', ''),
(N'Asus ROG Strix G16', 6, 1, 36990000, 110, N'Laptop gaming hiệu năng cao, RTX 4060, màn 165Hz.', ''),
(N'Lenovo IdeaPad 3', 5, 1, 13990000, 140, N'Laptop học sinh, sinh viên, chip AMD Ryzen, giá rẻ.', '');

-- 6-10: Smartphone
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'iPhone 15 Pro Max 256GB', 1, 2, 34990000, 200, N'Flagship Apple, chip A17 Pro, camera chuyên nghiệp.', ''),
(N'Samsung Galaxy S24 Ultra 256GB', 2, 2, 29990000, 210, N'Màn AMOLED 6.8", S Pen, zoom 100x, chip Snapdragon 8 Gen 3.', ''),
(N'Xiaomi Redmi Note 13 Pro', 8, 2, 7490000, 150, N'Smartphone giá rẻ cấu hình mạnh, màn AMOLED 120Hz.', ''),
(N'Sony Xperia 1 V', 7, 2, 29900000, 120, N'Camera chuyên nghiệp, màn 4K OLED 120Hz, thiết kế sang trọng.', ''),
(N'Samsung Galaxy A55', 2, 2, 9490000, 180, N'Máy tầm trung, thiết kế cao cấp, pin trâu, màn đẹp.', '');

-- 11-13: Tablet
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'iPad Air 5 M1 64GB WiFi', 1, 3, 16990000, 160, N'Máy tính bảng Apple chip M1 mạnh mẽ, hỗ trợ Apple Pencil.', ''),
(N'Xiaomi Pad 6 8GB/256GB', 8, 3, 8990000, 130, N'Màn hình 2.8K, Snapdragon 870, pin lớn, giá tốt.', ''),
(N'Samsung Galaxy Tab S9 FE', 2, 3, 10990000, 140, N'Máy tính bảng Android màn lớn, hỗ trợ S Pen.', '');

-- 14-15: Smartwatch
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'Apple Watch Series 9 41mm', 1, 4, 10990000, 160, N'Đồng hồ thông minh theo dõi sức khỏe, hỗ trợ Siri.', ''),
(N'Galaxy Watch6 40mm', 2, 4, 6990000, 150, N'Màn Super AMOLED, theo dõi giấc ngủ, thể thao.', '');

-- 16-17: Desktop PC
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'Dell Vostro 3888 MT', 3, 5, 13490000, 110, N'Máy tính để bàn dành cho doanh nghiệp nhỏ, Core i5.', ''),
(N'HP Pavilion Gaming TG01', 4, 5, 20900000, 100, N'Máy bàn gaming, card đồ họa GTX 1650, CPU Ryzen 5.', '');

-- 18-19: Monitor
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'LG UltraGear 27GP850-B 27"', 6, 6, 7490000, 120, N'Màn hình gaming 2K, tần số quét 165Hz, IPS.', ''),
(N'Dell S2721HN 27"', 3, 6, 4290000, 140, N'Màn hình văn phòng Full HD, viền mỏng, tấm nền IPS.', '');

-- 20: Accessories
INSERT INTO Product (name, brandId, categoryId, price, stock, description, imageUrl) VALUES
(N'Apple AirPods Pro 2', 1, 7, 5490000, 180, N'Tai nghe không dây chống ồn chủ động, âm thanh tốt.', '');

CREATE TABLE [Order] (
    orderId INT PRIMARY KEY IDENTITY(1,1),
    userId INT NOT NULL,
    orderDate DATE DEFAULT GETDATE(),
    expectedDeliveryDate DATE,
    status VARCHAR(50) CHECK (status IN ('pending', 'shipped', 'completed', 'cancelled')),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);
INSERT INTO [Order] (userId, orderDate, expectedDeliveryDate, status) VALUES
(3, '2024-06-01', DATEADD(DAY, 3, '2024-06-01'), 'pending'),
(5, '2024-06-03', DATEADD(DAY, 3, '2024-06-03'), 'shipped'),
(7, '2024-06-05', DATEADD(DAY, 3, '2024-06-05'), 'completed'),
(4, '2024-06-07', DATEADD(DAY, 3, '2024-06-07'), 'cancelled'),
(6, '2024-06-09', DATEADD(DAY, 3, '2024-06-09'), 'pending'),
(8, '2024-06-11', DATEADD(DAY, 3, '2024-06-11'), 'shipped'),
(9, '2024-06-13', DATEADD(DAY, 3, '2024-06-13'), 'completed'),
(10, '2024-06-15', DATEADD(DAY, 3, '2024-06-15'), 'cancelled'),
(3, '2024-06-17', DATEADD(DAY, 3, '2024-06-17'), 'pending'),
(5, '2024-06-19', DATEADD(DAY, 3, '2024-06-19'), 'shipped'),
(6, '2024-06-21', DATEADD(DAY, 3, '2024-06-21'), 'completed'),
(7, '2024-06-23', DATEADD(DAY, 3, '2024-06-23'), 'cancelled'),
(8, '2024-06-25', DATEADD(DAY, 3, '2024-06-25'), 'pending'),
(9, '2024-06-27', DATEADD(DAY, 3, '2024-06-27'), 'shipped'),
(10, '2024-06-29', DATEADD(DAY, 3, '2024-06-29'), 'completed');

CREATE TABLE OrderItem (
    itemId INT PRIMARY KEY IDENTITY(1,1),
    orderId INT NOT NULL,
    productId INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unitPrice DECIMAL(12,2) NOT NULL CHECK (unitPrice >= 0),
    FOREIGN KEY (orderId) REFERENCES [Order](orderId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

-- Order 1
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (1, 1, 2, 28990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (1, 6, 1, 34990000);

-- Order 2
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (2, 2, 1, 18990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (2, 7, 2, 29990000);

-- Order 3
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (3, 3, 3, 17490000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (3, 11, 1, 16990000);

-- Order 4
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (4, 4, 1, 36990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (4, 8, 2, 7490000);

-- Order 5
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (5, 5, 4, 13990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (5, 9, 1, 29900000);

-- Order 6
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (6, 10, 1, 9490000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (6, 14, 3, 10990000);

-- Order 7
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (7, 12, 2, 8990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (7, 15, 2, 6990000);

-- Order 8
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (8, 13, 1, 10990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (8, 16, 1, 13490000);

-- Order 9
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (9, 17, 2, 20900000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (9, 20, 1, 5490000);

-- Order 10
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (10, 18, 2, 7490000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (10, 19, 1, 4290000);

-- Order 11
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (11, 1, 1, 28990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (11, 8, 2, 7490000);

-- Order 12
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (12, 6, 2, 34990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (12, 11, 1, 16990000);

-- Order 13
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (13, 7, 3, 29990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (13, 14, 1, 10990000);

-- Order 14
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (14, 3, 1, 17490000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (14, 9, 1, 29900000);

-- Order 15
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (15, 4, 2, 36990000);
INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (15, 12, 2, 8990000);

CREATE TABLE Payment (
    paymentId INT PRIMARY KEY IDENTITY(1,1),
    orderId INT UNIQUE NOT NULL,
    amount DECIMAL(12,2) NOT NULL CHECK (amount >= 0),
    method VARCHAR(50) CHECK (method IN ('credit', 'bank', 'cod')),
    paidAt DATE DEFAULT GETDATE(),
    FOREIGN KEY (orderId) REFERENCES [Order](orderId)
);

INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (1, 92970000, 'credit', '2024-06-01');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (2, 78970000, 'bank', '2024-06-03');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (3, 69460000, 'cod', '2024-06-05');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (4, 51970000, 'credit', '2024-06-07');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (5, 85860000, 'cod', '2024-06-09');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (6, 42460000, 'bank', '2024-06-11');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (7, 31960000, 'cod', '2024-06-13');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (8, 24480000, 'credit', '2024-06-15');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (9, 47290000, 'cod', '2024-06-17');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (10, 19270000, 'bank', '2024-06-19');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (11, 43970000, 'credit', '2024-06-21');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (12, 86970000, 'bank', '2024-06-23');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (13, 100960000, 'cod', '2024-06-25');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (14, 47390000, 'credit', '2024-06-27');
INSERT INTO Payment (orderId, amount, method, paidAt) VALUES (15, 91960000, 'bank', '2024-06-29');

CREATE TABLE Review (
    reviewId INT PRIMARY KEY IDENTITY(1,1), 
    userId INT NOT NULL,                   
    productId INT NOT NULL,                
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5), 
    comment NVARCHAR(1000),               
    createdAt DATE DEFAULT GETDATE(),     
    FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);

INSERT INTO Review (userId, productId, rating, comment, createdAt) VALUES 
(3, 1, 5, N'Máy chạy cực kỳ mượt, pin dùng cả ngày, đáng đồng tiền.', '2024-06-02'),
(3, 6, 4, N'Camera chụp đẹp, nhưng hơi nóng khi quay video lâu.', '2024-06-03'),
(5, 2, 4, N'Giao hàng nhanh, máy mượt, nhưng vỏ hơi dễ trầy.', '2024-06-04'),
(5, 7, 5, N'Màn hình siêu đẹp, zoom 100x quá ấn tượng!', '2024-06-05'),
(7, 3, 3, N'Máy ổn, nhưng pin hơi yếu khi dùng lâu.', '2024-06-06'),
(7, 11, 5, N'Rất phù hợp cho vẽ và ghi chú, màn đẹp, hiệu năng tốt.', '2024-06-07'),
(4, 4, 5, N'Chơi game mượt mà, thiết kế hầm hố, rất hài lòng.', '2024-06-08'),
(4, 8, 4, N'Máy ngon trong tầm giá, hiệu năng ổn định.', '2024-06-09'),
(6, 5, 3, N'Giá rẻ, hiệu năng tạm được, nhưng không quá nổi bật.', '2024-06-10'),
(6, 9, 4, N'Màn đẹp, chụp ảnh tốt, pin ổn. Hài lòng.', '2024-06-11'),
(8, 10, 4, N'Máy nhỏ gọn, màn đẹp, đủ nhu cầu cơ bản.', '2024-06-12'),
(8, 14, 1, N'Thiết kế thiếu logic, pin mau hết, không hiệu quả.', '2024-06-13'),
(9, 12, 5, N'Giao hàng đúng hẹn, sản phẩm chính hãng, rất ổn.', '2024-06-14'),
(9, 15, 4, N'Dùng tốt, màn đẹp, dễ đeo nhưng pin không quá trâu.', '2024-06-15'),
(10, 13, 4, N'Phù hợp với nhu cầu học tập, giá tốt.', '2024-06-16'),
(10, 16, 3, N'Máy chạy ổn, nhưng thiết kế hơi cồng kềnh.', '2024-06-17'),
(3, 17, 5, N'Máy cực mạnh, chạy game max setting ngon lành.', '2024-06-18'),
(3, 20, 5, N'Tai nghe tốt, kết nối nhanh, chống ồn rất hiệu quả.', '2024-06-19'),
(5, 18, 4, N'Màn hình sắc nét, chơi game mượt nhưng giá hơi cao.', '2024-06-20'),
(5, 19, 3, N'Màn tạm được, đủ dùng cho văn phòng, nhưng màu hơi nhạt.', '2024-06-21');


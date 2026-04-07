CREATE TABLE logsearch (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       item_search VARCHAR(100) NOT NULL,
                       date_search TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
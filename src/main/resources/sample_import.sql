-- Script import mẫu dữ liệu hành chính và ranh giới cho MySQL

INSERT INTO administrative_unit (id, name_old, name_new, type, area, population) VALUES
(1, 'Xã Tân Lộc (cũ)', 'Xã Tân Lộc Đông', 'xa', 12.5, 8500),
(2, 'Xã Tân Hòa (cũ)', 'Xã Tân Lộc Đông', 'xa', 10.2, 7200),
(3, '', 'Xã Tân Lộc Đông', 'xa', 22.7, 15700);

INSERT INTO boundary (id, unit_id, type, geometry, time) VALUES
(1, 1, 'old', 'POLYGON((105.7 10.0, 105.8 10.0, 105.8 10.1, 105.7 10.1, 105.7 10.0))', '2020'),
(2, 2, 'old', 'POLYGON((105.7 10.1, 105.8 10.1, 105.8 10.2, 105.7 10.2, 105.7 10.1))', '2020'),
(3, 3, 'new', 'POLYGON((105.7 10.0, 105.8 10.0, 105.8 10.2, 105.7 10.2, 105.7 10.0))', '2025');

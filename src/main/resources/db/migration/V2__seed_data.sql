-- V2__seed_data.sql

-- Insert Users
INSERT INTO users (id, name, email, role, status)
VALUES
(gen_random_uuid(), 'Admin User', 'admin@test.com', 'ADMIN', 'ACTIVE'),
(gen_random_uuid(), 'Analyst User', 'analyst@test.com', 'ANALYST', 'ACTIVE'),
(gen_random_uuid(), 'Viewer User', 'viewer@test.com', 'VIEWER', 'ACTIVE');

-- Insert Financial Records
INSERT INTO financial_records (id, user_id, amount, type, category, record_date, notes, is_deleted)
SELECT
    gen_random_uuid(),
    u.id,
    v.amount,
    v.type,
    v.category,
    v.record_date,
    v.notes,
    FALSE
FROM users u
JOIN (
    VALUES
    (5000.00, 'INCOME', 'Salary', DATE '2026-04-01', 'Monthly salary'),
    (200.00, 'EXPENSE', 'Food', DATE '2026-04-02', 'Lunch'),
    (800.00, 'EXPENSE', 'Rent', DATE '2026-04-03', 'Monthly rent'),
    (300.00, 'EXPENSE', 'Groceries', DATE '2026-04-04', 'Shopping'),
    (1500.00, 'INCOME', 'Freelance', DATE '2026-04-05', 'Side project'),
    (100.00, 'EXPENSE', 'Transport', DATE '2026-04-06', 'Fuel'),
    (250.00, 'EXPENSE', 'Dining', DATE '2026-04-07', 'Dinner'),
    (600.00, 'EXPENSE', 'Utilities', DATE '2026-04-08', 'Electricity bill'),
    (1200.00, 'INCOME', 'Bonus', DATE '2026-04-09', 'Performance bonus'),
    (400.00, 'EXPENSE', 'Shopping', DATE '2026-04-10', 'Clothes')
) AS v(amount, type, category, record_date, notes)
ON TRUE;
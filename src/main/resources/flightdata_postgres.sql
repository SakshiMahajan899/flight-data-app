-- Connect to the correct database
-- \c flightdb;  -- Uncomment if using psql CLI

-- Drop existing table (safe for re-running)
DROP TABLE IF EXISTS flights;

-- Step 1: Create flights table
CREATE TABLE flights (
    id UUID PRIMARY KEY,
    airline VARCHAR(100),
    supplier VARCHAR(50),
    fare NUMERIC(10, 2),
    departure_airport CHAR(3),
    destination_airport CHAR(3),
    departure_time TIMESTAMPTZ,
    arrival_time TIMESTAMPTZ
);

-- Step 2: Insert sample flights with dynamic departure/arrival
INSERT INTO flights (id, airline, supplier, fare, departure_airport, destination_airport, departure_time, arrival_time)
VALUES
('f1a1e456-aaaa-bbbb-cccc-000000000001', 'Lufthansa', 'Internal', 320.75, 'FRA', 'JFK',
 NOW() + INTERVAL '6 hours', NOW() + INTERVAL '14 hours'),

('f1a1e456-aaaa-bbbb-cccc-000000000002', 'KLM', 'Internal', 280.25, 'AMS', 'LHR',
 NOW() + INTERVAL '4 hours', NOW() + INTERVAL '9 hours'),

('f1a1e456-aaaa-bbbb-cccc-000000000003', 'Delta', 'Internal', 350.00, 'ATL', 'SFO',
 NOW() - INTERVAL '1 day' + INTERVAL '2 hours', NOW() - INTERVAL '1 day' + INTERVAL '10 hours');

-- Step 3: Fallback values for missing airline or fare
UPDATE flights
SET airline = 'Unknown Airline'
WHERE airline IS NULL;

UPDATE flights
SET fare = 0.00
WHERE fare IS NULL;

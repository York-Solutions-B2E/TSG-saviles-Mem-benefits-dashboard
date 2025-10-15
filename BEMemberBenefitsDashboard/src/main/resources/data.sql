INSERT INTO plans(id, name, type, network_name, plan_year)
VALUES
    (gen_random_uuid(), 'Gold PPO', 'PPO', 'Prime Network', 2025),
    (gen_random_uuid(), 'Silver HMO', 'HMO', 'CarePlus Network', 2025),
    (gen_random_uuid(), 'Bronze EPO', 'EPO', 'HealthFirst Network', 2025)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO enrollments(id, member_id, plan_id, coverage_start, coverage_end, active)
VALUES(gen_random_uuid(),'fa97d609-cd19-4463-be77-5b3c75fe3056', 'ccb634f4-ed72-42d2-b16f-199141292fc2', '2025-01-01', '2025-12-31', true )
ON CONFLICT (member_id, plan_id) DO NOTHING;

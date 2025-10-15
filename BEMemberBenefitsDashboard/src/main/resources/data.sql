INSERT INTO plans(id, name, type, network_name, plan_year)
VALUES
    (gen_random_uuid(), 'Gold PPO', 'PPO', 'Prime Network', 2025),
    (gen_random_uuid(), 'Silver HMO', 'HMO', 'CarePlus Network', 2025),
    (gen_random_uuid(), 'Bronze EPO', 'EPO', 'HealthFirst Network', 2025)
    ON CONFLICT (name) DO NOTHING;

INSERT INTO enrollments(id, member_id, plan_id, coverage_start, coverage_end, active)
VALUES(gen_random_uuid(),'f3fac9ff-a1d6-4ee0-be35-5eac1deab15e', '677715fd-f700-4372-9431-2b7ad225e52a', '2025-01-01', '2025-12-31', true )
ON CONFLICT (member_id, plan_id) DO NOTHING;

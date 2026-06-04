# audit-log

The current state lives in a main table. Every change writes a new row to a separate history table.

## Example: employee salaries

`employees` — current state, one row per employee.

| id | name | salary |
|----|------|--------|
| 1  | Ana  | 75000  |
| 2  | Ben  | 82000  |
| 3  | Cora | 91000  |

`salary_history` — one row per salary change (including the initial hire).

| update_id | employee_id | salary | recorded_at | actor_id |
|-----------|-------------|--------|-------------|----------|
| 1         | 1           | 70000  | 2024-01-15  | 42       |
| 2         | 1           | 75000  | 2025-03-01  | 42       |
| 3         | 2           | 80000  | 2024-06-01  | 42       |
| 4         | 2           | 82000  | 2025-01-10  | 17       |
| 5         | 3           | 91000  | 2024-01-01  | 17       |

Reading the current salary hits `employees` directly. Reading the salary history queries `salary_history` for one employee. Each additional attribute that needs history usually means another side table.

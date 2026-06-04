# versioned-objects

A single table holds every version of an entity. Updates aren't UPDATEs — they close out the previous version (`valid_to`) and insert a new one.

## Example: employee salaries

`employees` — one row per version of each employee.

| update_id | employee_id | name | salary | valid_from | valid_to   | actor_id |
|-----------|-------------|------|--------|------------|------------|----------|
| 1         | 1           | Ana  | 70000  | 2024-01-15 | 2025-03-01 | 42       |
| 2         | 1           | Ana  | 75000  | 2025-03-01 | null       | 42       |
| 3         | 2           | Ben  | 80000  | 2024-06-01 | 2025-01-10 | 42       |
| 4         | 2           | Ben  | 82000  | 2025-01-10 | null       | 17       |
| 5         | 3           | Cora | 91000  | 2024-01-01 | null       | 17       |

Reading the current state filters for `valid_to IS NULL`. Reading history is every row for that id, ordered by `valid_from`. New attributes that need history become more columns on the same table.

-- LAB 2

-- 1. 
SELECT * FROM 
(
  SELECT JOBNAME FROM CAREER LEFT JOIN JOB ON CAREER.JOBNO = JOB.JOBNO
)
PIVOT
(
  COUNT(JOBNAME) FOR JOBNAME IN('CLERK', 'DRIVER', 'EXECUTIVE DIRECTOR', 'FINANCIAL DIRECTOR', 'MANAGER', 'PRESIDENT', 'SALESMAN')
);

-- 2.
SELECT MAX(CASE WHEN DEPTNAME = 'ACCOUNTING' THEN EMPNAME ELSE NULL END) AS ACCOUNTING,
MAX(CASE WHEN DEPTNAME = 'RESEARCH' THEN EMPNAME ELSE NULL END) AS RESEARCH,
MAX(CASE WHEN DEPTNAME = 'SALES' THEN EMPNAME ELSE NULL END) AS SALES,
MAX(CASE WHEN DEPTNAME = 'OPERATIONS' THEN EMPNAME ELSE NULL END) AS OPERATIONS
FROM (
SELECT DEPTNAME, EMPNAME, ROW_NUMBER()OVER(PARTITION BY DEPTNAME ORDER BY EMPNAME) RN
FROM EMP JOIN (CAREER JOIN DEPT USING(DEPTNO)) USING(EMPNO) 
)
GROUP BY RN;

-- 3.
SELECT J.JOBNAME,
CASE J.JOBNAME
WHEN 'CLERK' THEN FIRST.CLERK
WHEN 'DRIVER' THEN FIRST.DRIVER
WHEN 'EXECUTIVE DIRECTOR' THEN FIRST.EXECUTIVE_DIRECTOR
WHEN 'FINANCIAL DIRECTOR' THEN FIRST.FINANCIAL_DIRECTOR
WHEN 'MANAGER' THEN FIRST.MANAGER
WHEN 'PRESIDENT' THEN FIRST.PRESIDENT
WHEN 'SALESMAN' THEN FIRST.SALESMAN
ELSE NULL
END AS R
FROM (
SELECT * FROM 
(
  SELECT JOBNAME FROM CAREER LEFT JOIN JOB ON CAREER.JOBNO = JOB.JOBNO
)
PIVOT
(
  COUNT(JOBNAME) FOR JOBNAME IN('CLERK' AS "CLERK", 'DRIVER' "DRIVER", 'EXECUTIVE DIRECTOR' "EXECUTIVE_DIRECTOR", 'FINANCIAL DIRECTOR' "FINANCIAL_DIRECTOR", 'MANAGER' "MANAGER", 'PRESIDENT' "PRESIDENT", 'SALESMAN' AS "SALESMAN")
)
) FIRST,
(SELECT JOBNAME FROM JOB) J;

-- 4. 
CREATE OR REPLACE VIEW emps_info AS (
SELECT JOBNO, JOBNAME, COUNT(JOBNAME) AS JOBCOUNT
FROM CAREER JOIN JOB USING(JOBNO)
GROUP BY JOBNAME, JOBNO);

SELECT 
CASE rn
WHEN 1 THEN JOBNAME
WHEN 2 THEN CAST(JOBCOUNT AS CHAR(4))
END emps
FROM (
SELECT e.JOBNAME, e.JOBCOUNT,
ROW_NUMBER()OVER(PARTITION BY e.JOBNAME ORDER BY e.JOBNAME) rn
FROM emps_info e,
(SELECT * FROM emps_info WHERE ROWNUM <= 3) t_rows
);

-- 5.
SELECT DECODE(LAG(DEPTNAME) OVER(ORDER BY DEPTNAME), DEPTNAME, NULL, DEPTNAME) LAG, EMPNAME
FROM EMP JOIN (CAREER JOIN DEPT USING(DEPTNO)) USING(EMPNO);

-- 6
select d30_sal - d10_sal as d30_d10_diff,
d10_sal - d40_sal as d10_d40_diff
from (
select sum(case when deptno = 10 then salvalue end) as d10_sal,
 sum(case when deptno = 30 then salvalue end) as d30_sal,
 sum(case when deptno = 40 then salvalue end) as d40_sal
from emp join salary using(empno) join career using(empno) join job using(jobno) join dept using(deptno)
) total_by_dept;
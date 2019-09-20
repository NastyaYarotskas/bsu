--1------------------------
WITH AVG_sal AS (
    SELECT year, AVG(salvalue) AVG_sal    
    FROM salary GROUP BY year) 
SELECT RANK() OVER(ORDER BY AVG_sal) rn, AVG_sal, year 
FROM AVG_sal;

--2---------------------------
WITH cum_sal AS (
    SELECT month, year, SUM(salvalue) cum_sal 
    FROM salary GROUP BY year, month
    ) 
SELECT DENSE_RANK() OVER(ORDER BY cum_sal) rn, cum_sal, year, month 
FROM cum_sal;

--3-----------------------------
SELECT emp.empname, salary.salvalue, 
        RANK() OVER(PARTITION BY empname ORDER BY salary.year, salary.month) rn, 
        DENSE_RANK() OVER(PARTITION BY empname ORDER BY salary.year, salary.month) d_rn 
FROM emp JOIN salary USING(empno);

--4-------------------------
WITH sal AS (
    SELECT year, month, 
            AVG(salvalue) AVG_sal, 
            GROUPING_ID(year, month) g_id 
    FROM salary GROUP BY CUBE(year, month)
    ) 
SELECT year, month, AVG_sal, 
        RANK() OVER(PARTITION BY g_id ORDER BY AVG_sal DESC) rn 
FROM sal;

--5-------------------
SELECT jobname, empname, salvalue, 
        CUME_DIST() OVER(PARTITION BY jobname ORDER BY salvalue) c_d 
FROM emp, salary, job, career 
WHERE career.empno = emp.empno AND career.empno = salary.empno AND career.jobno = job.jobno; 

--6-------------------------
SELECT jobname, empname, salvalue, 
        PERCENT_RANK() OVER(PARTITION BY jobname ORDER BY salvalue) p_r 
FROM emp, salary, job, career 
WHERE career.empno = emp.empno AND career.empno = salary.empno AND career.jobno = job.jobno; 
-- Лабораторная работа 1 --

-- Выдать информацию о местоположении отдела продаж (SALES) компании.
SELECT DEPTADDR 
FROM DEPT 
WHERE DEPTNAME = 'SALES';

-- Выдать информацию об отделах, расположенных в Chicago и New York.
SELECT *
FROM DEPT 
WHERE DEPTADDR IN ('CHICAGO', 'NEW YORK');

-- Найти минимальную заработную плату, начисленную в 2009 году.
SELECT MIN(SALVALUE) 
FROM SALARY 
WHERE YEAR = 2009;

-- Подсчитать число работников, сведения о которых имеются в базе данных.
SELECT COUNT(*) 
FROM EMP;

-- Выдать информацию о должностях, изменив названия должности “CLERK” и
-- “DRIVER” на “WORKER”.
SELECT JOBNO,
DECODE(JOBNAME, 'CLERK', 'WORKER',
                'DRIVER', 'WORKER',
       JOBNAME) JOBNAME,
MINSALARY 
FROM JOB;

SELECT JOBNO,
SELECT JOBNO,
CASE JOBNAME
WHEN 'CLERK' THEN 'WORKER'
WHEN 'DRIVER' THEN 'WORKER'
ELSE JOBNAME
END JOBNAME,
MINSALARY
FROM JOB;

-- Определите максимальную зарплату за каждый год.
SELECT YEAR, MAX(SALVALUE)
FROM SALARY
GROUP BY YEAR;

-- Определите среднюю зарплату за годы, в которые были начисления не менее чем за три месяца.
SELECT YEAR, AVG(SALVALUE)
FROM SALARY
GROUP BY YEAR
HAVING COUNT(YEAR) >= 3;

-- Выполните декартово произведение таблиц EMP, CAREER, SALES.
SELECT *
FROM EMP, CAREER, SALARY;

-- Выведете ведомость получения зарплаты с указанием имен служащих. Выполните сортировку по имени сотрудника (ORDER BY).
SELECT EMP.EMPNAME, SALARY.MONTH, SALARY.YEAR, SALARY.SALVALUE
FROM EMP JOIN SALARY USING(EMPNO)
ORDER BY EMP.EMPNAME, SALARY.MONTH;

-- Выдайте сведения о карьере сотрудников с указанием их имён, наименования должности, и названия отдела.
SELECT EMP.EMPNAME, JOB.JOBNAME, DEPT.DEPTNAME
FROM EMP
LEFT JOIN CAREER ON EMP.EMPNO = CAREER.EMPNO
LEFT JOIN JOB ON JOB.JOBNO = CAREER.JOBNO
LEFT JOIN DEPT ON DEPT.DEPTNO = CAREER.DEPTNO;

-- Найти имена сотрудников, получивших за годы начисления зарплаты
минимальную зарплату.
SELECT EMPNAME, SALVALUE
FROM EMP 
LEFT JOIN SALARY ON EMP.EMPNO = SALARY.EMPNO
WHERE SALVALUE = (SELECT MIN(SALVALUE) 
FROM SALARY);

-- Разделите сотрудников на возрастные группы: A) возраст 20-30 лет; B) 31-40 лет;
C) 41-50; D) 51-60 или возраст не определён.
SELECT EMPNO, EMPNAME,
CASE
WHEN (to_date(SYSDATE, 'dd-mm-yyyy') - to_date(BIRTHDATE, 'dd-mm-yyyy'))/365.242199 BETWEEN 20 AND 30 THEN 'A'
WHEN (to_date(SYSDATE, 'dd-mm-yyyy') - to_date(BIRTHDATE, 'dd-mm-yyyy'))/365.242199 BETWEEN 31 AND 40 THEN 'B'
WHEN (to_date(SYSDATE, 'dd-mm-yyyy') - to_date(BIRTHDATE, 'dd-mm-yyyy'))/365.242199 BETWEEN 41 AND 50 THEN 'C'
SWHEN (to_date(SYSDATE, 'dd-mm-yyyy') - to_date(BIRTHDATE, 'dd-mm-yyyy'))/365.242199 BETWEEN 51 AND 60 THEN 'D'
WHEN BIRTHDATE IS NULL THEN 'D'
END BIR
FROM EMP;




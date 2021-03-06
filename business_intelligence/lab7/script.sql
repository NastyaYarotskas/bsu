--1.
SELECT A.EMPNAME || ' works for ' || B.EMPNAME AS EMP_MANAGER
FROM EMP A, EMP B
WHERE A.MANAGER_ID = B.EMPNO

--2.
SELECT EMPNAME || ' reports to ' || PRIOR MANAGER_NAME
FROM EMP X
CONNECT BY PRIOR EMPNO = MANAGER_ID
START WITH MANAGER_ID IS NULL;

--3.
SELECT LTRIM(SYS_CONNECT_BY_PATH(EMPNAME, '->'), '->') LEAF_BRANCH_ROOT
FROM EMP
WHERE LEVEL = 3
START WITH EMPNAME = 'CLARK'
CONNECT BY PRIOR MANAGER_ID = EMPNO;

--4.
SELECT LTRIM(SYS_CONNECT_BY_PATH(EMPNAME, '->'), '->') EMP_TREE
FROM EMP
START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR EMPNO = MANAGER_ID;

--5.
SELECT LPAD('_', LEVEL - 1, '_') || EMPNAME ORG_CHART 
FROM EMP
START WITH MANAGER_ID IS NULL
CONNECT BY PRIOR EMPNO = MANAGER_ID;

--6.
SELECT EMPNAME
FROM EMP
START WITH EMPNAME = 'ALLEN'
CONNECT BY PRIOR EMPNO = MANAGER_ID;
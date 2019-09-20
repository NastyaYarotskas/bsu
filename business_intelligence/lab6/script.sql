--1--
SELECT EMPNAME, STARTDATE, 
STARTDATE + 10, STARTDATE - 10, 
ADD_MONTHS(STARTDATE, 6), ADD_MONTHS(STARTDATE, -6), 
ADD_MONTHS(STARTDATE, 12), ADD_MONTHS(STARTDATE, -12)
FROM CAREER JOIN EMP USING(EMPNO)
WHERE EMPNAME = 'JOHN KLINTON';

--2--
SELECT S2.STARTDATE - S1.STARTDATE
FROM (
SELECT STARTDATE 
FROM CAREER JOIN EMP USING(EMPNO)
WHERE EMPNAME = 'JOHN MARTIN') S1, 
(
SELECT STARTDATE 
FROM CAREER JOIN EMP USING(EMPNO)
WHERE EMPNAME = 'ALEX BOUSH') S2;

--3--
WITH DATES AS (
  SELECT SYSDATE D2, SYSDATE-365 D1
  FROM DUAL
)
SELECT FLOOR(MONTHS_BETWEEN(D2, D1) / 12) YY,
MONTHS_BETWEEN(D2, D1) MM
FROM DATES;

--4--
SELECT EMPNAME, LEAD (STARTDATE, 1) over (ORDER BY STARTDATE) - STARTDATE AS DIF
FROM CAREER JOIN EMP USING(EMPNO) JOIN DEPT USING(DEPTNO)
WHERE DEPTNO = 20;

--5--
SELECT STARTDATE, ADD_MONTHS(TRUNC(STARTDATE, 'Y'), 12) - TRUNC(STARTDATE, 'Y')
FROM CAREER;

--6--
SELECT TO_NUMBER(TO_CHAR(SYSDATE, 'HH24')) HOUR,
       TO_NUMBER(TO_CHAR(SYSDATE, 'MI')) MIN,
       TO_NUMBER(TO_CHAR(SYSDATE, 'SS')) SEC,
       TO_NUMBER(TO_CHAR(SYSDATE, 'DD')) DAY,
       TO_NUMBER(TO_CHAR(SYSDATE, 'MM')) MTH,
       TO_NUMBER(TO_CHAR(SYSDATE, 'YYYY')) YEAR
FROM DUAL;

--7--
SELECT TRUNC(SYSDATE, 'MM') FIRSTDAY,
LAST_DAY(SYSDATE) LASTDAY
FROM DUAL;

--8--
SELECT ROWNUM,
ADD_MONTHS(TRUNC(SYSDATE,'Y'), (ROWNUM - 1) * 3) Q_START,
ADD_MONTHS(TRUNC(SYSDATE,'Y'), ROWNUM * 3) Q_END
FROM EMP
WHERE ROWNUM <= 4;

--9--
SELECT *
FROM (select (trunc(sysdate,'y') + (LEVEL - 1)) DY
FROM DUAL
CONNECT BY LEVEL <= ADD_MONTHS(TRUNC(SYSDATE,'Y'),12) - TRUNC(SYSDATE,'Y'))
WHERE TO_CHAR( DY, 'DY') = 'MON';

--10--
with x
as (
select *
from (
select trunc(sysdate, 'mm')+level-1 month_date,
to_char(trunc(sysdate, 'mm')+level-1, 'iw') week_number,
to_char(trunc(sysdate, 'mm')+level-1, 'dd') day_number,
to_number(to_char(trunc(sysdate, 'mm')+level-1, 'd')) week_day,
to_char(trunc(sysdate, 'mm')+level-1, 'mm') curr_month,
to_char(sysdate, 'mm') month_number
from dual
connect by level <=31
)
where curr_month = month_number
)
select max(case week_day when 2 then day_number end) Mo,
max(case week_day when 3 then day_number end) Tu,
max(case week_day when 4 then day_number end) We,
max(case week_day when 5 then day_number end) Th,
max(case week_day when 6 then day_number end) Fr,
max(case week_day when 7 then day_number end) Sa,
max(case week_day when 1 then day_number end) Su
from x
group by week_number
order by week_number;
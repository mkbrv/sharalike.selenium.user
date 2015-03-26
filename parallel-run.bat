@ECHO OFF
SET /a count=%1
call mvn clean install

:loop
start  run.bat %2%
set /a count-=1
if %count% GTR 0 goto loop
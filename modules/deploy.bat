@echo off
SETLOCAL
if not defined GS_HOME set GS_HOME=%~dp0..\..
call %GS_HOME%\bin\gs.bat pu deploy my-pu-stateful %~dp0\target\my-pu-stateful-0.1.jar %*
@echo off

color 04

rem if "%mfrioenv_set%" == "true" goto end

rem ***************************************************************************************************
rem * Set base directory of the source tree
rem ***************************************************************************************************
set mfsrcdir=%~dp0

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_45
set NPM_HOME=D:\Program Files\nodejs
set GIT_HOME=C:\Program Files (x86)\Git\bin
set GIT_SSH=%GIT_HOME%\ssh.exe
set TOMCAT_HOME=C:\apache\apache-tomcat-7.0.23
set ANT_HOME=c:\apache\apache-ant-1.8.3
set GRADLE_HOME=C:\ProgramData\chocolatey\lib\gradle.2.2.1\tools\gradle-2.2.1\bin

rem ***************************************************************************************************
rem * Call the machine, or user specific locations if they exist 
rem ***************************************************************************************************
rem The machine environment file takes priority over the user file
rem ***************************************************************************************************
set envdir=%MFSRCDIR%Win\environment
set env2fileUser=%envdir%\setenv_%USERNAME%.bat
set env1fileMachine=%envdir%\setenv_%ComputerName%.bat

@echo ---------------------------
@echo machine environment file %env1fileMachine%
@echo user environment file env2fileUser=%env2fileUser%
@echo ---------------------------

if exist "%env1fileMachine%" (
    @echo ==============================================================
    @echo processing machine environment file %env1fileMachine%
    @echo ==============================================================
    call %env1fileMachine% %1
) else (
    if exist "%env2fileUser%" (        
        @echo ==============================================================
        @echo processing user environment file %env2fileUser%
        @echo ==============================================================
        call %env2fileUser% %1
    ) 
)

rem ***************************************************************************************************
rem * Validate environment so the user is reasonably confident they have the right software installed.
rem ***************************************************************************************************
set FAIL=

if not exist "%JAVA_HOME%\bin\javac.exe" (set FAIL=TRUE && echo ** ERROR: JAVA_HOME appears incorrect - "%JAVA_HOME%" )
if not exist "%NPM_HOME%\npm.cmd" (set FAIL=TRUE && echo ** ERROR: NPM_HOME appears incorrect - "%NPM_HOME%" )
if not exist "%GIT_HOME%\" (set FAIL=TRUE && echo ** ERROR: GIT_HOME appears incorrect - "%GIT_HOME%" )
if not exist "%GIT_SSH%" (set FAIL=TRUE && echo ** ERROR: GIT_SSH appears incorrect - "%GIT_SSH%" )
if not exist "%TOMCAT_HOME%\bin\startup.bat" (set FAIL=TRUE && echo ** ERROR: TOMCAT_HOME appears incorrect - "%TOMCAT_HOME%" )
if not exist "%ANT_HOME%\lib\ant.jar" (set FAIL=TRUE && echo ** ERROR: ANT_HOME appears incorrect - "%ANT_HOME%")

if "%FAIL%"=="" goto setenv
goto end

:setenv
title My Atlas Build and Run Environment
set PATH=%JAVA_HOME%\bin;%NPM_HOME%;%GIT_HOME%;%TOMCAT_HOME%\bin;%ANT_HOME%\bin;%GRADLE_HOME%;%PATH%;
set JAVA7_HOME=%JAVA_HOME%
set CATALINA_HOME=%TOMCAT_HOME%
set mfrioenv_set=true
echo Atlas Environment Set
color 02
:end

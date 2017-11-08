call setenv
@echo off
verify other 2>nul
setlocal EnableDelayedExpansion
if not %ERRORLEVEL% equ 0 (
    echo Unable to enable extensions
    goto failure
)


endlocal & set "SSH_AUTH_SOCK=%SSH_AUTH_SOCK%" ^
         & set "SSH_AGENT_PID=%SSH_AGENT_PID%"

rem Start the command window
call cmd %*
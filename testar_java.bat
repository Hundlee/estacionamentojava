@echo off
echo Verificando instalacao do Java...
echo.

java -version
if %errorlevel% neq 0 (
    echo.
    echo ERRO: Java nao encontrado!
    echo Por favor, instale o JDK e adicione ao PATH do sistema.
    echo.
    pause
    exit /b 1
)

echo.
echo Java encontrado! Compilando o projeto...
echo.

javac *.java

if %errorlevel% neq 0 (
    echo.
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo.
echo Compilacao concluida com sucesso!
echo.
echo Executando o sistema...
echo.

java Principal

pause



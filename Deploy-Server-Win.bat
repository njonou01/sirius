 @echo off

cls
title deploy-server Student's Social network
set "m2=%M2_REPO%"



ssh -i "%~dp0\socialSSN" toto@172.31.249.143 "mkdir -p .m2/repository/edu/ssn/ing1/sirius/ssn-backend/1.0-SNAPSHOT/"
scp -i "%~dp0\socialSSN" -rp "%~dp0\ssn-backend\target\ssn-backend-1.0-SNAPSHOT-jar-with-dependencies.jar" toto@172.31.249.143:.m2/repository/edu/ssn/ing1/sirius/ssn-backend/1.0-SNAPSHOT/
@REM scp -i "%~dp0\socialSSN" -rp "%m2%\repository\edu\ssn\ing1\sirius\ssn-backend\1.0-SNAPSHOT\ssn-backend-1.0-SNAPSHOT-jar-with-dependencies.jar" toto@172.31.249.143:.m2/repository/edu/ssn/ing1/sirius/ssn-backend/1.0-SNAPSHOT/
echo ◅◅◅◅◅◅◅◅◅◅◅◅  Le Serveur est correctement déployé ▻▻▻▻▻▻▻▻▻▻▻▻▻▻▻▻

@REM scp -i "C:\Users\FERNANDY ELOKA\socialSSN" -rp "%~dp0\main-backend-server.bat" toto@172.31.249.143:/usr/local/bin
scp -i "%~dp0\socialSSN" -p "%~dp0\main-backend-server.sh" toto@172.31.249.143:/tmp
ssh -t -i "%~dp0\socialSSN" toto@172.31.249.143 "chmod u+x /tmp/main-backend-server.sh && dos2unix /tmp/main-backend-server.sh && sudo mv /tmp/main-backend-server.sh /usr/local/bin/main-backend-server.sh"

echo ◅◅◅◅◅◅◅◅◅◅◅◅  Lanceur déployé ▻▻▻▻▻▻▻▻▻▻▻▻▻▻▻▻

echo Le répertoire actuel est : %~dp0

echo %m2%

pause


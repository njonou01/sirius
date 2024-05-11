#!/bin/bash

echo ${M2_REPO}

cd .m2/repository/edu/ssn/ing1/sirius/ssn-backend/1.0-SNAPSHOT/

exec java -jar ssn-backend-1.0-SNAPSHOT-jar-with-dependencies.jar
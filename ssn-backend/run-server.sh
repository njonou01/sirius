#!/bin/bash




# Variables
ip="172.31.252.91"
user="ssnserver"

ssh "$user@$ip" "pkill -f java && exit"
ssh "$user@$ip" "java -jar .m2/repository/edu/ssng/ing1/sirius/ssn-backend/1.0-SNAPSHOT/ssn-backend-1.0-SNAPSHOT-jar-with-dependencies.jar && exit"

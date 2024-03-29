#!/bin/bash

ip="172.31.252.91"
user="ssnserver"

m2="$HOME/.m2/repository"

m2_source_file="$m2/edu/ssng/ing1/sirius/ssn-backend/1.0-SNAPSHOT"

m2_destination_path=".m2/repository/edu/ssng/ing1/sirius/ssn-backend/"

main_backend_source_file="main-backend-server.sh"

main_backend_destination_path="ssn/ssn-backend/"

scp -rp $m2_source_file $user@$ip:$m2_destination_path || ssh "$user@$ip" "mkdir -p $m2_destination_path && scp $m2_source_file $user@$ip:$m2_destination_path && exit"

scp -p $main_backend_source_file $user@$ip:$main_backend_destination_path || ssh "$user@$ip" "mkdir -p $main_backend_destination_path && scp $main_backend_source_file $user@$ip:$main_backend_destination_path && exit"




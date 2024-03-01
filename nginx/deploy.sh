#!/bin/bash

IS_GREEN_EXIST=$(docker ps -a | grep green)

if [ -z $IS_GREEN_EXIST ]; then
  echo "### GREEN -> BLUE DEPLOYMENT ###"
  sudo docker-compose pull blue
  echo "### UP BLUE ###"
  sudo docker-compose up -d blue
  while [ 1 = 1 ]; do
      sleep 3
      REQUEST=$(curl http://localhost:8081)
      if [ -n "$REQUEST" ]; then
          echo "### HEALTH CHECK OK BLUE ###"
          break
      fi
  done
  sleep 3
  echo "### CHANGE NGINX CONFIG ###"
  sudo cp -f ./nginx/conf.d/blue-url.conf ./nginx/conf.d/service-url.conf
  echo "### RELOAD NGINX ###"
  sudo docker exec -it daagn_nginx nginx -s reload
  echo "### DOWN GREEN ###"
  sudo docker-compose down green

else
  echo "### BLUE -> GREEN DEPLOYMENT ###"
  sudo docker-compose pull green
  echo "### UP GREEN ###"
  sudo docker-compose up -d green
  while [ 1 = 1 ]; do
      sleep 3
      REQUEST=$(curl http://localhost:8080)
      if [ -n "$REQUEST" ]; then
          echo "### HEALTH CHECK OK GREEN ###"
          break
      fi
  done
  sleep 3
  echo "### CHANGE NGINX CONFIG ###"
  sudo cp -f ./nginx/conf.d/green-url.conf ./nginx/conf.d/service-url.conf
  echo "### RELOAD NGINX ###"
  sudo docker exec -it daagn_nginx nginx -s reload
  echo "### DOWN BLUE ###"
  sudo docker-compose down blue
fi

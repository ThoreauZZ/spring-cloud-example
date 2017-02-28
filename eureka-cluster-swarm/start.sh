#!/bin/bash
set -e
eureka_nodes=$(docker node ls |awk 'NR>1{print $1}'|tail -3)

i=0

if [ -f .env ]
then
	rm -f .env
fi

for node in ${eureka_nodes}
do
	i=`expr $i + 1`
	IP=`docker node inspect --format="{{.Status.Addr}}" ${node}`
	echo "NODE${i}_ID=${node}" >> .env
	echo "NODE${i}_IP=${IP}"   >> .env
done

docker-compose -f discovery-cluster.yml config > stack.yml
docker stack deploy -c stack.yml discovery
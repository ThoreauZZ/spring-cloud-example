#!/bin/bash

URL=$1
STAT=`curl -I -m 10 -o /dev/null -s -w %{http_code} ${URL}`

until [ ${STAT} -eq 200 ]
do	
	sleep 3
	STAT=`curl -I -m 10 -o /dev/null -s -w %{http_code} ${URL}`
	echo "${STAT}"
done


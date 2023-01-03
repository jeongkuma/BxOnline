#!/bin/bash

docker stop scp
docker rm scp
docker rmi $(docker images -f "dangling=true" -q)
docker build --tag scp:0.0.1 .

docker run --restart always -v /root/apps/files:/files -v /root/apps/scp/logs:/logs -itd -p 8800:8800 --env JAVA_OPTS='-Dspring.profiles.active=local -Dlogging.level.root=info -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8' -t --name scp scp:0.0.1
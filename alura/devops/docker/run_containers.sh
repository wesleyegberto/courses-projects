#!/bin/bash

docker run -d --name mysql -e MYSQL_ROOT_PASSWORD=teste123 -v $(pwd)/app/:/var/lib/mysql mysql
docker run -d --name blog_alura --link mysql:mysql -p 80:80 wordpress

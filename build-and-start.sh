!/bin/sh
docker build -t studres .
docker run -itd -p 8080:8080 -p 5432:5432 --name studrescont studres

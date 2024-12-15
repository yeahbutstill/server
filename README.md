# server

Back-End manajemen server


## Setup Database 
Karena sudah ditambahkan dependency spring boot docker compose dan scopenya runtime. jadi setupnya ada di file docker compose ([compose.yaml](compose.yaml)).
oh iya jangan lupa di skip test pas jalanin lewat terminal, kalau lewat idea langsung run aja
```shell
mvn clean install spring-boot:run -DskipTests
```
## Tanpa Docker Compose
* Run MySQL di docker
  ```bash
  docker run --rm \
  --name=server-db \
  -e MYSQL_DATABASE=serverdb \
  -e MYSQL_USER=servermanager \
  -e MYSQL_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
  -e MYSQL_ROOT_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
  -e TZ=Asia/Jakarta \
  -p 6603:3306 \
  -v "$PWD/docker/server-db/conf.d":/etc/mysql/conf.d \
  -v "$PWD/storage/docker/serverdb-data":/var/lib/mysql \
  mysql:8.4
  ```

* Login MySQL
  ```shell
  mysql -uroot -p -h127.0.0.1 -P6603 
  ```

* Creat user
  ```mysql
  CREATE USER 'servermanager'@'%' IDENTIFIED WITH mysql_native_password BY 'PNSJkxXvVNDAhePMuExTBuRR';
  ```

* Allow access database
  ```mysql
  GRANT ALL ON serverdb.* TO 'servermanager'@'%';
  ```
  exit;


* Try login again with new user
  ```shell
  mysql -uservermanager -p -h127.0.0.1 -P6603 
  ```

* Show database
  ```mysql
  SHOW DATABASES; 
  ```

## Dengan Docker Compose
```shell
docker compose up
```
setelah itu 
```shell
mvn clean install spring-boot:run
```
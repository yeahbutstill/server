# server

Back-End manajemen server

## Setup Database

* Run MySQL di docker
    ```bashpro shell script
  sudo docker run --rm \
  --name=server-db \
  -e MYSQL_DATABASE=serverdb \
  -e MYSQL_USER=servermanager \
  -e MYSQL_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
  -e MYSQL_ROOT_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
  -e TZ=Asia/Jakarta \
  -p 6603:3306 \
  -v "$PWD/docker/server-db/conf.d":/etc/mysql/conf.d \
  -v "$PWD/storage/docker/serverdb-data":/var/lib/mysql \
  mysql
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
  the output:
  ```shell
  +--------------------+
  | Database           |
  +--------------------+
  | information_schema |
  | invoicedb          |
  +--------------------+
  2 rows in set (0,00 sec) 
  ```

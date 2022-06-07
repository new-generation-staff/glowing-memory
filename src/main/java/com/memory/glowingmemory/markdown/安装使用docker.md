```shell
##docker官网
https://www.docker.com/get-started
1、下载对应的安装包
2、docker安装各种实例（mysql、redis之类）
  2.1下载mysql示例，命令行中输入: docker pull mysql:latest
  2.2查看是否安装号mysql，输入: docker images #安装成功会显示mysql
  2.3运行mysql容器，输入: docker run -itd --name mysql-test -p 3306:3306 -e MYSQL_ROOT_PASSWORD="" mysql
    2.3.1参数说明：-i:以交互模式运行容器，通常与 -t 同时使用, -t:为容器重新分配一个伪输入终端，通常与 -i 同时使用, -d: 后台运行容器，并返回容器ID, -p 3306:3306 ：映射容器服务的 3306 端口到宿主机的 3306 端口，外部主机可以直接通过 宿主机ip:3306 访问到 MySQL 的服务, MYSQL_ROOT_PASSWORD=123456：设置 MySQL 服务 root 用户的密码
  2.4查看mysql是否运行成功，输入: docker ps, IMAGE下有mysql为启动成功, 成功后可通过localhost 访问mysql，也可通过本地ip访问数据库  （留存好 CONTAINER ID）
  2.5关闭mysql容器,输入: docker stop "要关闭容器的CONTAINER ID"
  2.6启动容器，输入: docker stop "要运行容器的CONTAINER ID"
具体可查看以下
##菜鸟教程 docker
https://www.runoob.com/docker/windows-docker-install.html
```

```markdown
本地访问docker中的redis
docker exec -it redis-NAMES redis-cli
```

```shell
# docker 运行jar
1、新建文件夹
2、文件夹放入 Dockerfile文件和 打包好的jar包
    2.1 Dockerfile文件内容如下
     {
        FROM java:8    #基础镜像，当前新镜像是基于那个镜像， 像这里就是基于java 8环境
        ADD glowing-memory-0.0.1-SNAPSHOT.jar app.jar  #dglowing-memory-0.0.1-SNAPSHOT.jar为你SpringBoot打包最终的成包名称，别名为app.jar
        EXPOSE 8080   # 这个是你项目的要暴露的端口，你项目的端口是什么这里就写什么
        ENTRYPOINT ["java","-jar","/app.jar"]    #这句话相当于 java -jar app.jar
     }
3、构建镜像
    在存放Dockerfile和项目jar的目录下，执行以下的命令
    docker build -t glowing-memory .
4、启动容器
    docker run -d -p 8080:8080 --name glowing-memory glowing-memory
    4.1 如用到 mysql/redis 之类 启动时先启用 mysql、redis 等前置 再用如下命令
    docker run --name glowing-memory -d -p 8080:8080 --link onlinemysql:onlinemysql glowing-memory
        –link可以通过容器名互相通信，容器间共享环境变量。  --link后跟mysql的容器名
        –link主要用来解决两个容器通过ip地址连接时容器ip地址会变的问题
5、将已有的应用导出
    docker save -o glowing-memory.tar glowing-memory:latest
6、导入应用
    docker load -i glowing-memory.tar
7、更新jar包时 删除原来的镜像，重复执行 3、4步
```
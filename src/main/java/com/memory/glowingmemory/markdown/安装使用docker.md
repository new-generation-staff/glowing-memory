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

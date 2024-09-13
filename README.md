# springboot 整合 elasticsearch

## 步骤记录

### docker安装elasticsearch镜像 版本:7.17.24

```
docker pull bitnami/elasticsearch:7.17.24
```

### 启动

```
docker run -d -p 9200:9200 -p 9300:9300 --name=elasticsearch bitnami/elasticsearch:7.17.24
```

### 查看日志

```
docker logs -f --tail 200 elasticsearch
```

### 服务版本

+ [elasticsearch 7.17](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/install-elasticsearch.html)
+ Java17
+ springboot 3.0+

#### 参考链接

[Search API](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-search.html)

[BulkProcessor](https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-docs-bulk-processor.html)

## 安装 kafka

## 下载kafka服务

+ [kafka下载](https://kafka.apache.org/downloads)直接下载二进制

## 启动kafka

## 1.启动 Zookeeper：

```
bin/zookeeper-server-start.sh config/zookeeper.properties
```

### 2.启动 Kafka：

```
bin/kafka-server-start.sh config/server.properties
```

### 3.验证kafka

#### 3.1 创建topic

```bin/kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1```

#### 3.2 查看 topic 列表

```bin/kafka-topics.sh --list --bootstrap-server localhost:9092```

#### 3.3 描述 topic 信息

```bin/kafka-topics.sh --describe --topic my-topic --bootstrap-server localhost:9092```

#### 3.4 删除 topic

```bin/kafka-topics.sh --delete --topic my-topic --bootstrap-server localhost:9092```

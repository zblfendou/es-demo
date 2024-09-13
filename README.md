### springboot 整合 elasticsearch

### 步骤记录

# docker安装elasticsearch镜像 版本:7.17.24

```
docker pull bitnami/elasticsearch:7.17.24
```

# 启动

```
docker run -d -p 9200:9200 -p 9300:9300 --name=elasticsearch bitnami/elasticsearch:7.17.24
```

# 查看日志

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
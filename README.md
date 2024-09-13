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

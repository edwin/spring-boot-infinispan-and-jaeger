# Integrating Spring Boot 3, OpenTelemetry, Infinispan, and Jaeger

## Setting Up Jaeger
```
docker run -d \ 
        -e COLLECTOR_OTLP_ENABLED=true \ 
        -p 16686:16686 \ 
        -p 4317:4317 \
        -p 4318:4318 \ 
        jaegertracing/all-in-one:1.40
```

## Infinispan Cache Store
```xml
<distributed-cache name="balance" mode="SYNC" remote-timeout="30000" statistics="true">
    <encoding media-type="text/plain"/>
    <locking concurrency-level="1000" isolation="READ_COMMITTED" acquire-timeout="60000" striping="false"/>
    <transaction mode="NON_XA" auto-commit="true" stop-timeout="30000" locking="PESSIMISTIC" reaper-interval="30000" complete-timeout="30000" notifications="true"/>
    <state-transfer timeout="30000"/>
</distributed-cache>
```

## Infinispan Runtime Variable
```
JAVA_OPTS="$JAVA_OPTS -Dinfinispan.tracing.enabled=true -Dotel.service.name=infinispan-server -Dotel.exporter.otlp.endpoint=http://localhost:4317 -Dotel.metrics.exporter=none"
```

## Sample Log Files
This log file contains Trace ID and Span ID to make debugging easier
```
2024-02-29T19:51:04.282+07:00  INFO [app-testing,5554cccec43b54f0727d7fea6cc0d245,27349525f3064b89] 24032 --- [nio-8080-exec-1] com.edw.controller.IndexController       : request at / 
2024-02-29T19:51:04.282+07:00  INFO [app-testing,5554cccec43b54f0727d7fea6cc0d245,27349525f3064b89] 24032 --- [nio-8080-exec-1] com.edw.service.IndexService             : starting ====================
2024-02-29T19:51:04.291+07:00  INFO [app-testing,,] 24032 --- [-async-pool-1-1] org.infinispan.HOTROD                    : ISPN004006: Server sent new topology view (id=699949201, age=0) containing 1 addresses: [127.0.0.1/<unresolved>:11222]
2024-02-29T19:51:04.322+07:00  INFO [app-testing,5554cccec43b54f0727d7fea6cc0d245,27349525f3064b89] 24032 --- [nio-8080-exec-1] com.edw.service.IndexService             : done processing 712088c9-fc60-4ea1-a269-097887862371 - 0.9446111542600439 ====================
2024-02-29T19:51:07.974+07:00  INFO [app-testing,0fd6ee73cc9fa613a5ed276cf5b21206,aba3f957c8402ee1] 24032 --- [nio-8080-exec-5] com.edw.controller.IndexController       : request at / 
2024-02-29T19:51:07.974+07:00  INFO [app-testing,0fd6ee73cc9fa613a5ed276cf5b21206,aba3f957c8402ee1] 24032 --- [nio-8080-exec-5] com.edw.service.IndexService             : starting ====================
2024-02-29T19:51:07.980+07:00  INFO [app-testing,0fd6ee73cc9fa613a5ed276cf5b21206,aba3f957c8402ee1] 24032 --- [nio-8080-exec-5] com.edw.service.IndexService             : done processing 4c0790fe-e9e1-4c72-8a53-641f1c17cbbd - 0.7221371835398985 ====================
2024-02-29T19:51:10.073+07:00  INFO [app-testing,8a41c6156fbaf9f8e19d6cde8e74aa58,04d60c49d88fa273] 24032 --- [nio-8080-exec-2] com.edw.controller.IndexController       : request at / 
2024-02-29T19:51:10.074+07:00  INFO [app-testing,8a41c6156fbaf9f8e19d6cde8e74aa58,04d60c49d88fa273] 24032 --- [nio-8080-exec-2] com.edw.service.IndexService             : starting ====================
2024-02-29T19:51:10.079+07:00  INFO [app-testing,8a41c6156fbaf9f8e19d6cde8e74aa58,04d60c49d88fa273] 24032 --- [nio-8080-exec-2] com.edw.service.IndexService             : done processing 17cf17eb-0eee-420d-aa23-19fbd6cf3793 - 0.2734244284738404 ====================
```

## Blog Post
```
https://edwin.baculsoft.com/2024/02/distributed-tracing-with-spring-boot-infinispan-and-jaeger/
```
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
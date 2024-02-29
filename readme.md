# Integrating Spring Boot 3, OpenTelemetry, Infinispan, and Jaeger

## Setting Up Jaeger
```
docker run -d \ 
        -e COLLECTOR_OTLP_ENABLED=true \ 
        -p 16686:16686 \ 
        -p 4318:4318 \ 
        jaegertracing/all-in-one:1.40
```
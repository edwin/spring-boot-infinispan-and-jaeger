package com.edw.service;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <pre>
 *     com.edw.service.IndexService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 29 Feb 2024 13:11
 */
@Service
public class IndexService {

    @Autowired
    private RemoteCacheManager cacheManager;

    @Autowired
    private Tracer tracer;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void createData() {
        logger.info("starting ====================");

        // starting span
        Span span = tracer.spanBuilder()
                .name("cache-add")
                .tag("cache", "balance")
                .kind(Span.Kind.CLIENT).start();

        final RemoteCache cache = cacheManager.getCache("balance");

        // generate cache key
        String key = UUID.randomUUID().toString();

        // put it
        cache.put(key, Math.random());

        // get it -- somehow this is not monitored in Jaeger
        Double value = Double.parseDouble((String)cache.get(key));

        // and remove it
        cache.remove(key);

        span.end();

        logger.info("done processing {} - {} ====================", key, value);
    }
}

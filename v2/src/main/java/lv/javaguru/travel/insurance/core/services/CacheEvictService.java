package lv.javaguru.travel.insurance.core.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CacheEvictService {

    private final CacheManager cacheManagerV1;
    private final CacheManager cacheManagerV2;

    public CacheEvictService(@Qualifier("cacheManagerV1") CacheManager cacheManagerV1,
                             @Qualifier("cacheManagerV2") CacheManager cacheManagerV2) {
        this.cacheManagerV1 = cacheManagerV1;
        this.cacheManagerV2 = cacheManagerV2;
    }

    public void clearCache(String version, String cacheName) {
        CacheManager cacheManager = getCacheManager(version);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            throw new IllegalArgumentException("Cache with name " + cacheName + " not found");
        }
    }

    public void clearAllCaches(String version) {
        CacheManager cacheManager = getCacheManager(version);
        for (String cacheName : Objects.requireNonNull(cacheManager.getCacheNames())) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        }
    }

    private CacheManager getCacheManager(String version) {
        if ("v1".equalsIgnoreCase(version)) {
            return cacheManagerV1;
        } else if ("v2".equalsIgnoreCase(version)){
            return cacheManagerV2;
        }
        throw new IllegalArgumentException("Unknown version: " + version);
    }


}

package lv.javaguru.travel.insurance.rest.common;

import lv.javaguru.travel.insurance.core.services.CacheEvictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheEvictService cacheEvictService;

    @DeleteMapping("/{version}/{cacheName}")
    public ResponseEntity<String> clearCache(
            @PathVariable String version,
            @PathVariable String cacheName) {
        cacheEvictService.clearCache(version, cacheName);
        return ResponseEntity.ok("Cache '" + cacheName + "' cleared for version " + version);
    }

    @DeleteMapping("/{version}/all")
    public ResponseEntity<String> clearAllCaches(@PathVariable String version) {
        cacheEvictService.clearAllCaches(version);
        return ResponseEntity.ok("All caches cleared for version " + version);
    }
}


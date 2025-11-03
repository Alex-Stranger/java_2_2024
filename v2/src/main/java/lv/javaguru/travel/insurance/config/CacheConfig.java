package lv.javaguru.travel.insurance.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;


import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean ("cacheManagerV1")
    public CacheManager cacheManagerV1() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "tcAgeCoefficientV1",
                "tcCountrySafetyRatingCoefficientV1",
                "tcTravelCostCoefficientV1",
                "classifierV1",
                "classifierValueV1",
                "tmAgeCoefficientV1",
                "tmCountryDefaultDayRateV1",
                "tmMedicalRiskLimitLevelV1"
        );
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(500)
        );

        cacheManager.setAllowNullValues(true);
        return cacheManager;
    }

    @Bean ("cacheManagerV2")
    @Primary
    public CacheManager cacheManagerV2() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(
                "tcAgeCoefficientV2",
                "tcCountrySafetyRatingCoefficientV2",
                "tcTravelCostCoefficientV2",
                "classifierV2",
                "classifierValueV2",
                "tmAgeCoefficientV2",
                "tmCountryDefaultDayRateV2",
                "tmMedicalRiskLimitLevelV2"
        );
        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .maximumSize(500)
        );

        cacheManager.setAllowNullValues(true);
        return cacheManager;
    }

}


package lv.javaguru.travel.insurance.core.repositories.v2;

import lv.javaguru.travel.insurance.core.domain.Classifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassifierRepositoryV2 extends JpaRepository<Classifier, Long> {

    @Cacheable(value = "classifierV2", key = "#p0", cacheManager = "cacheManagerV2")
    Optional<Classifier> findByTitle(String title);
}

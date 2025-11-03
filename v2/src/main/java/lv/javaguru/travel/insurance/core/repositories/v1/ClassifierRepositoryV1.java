package lv.javaguru.travel.insurance.core.repositories.v1;

import lv.javaguru.travel.insurance.core.domain.Classifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassifierRepositoryV1 extends JpaRepository<Classifier, Long> {

    @Cacheable(value = "classifierV1", key = "#p0", cacheManager = "cacheManagerV1")
    Optional<Classifier> findByTitle(String title);
}

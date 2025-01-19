package org.example.galacticmarketplace.feature_toggle.aspect;

import org.example.galacticmarketplace.feature_toggle.FeatureToggleService;
import org.example.galacticmarketplace.feature_toggle.FeatureToggles;
import org.example.galacticmarketplace.feature_toggle.anotation.FeatureToggle;
import org.example.galacticmarketplace.feature_toggle.exception.FeatureToggleNotEnabledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {
    private final FeatureToggleService featureToggleService;

    @Before("@annotation(featureToggle)")
    public void checkFeatureToggle(FeatureToggle featureToggle) {
        FeatureToggles toggle = featureToggle.value();
        String featureName = featureToggle.value().getFeatureName();
        if (!featureToggleService.check(toggle.getFeatureName())) {
            log.warn("Feature toggle {} is not enabled!", featureName);
            throw new FeatureToggleNotEnabledException(featureName);
        }
    }
}


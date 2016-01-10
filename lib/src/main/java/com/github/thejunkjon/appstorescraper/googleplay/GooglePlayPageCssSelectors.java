package com.github.thejunkjon.appstorescraper.googleplay;

final class GooglePlayPageCssSelectors {

    private GooglePlayPageCssSelectors() {

    }

    static final String CSS_SELECTOR_APP_TITLE =
            "#body-content > div > div > div.main-content > div:nth-child(1) > div > div.details-info > div > div.info-box-top > h1 > div";

    static final String CSS_SELECTOR_APP_SUBTITLE =
            "#body-content > div > div > div.main-content > div:nth-child(1) > div > div.details-info > div > div.info-box-top > div:nth-child(3) > div.left-info > div > a.document-subtitle.primary > span";

    static final String CSS_SELECTOR_APP_CATEGORY =
            "#body-content > div > div > div.main-content > div:nth-child(1) > div > div.details-info > div > div.info-box-top > div:nth-child(3) > div.left-info > div > a.document-subtitle.category > span";

    static final String CSS_SELECTOR_APP_RATING =
            "div > div.score-container > div.score";

    static final String CSS_SELECTOR_APP_ADDITIONAL_INFORMATION =
            "#body-content > div > div > div.main-content > div:nth-child(4) > div > div.details-section-contents";

    static final String CSS_SELECTOR_APP_SCREENSHOTS =
            "div > div.details-section.screenshots";

    static final String CSS_SELECTOR_APP_BUY_OR_INSTALL_BUTTON =
            "#body-content > div > div > div.main-content > div:nth-child(1) > div > div.details-info > div > div.info-box-bottom > div > div.details-actions-right > span > span > button > span:nth-child(3)";

    static final String CSS_SELECTOR_APP_TITLE_IMAGE =
            "#body-content > div > div > div.main-content > div:nth-child(1) > div > div.details-info > div > div.cover-container > img";

    static final String CSS_SELECTOR_APP_TOTAL_REVIEWS_COUNT =
            "#body-content > div.outer-container > div > div.main-content > div.details-wrapper.apps > div.details-section.reviews > div.details-section-contents > div.rating-box > div.score-container > div.reviews-stats > span.reviews-num";

    static final String CSS_SELECTOR_APP_REVIEWS_HISTOGRAM =
            "#body-content > div.outer-container > div > div.main-content > div.details-wrapper.apps > div.details-section.reviews > div.details-section-contents > div.rating-box > div.rating-histogram";

    static final String CSS_SELECTOR_APP_DESCRIPTION =
            "#body-content > div > div > div.main-content > div:nth-child(1) > div > div.details-section.description.simple.contains-text-link";

    static final String CSS_SELECTOR_APP_WHATS_NEW =
            "#body-content > div > div > div.main-content > div:nth-child(3) > div";
}

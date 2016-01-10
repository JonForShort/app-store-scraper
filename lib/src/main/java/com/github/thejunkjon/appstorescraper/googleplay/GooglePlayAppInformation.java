package com.github.thejunkjon.appstorescraper.googleplay;

import java.util.List;
import java.util.Map;

final class GooglePlayAppInformation {

    String packageName;
    String title;
    String titleImageUrl;
    String subtitle;
    String category;
    String rating;
    Map<String, String> additionalInformation;
    List<String> thumbNailImageUrls;
    boolean isFree;
    int priceInCents;
    int numberOfTotalRatings;
    int numberOfFiveStarRatings;
    int numberOfFourStarRatings;
    int numberOfThreeStarRatings;
    int numberOfTwoStarRatings;
    int numberOfOneStarRatings;
    List<String> whatsNew;
    String description;
    int status;

}

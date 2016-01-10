package com.github.thejunkjon.appstorescraper.googleplay;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

import static com.github.thejunkjon.appstorescraper.googleplay.GooglePlayPageCssSelectors.*;

public final class GooglePlayInformationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(GooglePlayInformationProvider.class);

    private static final String CHROME_USER_AGENT =
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36";

    private static final String GOOGLE_PLAY_APP_URL_FORMAT =
            "https://play.google.com/store/apps/details?id=%s";


    private static String buildUrlForGooglePlayApp(final String packageName) {
        return String.format(Locale.US, GOOGLE_PLAY_APP_URL_FORMAT, packageName);
    }


    private static String parseForText(final Document document, final String cssSelector) {
        try {
            return document.select(cssSelector).first().ownText();
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return "";
        }
    }

    private static String parseForImage(final Document document, final String cssSelector) {
        try {
            return document.select(cssSelector).first().attr("src");
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return "";
        }
    }

    private static String parseForTitleImageUrl(final Document document) {
        return parseForImage(document, CSS_SELECTOR_APP_TITLE_IMAGE);
    }

    private static String parseForTitle(final Document document) {
        return parseForText(document, CSS_SELECTOR_APP_TITLE);
    }

    private static String parseForSubTitle(final Document document) {
        return parseForText(document, CSS_SELECTOR_APP_SUBTITLE);
    }

    private static String parseForRating(final Document document) {
        return parseForText(document, CSS_SELECTOR_APP_RATING);
    }

    private static String parseForCategory(final Document document) {
        return parseForText(document, CSS_SELECTOR_APP_CATEGORY);
    }

    private static List<String> parseForThumbnailImageUrls(final Document document) {
        final List<String> urls = new ArrayList<String>();
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_SCREENSHOTS).select("div.thumbnails").first().getElementsByTag("img").select(".screenshot");
            for (final Element element : elements) {
                urls.add(element.attr("src"));
            }
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
        }
        return urls;
    }

    private static int parseForPriceInCents(final Document document) {
        try {
            final String buttonText = document.select(CSS_SELECTOR_APP_BUY_OR_INSTALL_BUTTON).first().text();
            if ("Install".equals(buttonText)) {
                return 0;
            } else {
                final String priceAsString = buttonText.replace("Buy", "").trim();
                if (priceAsString.contains(".")) {
                    final NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
                    final float priceAsFloat = numberFormatter.parse(priceAsString).floatValue();
                    return Math.round(priceAsFloat * 100);
                } else {
                    return Integer.parseInt(priceAsString);
                }
            }
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static Map<String, String> parseForAdditionalInformation(final Document document) {
        final Map<String, String> additionalInformation = new HashMap<String, String>();
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_ADDITIONAL_INFORMATION);
            if (elements.size() > 0) {
                for (final Element element : elements.first().children()) {
                    final Elements titleElements = element.select("div.title");
                    final Elements contentElements = element.select("div.content");
                    if (titleElements.size() > 0 && contentElements.size() > 0) {
                        additionalInformation.put(titleElements.first().ownText(),
                                contentElements.first().ownText());
                    }
                }
            }
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
        }
        return additionalInformation;
    }

    private static int parseforNumberOfTotalRatings(final Document document) {
        try {
            return Integer.parseInt(document.select(CSS_SELECTOR_APP_TOTAL_REVIEWS_COUNT).first().ownText().replace("(", "").replace(")", "").trim());
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static int parseForNumberOfFiveStarRatings(final Document document) {
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_REVIEWS_HISTOGRAM);
            return Integer.parseInt(elements.select("span.bar-number").get(0).ownText());
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static int parseForNumberOfFourStarRatings(final Document document) {
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_REVIEWS_HISTOGRAM);
            return Integer.parseInt(elements.select("span.bar-number").get(1).ownText());
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static int parseForNumberOfThreeStarRatings(final Document document) {
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_REVIEWS_HISTOGRAM);
            return Integer.parseInt(elements.select("span.bar-number").get(2).ownText());
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static int parseForNumberOfTwoStarRatings(final Document document) {
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_REVIEWS_HISTOGRAM);
            return Integer.parseInt(elements.select("span.bar-number").get(3).ownText());
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static int parseForNumberOfOneStarRatings(final Document document) {
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_REVIEWS_HISTOGRAM);
            return Integer.parseInt(elements.select("span.bar-number").get(4).ownText());
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return -1;
        }
    }

    private static List<String> parseForWhatsNew(final Document document) {
        final List<String> whatsNewPieces = new ArrayList<String>();
        try {
            final Elements elements = document.select(CSS_SELECTOR_APP_WHATS_NEW).select("div.recent-change");
            for (final Element element : elements) {
                whatsNewPieces.add(element.ownText());
            }
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
        }
        return whatsNewPieces;
    }

    private static String parseForDescription(final Document document) {
        try {
            return document.select(CSS_SELECTOR_APP_DESCRIPTION).select("div.id-app-orig-desc").text();
        } catch (final Throwable t) {
            LOGGER.error(t.getMessage());
            return "";
        }
    }

    public String getAppInformation(final String packageName) {
        final String absoluteUrl = buildUrlForGooglePlayApp(packageName);
        final Document document;
        final GooglePlayAppInformation googlePlayAppInformation = new GooglePlayAppInformation();
        try {
            document = Jsoup.connect(absoluteUrl).userAgent(CHROME_USER_AGENT).get();
            googlePlayAppInformation.packageName = packageName;
            googlePlayAppInformation.title = parseForTitle(document);
            googlePlayAppInformation.titleImageUrl = parseForTitleImageUrl(document);
            googlePlayAppInformation.subtitle = parseForSubTitle(document);
            googlePlayAppInformation.category = parseForCategory(document);
            googlePlayAppInformation.rating = parseForRating(document);
            googlePlayAppInformation.additionalInformation = parseForAdditionalInformation(document);
            googlePlayAppInformation.thumbNailImageUrls = parseForThumbnailImageUrls(document);
            googlePlayAppInformation.priceInCents = parseForPriceInCents(document);
            googlePlayAppInformation.isFree = googlePlayAppInformation.priceInCents == 0;
            googlePlayAppInformation.numberOfTotalRatings = parseforNumberOfTotalRatings(document);
            googlePlayAppInformation.numberOfFiveStarRatings = parseForNumberOfFiveStarRatings(document);
            googlePlayAppInformation.numberOfFourStarRatings = parseForNumberOfFourStarRatings(document);
            googlePlayAppInformation.numberOfThreeStarRatings = parseForNumberOfThreeStarRatings(document);
            googlePlayAppInformation.numberOfTwoStarRatings = parseForNumberOfTwoStarRatings(document);
            googlePlayAppInformation.numberOfOneStarRatings = parseForNumberOfOneStarRatings(document);
            googlePlayAppInformation.whatsNew = parseForWhatsNew(document);
            googlePlayAppInformation.description = parseForDescription(document);
            googlePlayAppInformation.status = 0;
        } catch (final IOException e) {
            LOGGER.error(e.getMessage());
            googlePlayAppInformation.status = -1;
        }
        return new Gson().toJson(googlePlayAppInformation);
    }
}

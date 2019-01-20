package com.thai.scraping.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ElementHelper {

    public static final String NA = "NA";

    public static String getAbsUrlString(Element productItem, String cssQuery) {
        Elements elements = productItem.select(cssQuery);
        if (elements.isEmpty()) {
            return "NA";
        } else {
            return elements.first().absUrl("href");
        }
    }

    public static String getAttrString(Element productItem, String attribute) {
        String productId = productItem.attr(attribute);
        if (StringUtils.isNotEmpty(productId)) {
            return productId;
        } else {
            return "NA";
        }
    }

    public static String getTextString(Element productItem, String cssQuery) {
        Elements elements = productItem.select(cssQuery);
        if (elements.isEmpty()) {
            return "NA";
        } else {
            return elements.first().text();
        }
    }
}

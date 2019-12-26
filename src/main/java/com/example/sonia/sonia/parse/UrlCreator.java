package com.example.sonia.sonia.parse;

import com.example.sonia.sonia.service.impl.ScheduledUpdatorService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class UrlCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledUpdatorService.class);

    private static final String URL = "https://www.olx.ua/uk/";
    private static final String Q_FORMAT = "/q-%s/";
    private static final String PAGE = "?page=";
    private static final Pattern PAGE_PATTERN = Pattern.compile("/?page(//d+)");

    public static String buildUrl(String q, String city, Integer page) {
        return URL + city + format(Q_FORMAT, q) + PAGE + page;
    }

    public static boolean isValid(String url, String q) {
        String nextUrl;
        try {
            Connection document = Jsoup.connect(url);
            LOGGER.info("URL : {} with code {} ", document.get().html());
            nextUrl = document.get().location();
        } catch (Exception e) {
            return false;
        }
        return nextUrl.contains(format(Q_FORMAT, q));
    }
}

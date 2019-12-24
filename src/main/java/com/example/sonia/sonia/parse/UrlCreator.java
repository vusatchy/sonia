package com.example.sonia.sonia.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class UrlCreator {

    private static final String URL = "https://www.olx.ua/uk/";
    private static final String Q_FORMAT = "/q-%s/";
    private static final String PAGE = "?page=";
    private static final Pattern PAGE_PATTERN = Pattern.compile("/?page(//d+)");

    public static String buildUrl(String q, String city, Integer page) {
        return URL + city + format(Q_FORMAT, q) + PAGE + page;
    }

    public static boolean isValid(String url, String q) throws IOException {
        String nextUrl;
        try {
            nextUrl = Jsoup.connect(url).get().location();
        } catch (Exception e) {
            return false;
        }
        return nextUrl.contains(format(Q_FORMAT, q));
    }
}

package com.example.sonia.sonia.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Pattern;

public class UrlCreator {

    private static final String URL = "https://www.olx.ua/uk/elektronika/igry-i-igrovye-pristavki/igry-dlya-pristavok/sony-playstation/";
    private static final String PS_4 = "/q-ps4/";
    private static final String PAGE = "/?page=";
    private static final Pattern PAGE_PATTERN = Pattern.compile("/?page(//d+)");

    public static String buildUrl(String city, Integer page) {
        return URL + city + PS_4 + PAGE + page;
    }

    public static boolean isValid(String url) throws IOException {
        String nextUrl = Jsoup.connect(url).get().location();
        return nextUrl.contains(PS_4);
    }
}

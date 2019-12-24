package com.example.sonia.sonia.parse;

import com.example.sonia.sonia.model.Item;
import com.example.sonia.sonia.util.Tokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HtmlPageToItemCollector {

    private static final String T_BODY_TAG = "tbody";
    private static final String TD_TAG = "td";
    private static final String HREF_ATTRIBUTE = "href";
    private static final String SRC_ATTRIBUTE = "src";
    private static final int REQUIRED_PARAMS_COUNT = 3;
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final Pattern PRICE_PATTERN = Pattern.compile("\\d+(,|.|\\s)\\d+");

    public static List<Item> parseItems(String url, String token) throws IOException {
        List<Item> items = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        document.getElementsByTag(T_BODY_TAG).forEach(element1 -> {
            List<String> elements = new ArrayList<>(element1.getElementsByTag(TD_TAG)).stream()
                    .map(Element::text)
                    .filter(el -> !StringUtils.isEmpty(el))
                    .collect(Collectors.toList());
            if (elements.size() == REQUIRED_PARAMS_COUNT) {
                String pictureHref = getAttributeValue(SRC_ATTRIBUTE, element1);
                String href = getAttributeValue(HREF_ATTRIBUTE, element1);
                Item item = new Item();
                item.setHref(href);
                item.setImg(pictureHref);
                item.setName(elements.get(NAME_INDEX));
                item.setPrice(parsePrice(elements.get(PRICE_INDEX)));
                items.add(item);
            }
        });
        return validateItems(items, token);
    }

    private static List<Item> validateItems(List<Item> items, String token) {
        return items.stream().filter(item -> checkContains(item, token)).collect(Collectors.toList());
    }

    private static boolean checkContains(Item item, String searchPhrase) {
        Set<String> nameTokens = Tokenizer.tokenize(item.getName().toLowerCase());
        Set<String> searchTokens = Tokenizer.tokenize(searchPhrase.toLowerCase());
        return searchTokens.stream().allMatch(token -> {
          return nameTokens.stream().anyMatch(nameToken -> nameToken.contains(token));
        });
    }

    private static Integer parsePrice(String price) {
        Matcher matcher = PRICE_PATTERN.matcher(price);
        if (matcher.find()) {
            String priceParsed = matcher.group();
            priceParsed = priceParsed.replaceAll(" ", "");
            return safeIntegerParse(priceParsed);
        } else {
            return null;
        }
    }

    private static Integer safeIntegerParse(String price) {
        try {
            return Integer.valueOf(price);
        } catch (Exception e) {
            return null;
        }
    }

    private static String getAttributeValue(String key, Element element) {
        Elements source = element.getElementsByAttribute(key);
        return source.stream().map(el -> el.attr(key)).findFirst().get();
    }

}

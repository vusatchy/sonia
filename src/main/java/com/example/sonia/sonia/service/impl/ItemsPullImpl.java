package com.example.sonia.sonia.service.impl;

import com.example.sonia.sonia.model.Item;
import com.example.sonia.sonia.parse.HtmlPageToItemCollector;
import com.example.sonia.sonia.parse.UrlCreator;
import com.example.sonia.sonia.service.ItemsPull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemsPullImpl implements ItemsPull {

    public static final String SPACE_REPLACEMENT = "-";

    @Value("${app.city}")
    private String city;

    @Value("#{'${app.items}'.split(',')}")
    private List<String> searchItems;

    @Override
    public List<Item> getAllGames() throws IOException {
        List<Item> items = new ArrayList<>();
        for (String searchItem : searchItems) {
            String searchItemProcessed = preProcessSearchToken(searchItem);
            int page = 1;
            String url = UrlCreator.buildUrl(searchItemProcessed, city, page);
            while (UrlCreator.isValid(url, searchItemProcessed)) {
                items.addAll(HtmlPageToItemCollector.parseItems(url, searchItem));
                page++;
                url = UrlCreator.buildUrl(searchItem, city, page);
            }
        }
        return items;
    }

    private String preProcessSearchToken(String token) {
        return token.replaceAll("\\s+", SPACE_REPLACEMENT);
    }

}

package com.example.sonia.sonia.parse;

import com.example.sonia.sonia.model.VideoGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HtmlPageToGameCollector {

    private static final String T_BODY_TAG = "tbody";
    private static final String TD_TAG = "td";
    private static final String HREF_ATTRIBUTE = "href";
    private static final String SRC_ATTRIBUTE = "src";
    private static final int REQUIRED_PARAMS_COUNT = 3;
    private static final int NAME_INDEX = 0;
    private static final int PRICE_INDEX = 1;
    private static final String PHRASE_TO_DELETE = "Ігри та ігрові приставки » Ігри для приставок";
    private static final Pattern PRICE_PATTERN = Pattern.compile("\\d+(,|.|\\s)\\d+");

    public static List<VideoGame> parseGames(String url) throws IOException {
        List<VideoGame> videoGames = new ArrayList<>();
        Document document = Jsoup.connect(url).get();
        document.getElementsByTag(T_BODY_TAG).forEach(element1 -> {
            List<String> elements = new ArrayList<>(element1.getElementsByTag(TD_TAG)).stream()
                .map(Element::text)
                .filter(el -> !StringUtils.isEmpty(el))
                .collect(Collectors.toList());
            if (elements.size() == REQUIRED_PARAMS_COUNT) {
                String pictureHref = getAttributeValue(SRC_ATTRIBUTE, element1);
                String href = getAttributeValue(HREF_ATTRIBUTE, element1);
                VideoGame videoGame = new VideoGame();
                videoGame.setHref(href);
                videoGame.setPictureHref(pictureHref);
                videoGame.setName(elements.get(NAME_INDEX));
                videoGame.setPrice(elements.get(PRICE_INDEX));
                videoGames.add(videoGame);
            }
        });
        return videoGames;
    }

    public static List<VideoGame> validateGames(List<VideoGame> videoGames){
        return videoGames.stream().map(HtmlPageToGameCollector::videoGameMapper).collect(Collectors.toList());
    }

    private static VideoGame videoGameMapper(VideoGame videoGame){
        videoGame.setName(videoGame.getName().replaceAll(PHRASE_TO_DELETE,""));
        Matcher matcher = PRICE_PATTERN.matcher(videoGame.getPrice());
        if(matcher.find()){
            String price = matcher.group();
            price.replaceAll(" ",".");
            videoGame.setPrice(price);
        }
        else {
            videoGame.setPrice(null);
        }
        return videoGame;
    }

    private static String getAttributeValue(String key, Element element) {
        Elements source = element.getElementsByAttribute(key);
        return source.stream().map(el -> el.attr(key)).findFirst().get();
    }

}

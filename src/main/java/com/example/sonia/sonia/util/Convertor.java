package com.example.sonia.sonia.util;

import com.example.sonia.sonia.model.VideoGame;

public class Convertor {

    public static String gameToText(VideoGame game) {
        StringBuilder stringBuilder = new StringBuilder()
            .append("Name: ")
            .append(game.getName())
            .append("\n")
            .append("Price: ")
            .append(game.getPrice())
            .append("\n")
            .append("Href: ")
            .append(game.getHref());
        return stringBuilder.toString();
    }

}

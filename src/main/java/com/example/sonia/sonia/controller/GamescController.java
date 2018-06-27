package com.example.sonia.sonia.controller;

import com.example.sonia.sonia.model.VideoGame;
import com.example.sonia.sonia.repository.VideoGamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GamescController {

    @Value("#{'${app.games}'.split(',')}")
    private List<String> names;

    @Autowired
    private VideoGamesRepository videoGamesRepository;

    @GetMapping(value = "/games")
    public String games(Model model, @RequestParam(name = "game", defaultValue = "god of war") String game) {
        List<VideoGame> games = videoGamesRepository.findByNameContainingIgnoreCaseOrderByPriceAsc(game);
        model.addAttribute("names", names);
        model.addAttribute("games", games);
        return "games";
    }
}

package com.example.sonia.sonia.service;

import com.example.sonia.sonia.model.Item;

import java.io.IOException;
import java.util.List;

public interface ItemsPull {

    List<Item> getAllGames() throws IOException;
}

package com.example.sonia.sonia.repository;

import com.example.sonia.sonia.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemsRepository extends CrudRepository<Item, Integer> {

    List<Item> findByNameContainingIgnoreCaseOrderByPriceDesc(String name);

    List<Item> findByNameContainingIgnoreCaseOrderByPriceAsc(String name);
}

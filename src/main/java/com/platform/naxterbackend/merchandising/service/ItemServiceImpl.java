package com.platform.naxterbackend.merchandising.service;

import com.platform.naxterbackend.merchandising.model.Item;
import com.platform.naxterbackend.merchandising.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getItems() {
        return this.itemRepository.findAll();
    }
}

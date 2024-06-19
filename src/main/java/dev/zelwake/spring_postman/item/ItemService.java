package dev.zelwake.spring_postman.item;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public boolean saveItem(ItemDTO item, UUID invoiceId) {
        try {
            itemRepository.save(new Item(null, item.name(), item.priceInCents(), item.amount(), invoiceId));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean saveItems(List<ItemDTO> items, UUID invoiceId) {
        try {
            List<Item> newItems = items.stream().map(i -> new Item(null, i.name(), i.priceInCents(), i.amount(), invoiceId)).toList();
            itemRepository.saveAll(newItems);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}

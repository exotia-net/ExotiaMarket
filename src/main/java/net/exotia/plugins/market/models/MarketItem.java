package net.exotia.plugins.market.models;

import net.exotia.plugins.market.models.enums.ItemState;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public record MarketItem(UUID uuid, UUID owner, ItemState state, ItemStack item, double price, long created, long expireIn) {
    public void test() {
        System.out.println("re");
    }
}

package net.exotia.plugins.market.services;

import net.exotia.developer.kit.utils.serializers.ItemStackSerializer;
import net.exotia.plugins.market.configuration.PluginConfiguration;
import net.exotia.plugins.market.database.DatabaseService;
import net.exotia.plugins.market.models.MarketItem;
import net.exotia.plugins.market.models.enums.ItemState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MarketService {
    private final List<MarketItem> items = new ArrayList<>();
    private final PluginConfiguration configuration;

    public MarketService(PluginConfiguration configuration) {
        this.configuration = configuration;
    }

    public void register(MarketItem marketItem) {
        this.items.add(marketItem);
    }
    public void register(ResultSet resultSet) throws SQLException {
        MarketItem marketItem = new MarketItem(
                UUID.fromString(resultSet.getString("uuid")),
                UUID.fromString(resultSet.getString("owner")),
                ItemState.valueOf(resultSet.getString("state")),
                ItemStackSerializer.fromBase64(resultSet.getString("item"))[0],
                resultSet.getDouble("price"),
                resultSet.getLong("created"),
                resultSet.getLong("expireIn")
        );
        this.register(marketItem);
    }

    public List<MarketItem> getItems() {
        return items;
    }
    public List<MarketItem> getItems(ItemState itemState) {
        return this.items.stream().filter(marketItem -> marketItem.state().equals(itemState)).toList();
    }
    public List<MarketItem> getPlayerItems(Player player, ItemState itemState) {
        return this.items.stream()
                .filter(item -> item.uuid().equals(player.getUniqueId()))
                .filter(item -> item.state().equals(itemState))
                .toList();
    }

    public int getServicesLimit(Player player) {
        return this.getLimit(this.configuration.servicesLimit, "exotia.market.services.limit.", player);
    }

    private AtomicInteger getDefaultValue(Map<String, Integer> map) {
        if (map.get("default") != null) return new AtomicInteger(map.get("default"));
        return new AtomicInteger(map.get(map.keySet().iterator().next()));
    }
    private int getLimit(Map<String, Integer> map, String prefix, Player player) {
        AtomicInteger limit = this.getDefaultValue(map);
        map.forEach((key, value) -> {
            if (player.hasPermission(prefix+key)) {
                if (value > limit.get()) limit.set(value);
            }
        });
        return limit.get();
    }

    public boolean reachedLimit(Player player) {
        return this.getPlayerItems(player, ItemState.AVAILABLE).size() >= this.getServicesLimit(player);
    }

    public MarketItem createItem(ItemStack itemStack, int price, Player player) {
        itemStack.setItemMeta(itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType()));
        MarketItem marketItem = MarketItem.create(player, itemStack, price, this.configuration);
        this.items.add(marketItem);
        return marketItem;
    }
}

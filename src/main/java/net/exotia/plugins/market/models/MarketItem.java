package net.exotia.plugins.market.models;

import net.exotia.developer.kit.utils.serializers.ItemStackSerializer;
import net.exotia.plugins.market.configuration.PluginConfiguration;
import net.exotia.plugins.market.models.enums.ItemState;
import net.exotia.plugins.market.utils.TimeUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public record MarketItem(UUID uuid, UUID owner, ItemState state, ItemStack item, double price, long created, long expireIn) {
    public static MarketItem create(Player player, ItemStack item, int price, PluginConfiguration configuration) {
        return new MarketItem(
                UUID.randomUUID(),
                player.getUniqueId(),
                ItemState.AVAILABLE,
                item, price,
                System.currentTimeMillis(),
                System.currentTimeMillis() + TimeUtil.timeFromString(configuration.itemExpireIn)
        );
    }
}

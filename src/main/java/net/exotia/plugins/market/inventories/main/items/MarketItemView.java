package net.exotia.plugins.market.inventories.main.items;

import net.exotia.plugins.market.inventories.main.MainInventoryConfiguration;
import net.exotia.plugins.market.models.MarketItem;
import net.exotia.plugins.market.models.enums.ItemState;
import net.exotia.plugins.market.utils.TimeUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class MarketItemView extends AbstractItem {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final MainInventoryConfiguration configuration;
    private final MarketItem marketItem;

    public MarketItemView(MainInventoryConfiguration mainInventoryConfiguration, MarketItem marketItem) {
        this.configuration = mainInventoryConfiguration;
        this.marketItem = marketItem;
    }

    @Override
    public ItemProvider getItemProvider() {
        ItemStack itemStack = this.marketItem.item().clone();
        ItemMeta meta = itemStack.getItemMeta();
        List<Component> lore = this.implementsVariables(this.configuration.viewItemLore, this.marketItem);
        meta.lore(meta.hasLore() ? Stream.of(meta.lore(), lore).flatMap(Collection::stream).toList() : lore);
        itemStack.setItemMeta(meta);
        return new ItemBuilder(itemStack);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {

    }

    private List<Component> implementsVariables(List<String> messages, MarketItem itemElement){
        List<Component> result = new ArrayList<>();
        messages.forEach(message -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(itemElement.uuid());
            result.add(this.miniMessage.deserialize(message
                    .replace("{owner}", offlinePlayer.getName() == null ? "Offline" : offlinePlayer.getName())
                    .replace("{item_name}", itemElement.item().getType().name())
                    .replace("{price}", String.valueOf(itemElement.price()))
                    .replace("{created}", TimeUtil.formatDate(itemElement.created()))
                    .replace("{expireIn}", TimeUtil.formatDate(itemElement.expireIn()))
            ));
        });
        return result;
    }
}

package net.exotia.plugins.market.inventories.main;

import net.exotia.plugins.market.inventories.GuiType;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventory;
import net.exotia.plugins.market.inventories.main.items.MarketItemView;
import net.exotia.plugins.market.models.enums.ItemState;
import net.exotia.plugins.market.services.MarketService;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class MainInventory extends AbstractInventory<MainInventoryConfiguration> {
    private final MarketService marketService;

    public MainInventory(MarketService marketService) {
        super(MainInventoryConfiguration.class);
        this.marketService = marketService;
    }

    @Override
    public String inventoryId() {
        return "main";
    }

    @Override
    protected GuiType guiType() {
        return GuiType.NORMAL;
    }

    @Override
    public List<Item> content() {
        return this.marketService.getItems(ItemState.AVAILABLE).stream()
                .filter(item -> !item.item().getType().equals(Material.AIR))
                .map(item -> new MarketItemView(this.configuration, item))
                .collect(Collectors.toList());
    }

    @Override
    protected void initialize(Gui.Builder<?, ?> builder) {

    }
}

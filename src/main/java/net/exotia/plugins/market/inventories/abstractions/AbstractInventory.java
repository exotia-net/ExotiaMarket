package net.exotia.plugins.market.inventories.abstractions;

import net.exotia.plugins.market.inventories.GuiType;
import net.exotia.plugins.market.inventories.InventoryService;
import net.exotia.plugins.market.inventories.exceptions.InvalidInventoryTypeException;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.function.BiConsumer;

public abstract class AbstractInventory<T extends AbstractInventoryConfiguration> {
    protected T configuration;
    private final Class<T> configurationClass;
    protected InventoryService inventoryService;

    public AbstractInventory(Class<T> configurationClass) {
        this.configurationClass = configurationClass;
    }

    public abstract String inventoryId();
    protected abstract GuiType guiType();
    public abstract List<Item> content();
    protected abstract void initialize(Gui.Builder<?, ?> builder);

    public Class<?> inventoryClass() {
        return this.getClass();
    }

    public void setConfiguration(AbstractInventoryConfiguration configuration) {
        this.configuration = (T) configuration;
    }
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Class<T> configurationClass() {
        return this.configurationClass;
    }

    private void applyItems(BiConsumer<Character, AbstractItem> consumer) {
        this.configuration.getItems().forEach((key, item) -> {
            consumer.accept(key, new AbstractItem() {
                @Override
                public ItemProvider getItemProvider() {
                    return new ItemBuilder(item.item.getItem());
                }
                @Override
                public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent event) {
                    player.sendMessage(String.join(", ", item.actions));
                }
            });
        });
    }

    public void open(Player player) {
        Gui.Builder<?, ?> builder;
        switch (this.guiType()) {
            case NORMAL -> builder = Gui.normal();
            case PAGED -> builder = PagedGui.items().setContent(this.content());
            default -> throw new InvalidInventoryTypeException();
        }
        builder.setStructure(this.configuration.getPattern()).addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL);
        this.applyItems(builder::addIngredient);
        this.initialize(builder);

        Window window = Window.single()
                .setViewer(player)
                .setGui(builder.build())
                .setTitle(this.configuration.getTitle())
                .build();
        window.open();
    }
}

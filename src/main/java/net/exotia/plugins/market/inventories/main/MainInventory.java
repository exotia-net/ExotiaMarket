package net.exotia.plugins.market.inventories.main;

import net.exotia.plugins.market.inventories.GuiType;
import net.exotia.plugins.market.inventories.abstractions.AbstractInventory;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;

public class MainInventory extends AbstractInventory<MainInventoryConfiguration> {
    public MainInventory() {
        super(MainInventoryConfiguration.class);
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
    protected Gui initialize(Gui.Builder<?, ?> builder) {
        Gui gui = builder.build();
        return null;
    }
}

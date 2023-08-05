package net.exotia.plugins.market.commands;

import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import net.exotia.plugins.market.inventories.InventoryService;
import net.exotia.plugins.market.inventories.main.MainInventory;
import org.bukkit.entity.Player;

@Route(name = "market")
public class MarketCommand {
    private final InventoryService inventoryService;

    public MarketCommand(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Execute
    public void execute(Player player) {
        this.inventoryService.inventory(MainInventory.class).open(player);
    }
}

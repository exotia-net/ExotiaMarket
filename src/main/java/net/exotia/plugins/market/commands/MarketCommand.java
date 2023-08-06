package net.exotia.plugins.market.commands;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import net.exotia.plugins.market.configuration.MessagesConfiguration;
import net.exotia.plugins.market.database.DatabaseService;
import net.exotia.plugins.market.inventories.InventoryService;
import net.exotia.plugins.market.inventories.main.MainInventory;
import net.exotia.plugins.market.services.MarketService;
import net.exotia.plugins.market.utils.ExotiaUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static net.exotia.plugins.market.utils.ExotiaUtils.of;
import static net.exotia.plugins.market.utils.ExotiaUtils.sendList;

@Route(name = "market")
public class MarketCommand {
    private final MessagesConfiguration messagesConfiguration;
    private final InventoryService inventoryService;
    private final MarketService marketService;
    private final DatabaseService databaseService;

    public MarketCommand(InventoryService inventoryService, MarketService marketService, MessagesConfiguration messagesConfiguration, DatabaseService databaseService) {
        this.inventoryService = inventoryService;
        this.marketService = marketService;
        this.databaseService = databaseService;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Execute
    public void execute(Player player) {
        this.inventoryService.inventory(MainInventory.class).open(player);
    }
    @Execute
    public void execute(Player player, @Arg @Name("cena") Integer price) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType().equals(Material.AIR)) {
            player.sendMessage(of(this.messagesConfiguration.itemIsNull));
            return;
        }
        if (this.marketService.reachedLimit(player)) {
            player.sendMessage(of(this.messagesConfiguration.servicesLimitReached));
            return;
        }
        this.databaseService.save(this.marketService.createItem(itemStack, price, player));
        List<Component> message = this.messagesConfiguration.playerAddedItem.stream()
                .map(line -> line.replace("{player}", player.getName()))
                .map(ExotiaUtils::of).toList();
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> sendList(message, onlinePlayer::sendMessage));
        player.sendMessage(of(this.messagesConfiguration.itemSuccessfullyAdded));
        player.getInventory().removeItem(itemStack);
        player.updateInventory();
    }
}

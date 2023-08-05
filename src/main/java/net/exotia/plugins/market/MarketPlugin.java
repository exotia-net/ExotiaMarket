package net.exotia.plugins.market;

import net.exotia.developer.kit.core.ExotiaPlugin;
import net.exotia.developer.kit.core.PluginFactory;
import net.exotia.developer.kit.utils.items.ExotiaItemBuilder;
import net.exotia.plugins.market.commands.MarketCommand;
import net.exotia.plugins.market.configuration.MessagesConfiguration;
import net.exotia.plugins.market.configuration.PluginConfiguration;
import net.exotia.plugins.market.database.DatabaseService;
import net.exotia.plugins.market.inventories.InventoryService;
import net.exotia.plugins.market.inventories.main.MainInventory;
import net.exotia.plugins.market.inventories.main.MainInventoryConfiguration;
import net.exotia.plugins.market.models.MarketItem;
import net.exotia.plugins.market.models.enums.ItemState;
import net.exotia.plugins.market.services.MarketService;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.UUID;

public class MarketPlugin extends JavaPlugin {
    private MessagesConfiguration messagesConfiguration;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onEnable() {
        var pluginFactory = PluginFactory.create(this)
                .useInjector()
                .configFile(MessagesConfiguration.class, "messages.yml", produce -> this.messagesConfiguration = produce)
                .configFile(PluginConfiguration.class, "configuration.yml", produce -> this.pluginConfiguration = produce);

        MarketService marketService = new MarketService();
        InventoryService inventoryService = new InventoryService(pluginFactory)
                .withInventory(new MainInventory());

        DatabaseService databaseService = new DatabaseService(this.pluginConfiguration.database, marketService);
        databaseService.connect();
        databaseService.load();

        pluginFactory.liteCommands(this.messagesConfiguration.liteCommands, builder -> { builder
                .commandInstance(new MarketCommand(inventoryService));
        });

        ExotiaPlugin exotiaPlugin = pluginFactory.produce();
        exotiaPlugin.bootstrap();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

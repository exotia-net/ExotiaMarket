package net.exotia.plugins.market;

import net.exotia.developer.kit.core.ExotiaPlugin;
import net.exotia.developer.kit.core.PluginFactory;
import net.exotia.plugins.market.commands.MarketCommand;
import net.exotia.plugins.market.configuration.MessagesConfiguration;
import net.exotia.plugins.market.configuration.PluginConfiguration;
import net.exotia.plugins.market.database.DatabaseService;
import net.exotia.plugins.market.inventories.InventoryService;
import net.exotia.plugins.market.inventories.main.MainInventory;
import net.exotia.plugins.market.services.MarketService;
import org.bukkit.plugin.java.JavaPlugin;

public class MarketPlugin extends JavaPlugin {
    private MessagesConfiguration messagesConfiguration;
    private PluginConfiguration pluginConfiguration;

    @Override
    public void onEnable() {
        var pluginFactory = PluginFactory.create(this)
                .useInjector()
                .configFile(MessagesConfiguration.class, "messages.yml", produce -> this.messagesConfiguration = produce)
                .configFile(PluginConfiguration.class, "configuration.yml", produce -> this.pluginConfiguration = produce);

        MarketService marketService = new MarketService(this.pluginConfiguration);
        InventoryService inventoryService = new InventoryService(pluginFactory)
                .withInventory(new MainInventory(marketService));

        DatabaseService databaseService = new DatabaseService(this.pluginConfiguration.database, marketService);
        databaseService.connect();
        databaseService.load();

        pluginFactory.liteCommands(this.messagesConfiguration.liteCommands, builder -> { builder
                .commandInstance(new MarketCommand(inventoryService, marketService, this.messagesConfiguration, databaseService));
        });

        ExotiaPlugin exotiaPlugin = pluginFactory.produce();
        exotiaPlugin.bootstrap();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

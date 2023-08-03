package net.exotia.plugins.market.services;

import net.exotia.developer.kit.utils.serializers.ItemStackSerializer;
import net.exotia.plugins.market.models.MarketItem;
import net.exotia.plugins.market.models.enums.ItemState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MarketService {
    private final List<MarketItem> items = new ArrayList<>();

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
}

package net.exotia.plugins.market.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.exotia.developer.kit.utils.serializers.ItemStackSerializer;
import net.exotia.plugins.market.configuration.sections.DatabaseSection;
import net.exotia.plugins.market.models.MarketItem;
import net.exotia.plugins.market.services.MarketService;
import org.bukkit.inventory.ItemStack;

import java.sql.*;

public class DatabaseService {
    private HikariDataSource hikariDataSource;
    private Connection connection;
    private final DatabaseSection config;
    private final MarketService marketService;

    public DatabaseService(DatabaseSection databaseSection, MarketService marketService) {
        this.config = databaseSection;
        this.marketService = marketService;
    }

    public void load() {
        try {
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `exotia_market_items`;");
            while (resultSet.next()) {
                this.marketService.register(resultSet);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void save(MarketItem marketItem) {
        try {
            String query = "INSERT INTO `exotia_market_items` (`uuid`, `owner`, `state`, `item`, `price`, `created`, `expireIn`) VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE `uuid` = VALUES(uuid), `owner` = VALUES(owner), `state` = VALUES(state), `item` = VALUES(item), `price` = VALUES(price), `created` = VALUES(created), `expireIn` = VALUES(expireIn);";
            PreparedStatement preparedStatement = this.getConnection().prepareStatement(query);
            preparedStatement.setString(1, marketItem.uuid().toString());
            preparedStatement.setString(2, marketItem.owner().toString());
            preparedStatement.setString(3, marketItem.state().name());
            preparedStatement.setString(4, ItemStackSerializer.toBase64(new ItemStack[]{marketItem.item()}));
            preparedStatement.setDouble(5, marketItem.price());
            preparedStatement.setLong(6, marketItem.created());
            preparedStatement.setLong(7, marketItem.expireIn());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void delete(MarketItem marketItem) {
        try {
            PreparedStatement preparedStatement = this.getConnection().prepareStatement("DELETE FROM `exotia_market_items` WHERE `uuid` = ?;");
            preparedStatement.setString(1, marketItem.uuid().toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void connect() {
        this.hikariDataSource = new HikariDataSource(this.getHikariConfig());
        try {
            Statement statement = this.hikariDataSource.getConnection().createStatement();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE IF NOT EXISTS `exotia_market_items`(");
            stringBuilder.append("`uuid` VARCHAR(64) NOT NULL,");
            stringBuilder.append("`owner` VARCHAR(64) NOT NULL,");
            stringBuilder.append("`state` VARCHAR(16) NOT NULL,");
            stringBuilder.append("`item` LONGTEXT NOT NULL,");
            stringBuilder.append("`price` DOUBLE NOT NULL,");
            stringBuilder.append("`created` BIGINT(20) NOT NULL,");
            stringBuilder.append("`expireIn` BIGINT(20) NOT NULL,");
            stringBuilder.append(" PRIMARY KEY (uuid));");
            statement.executeUpdate(stringBuilder.toString());
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private HikariConfig getHikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:mysql://%s:%d/%s", this.config.hostname, this.config.port, this.config.database));
        hikariConfig.setUsername(this.config.username);
        hikariConfig.setPassword(this.config.password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        hikariConfig.addDataSourceProperty("useLocalSessionState", "true");
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("cacheResultSetMetadata", "true");
        hikariConfig.addDataSourceProperty("cacheServerConfiguration", "true");
        hikariConfig.addDataSourceProperty("elideSetAutoCommits", "true");
        hikariConfig.addDataSourceProperty("maintainTimeStats", "false");
        return hikariConfig;
    }
    private Connection getConnection() throws SQLException {
        if (this.connection == null || this.connection.isClosed()) {
            this.connection = this.hikariDataSource.getConnection();
        }
        return this.connection;
    }
}

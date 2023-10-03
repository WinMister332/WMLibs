/*
 * ==========
 * PlayerBridge.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/27 @ 12:01:14 PM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.player;

import net.winmister332.libs.WMLib;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.UUID;

public final class PlayerBridge
{
    /**
     * Gets a player with the associated name if one exists.
     * @param player The players' name to check.
     * @return The player with the associated name.
     */
    public static Player getPlayerByName(String player)
    {
        return WMLib.getInstance().getServer().getPlayer(player);
    }

    /**
     * Gets a player with the associated UUID if one exists.
     * @param uuid The UUID to check.
     * @return The player with the associated ID of one exists.
     */
    public static Player getPlayerByUUID(UUID uuid)
    {
        return WMLib.getInstance().getServer().getPlayer(uuid);
    }

    /**
     * Gets a player with the associated UUID if one exists.
     * @param uuid The UUID to check.
     * @return The player with the associated ID of one exists.
     */
    public static Player getPlayerByUUID(String uuid)
    {
        if (uuid == null || uuid.isEmpty() || uuid.isBlank())
            return null;
        UUID uuidx = UUID.fromString(uuid);
        return uuid != null ? getPlayerByUUID(uuid) : null;
    }

    /**
     * Gets the player with the specified name or UUID.
     * @param nameOrUUID The name or UUID to check.
     * @return The player with the associated name or UUID.
     */
    public static Player getPlayer(String nameOrUUID)
    {
        Player nmPlayer = getPlayerByName(nameOrUUID);
        Player uidPlayer = getPlayerByUUID(nameOrUUID);
        if (uidPlayer != null)
            return uidPlayer;
        else if (nmPlayer != null)
            return nmPlayer;
        else return null;
    }

    /**
     * Gets a collection of all players who are currently online.
     * @return A collection of @see Player who are on the server.
     */
    public static Player[] getOnlinePlayers()
    {
        return (Player[]) WMLib.getInstance().getServer().getOnlinePlayers().toArray();
    }

    /**
     * Gets a collection of players who are offline.
     * @return A collection of @see OfflinePlayer
     */
    public static OfflinePlayer[] getOfflinePlayers()
    {
        return WMLib.getInstance().getServer().getOfflinePlayers();
    }

    /**
     * Checks if a player with the specified name or UUID is online.
     * @param nameOrUUID The name or UUID of the player to get and check.
     * @return True if the player is found with the name or UUID and they're online.
     */
    public static boolean isPlayerOnline(String nameOrUUID) {
        Player player = getPlayer(nameOrUUID);
        if (player == null)
            return false;

        return player.isOnline();
    }

    /**
     * Checks if the player is on the block at the specified location.
     * @param player The player to check.
     * @param pos The position of the block to check.
     * @return True if the player is at the specified block coords.
     */
    public static boolean playerOnBlock(Player player, Location pos)
    {
        Location playerPos = player.getLocation();
        double playerX = playerPos.getBlockX();
        double playerY = playerPos.getBlockY();
        double playerZ = playerPos.getBlockZ();
        double blockX = pos.getBlockX();
        double blockY = pos.getBlockY();
        double blockZ = pos.getBlockZ();
        return (playerX == blockX) && (playerY == blockY) && (playerZ == blockZ);
    }

    /**
     * Checks if the player is in a specific block range.
     * @param player The player to check.
     * @param location The location of the initial block to check.
     * @param range The range from the block to check.
     * @return True if the player is within range of the specified block location.
     */
    public static boolean isPlayerInRangeOf(Player player, Location location, int range)
    {
        LocationRange locationRange = new LocationRange(location, range);
        return isPlayerInRangeOf(player, locationRange);
    }

    /**
     * Checks if the player is in a specific block range.
     * @param player The player to check.
     * @param range The range handler class to compare.
     * @return True if the player is within the specified range.
     */
    public static boolean isPlayerInRangeOf(Player player, LocationRange range)
    {
        return range.isPlayerInRange(player);
    }
}

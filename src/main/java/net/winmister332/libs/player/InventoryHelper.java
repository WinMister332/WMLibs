/*
 * ==========
 * InventoryHelper.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/10/01 @ 23:39:59 PM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.player;

import io.papermc.paper.math.BlockPosition;
import io.papermc.paper.math.Position;
import net.kyori.adventure.text.BlockNBTComponent;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper class for a players' inventory and items within said inventory.
 */
public final class InventoryHelper
{
    /**
     * Gets the inventory of the specified player.
     * @param player The player.
     * @return The inventory of the specified player.
     */
    public static PlayerInventory getPlayerInventory(Player player)
    {
        return player.getInventory();
    }

    /**
     * Gets the inventory of the player with the specified name or UUID.
     * @param playerNameOrID The name or UUID of the player.
     * @return The inventory of the player with the specified name or UUID.
     */
    public static PlayerInventory getPlayerInventory(String playerNameOrID)
    {
        Player player = PlayerBridge.getPlayer(playerNameOrID);
        return getPlayerInventory(player);
    }

    /**
     * Checks if a player has a specific item in their inventory.
     * @param player The player to check.
     * @param item The item to check.
     * @return True if the item is in the specified players' inventory.
     */
    public static boolean playerHasItem(Player player, Item item)
    {
        PlayerInventory inventory = getPlayerInventory(player);
        return inventory.contains(item.getItemStack());
    }
}

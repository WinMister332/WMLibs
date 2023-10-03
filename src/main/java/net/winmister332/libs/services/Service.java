/*
 * ==========
 * Service.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332
 * TimeCreated: 2023/09/29 @ 18:05:05 PM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.services;

import net.winmister332.libs.events.Event;
import net.winmister332.libs.events.XEventArgs;
import net.winmister332.libs.utils.Utilities;

import javax.sql.XAConnection;
import java.util.UUID;

public class Service
{
    private final String serviceName;
    private final UUID serviceID;

    private Event<XEventArgs> serviceRunning = new Event<XEventArgs>();

    public Service(String name)
    {
        serviceID = UUID.randomUUID();
        if (Utilities.isNullWhiteSpaceOrEmpty(name))
            name = "Service_" + serviceID.toString();
        serviceName = name;
    }


}

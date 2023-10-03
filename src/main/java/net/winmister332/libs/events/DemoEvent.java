/*
 * ==========
 * DemoEvent.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332, Faua
 * TimeCreated: 2023/09/27 @ 24:15:17 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.events;

import net.winmister332.libs.logging.LogStatus;
import net.winmister332.libs.logging.Logger;

import java.io.IOException;

//This is an example which utilizes the net.winmister332.libs.events event system.
public final class DemoEvent
{
    //Create a demo event args. (If only 1 element needs to be passed, a XEventArgs can be used.
    public final class DemoEventArgs extends EventArgs
    {
        private String demoString = "Hello World, this is a demo string!";
        public String getDemoString() { return demoString; }
    }

    private Logger logger;
    private Event<DemoEventArgs> event = new Event();

    /***
     * An example using the WinMister332 event system.
     * @throws IOException A temporary patch for logger, later revision will NOT require throw signature.
     */
    public DemoEvent() throws IOException {
        logger = new Logger("Logger");

        final boolean[] doOnce = {true};

        event.add((sender, e) ->
        {
            if (doOnce[0]) {
                logger.log(LogStatus.INFO, "The event was triggered!");
                doOnce[0] = false;
            }
        });

        event.invoke(this, EventArgs.getEmpty());
    }
}

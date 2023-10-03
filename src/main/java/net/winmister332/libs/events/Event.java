/*
 * ==========
 * Event.java
 * ==========
 * Project: WMLib
 * Developer(s): WinMister332, Faua
 * TimeCreated: 2023/09/27 @ 24:14:10 AM
 * License: MIT
 * ------------------
 * Copyright (c) $originalCopyright.match("Copyright \(c\) (\d+)", 1, "-")2023
 * ===========
 *
 */

package net.winmister332.libs.events;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Event<T extends EventArgs> implements Iterable<EventAction<T>>
{
    private Set<EventAction<T>> queue = new HashSet<>();

    public Event() {}

    public Event(EventAction<T> action)
    {
        add(action);
    }

    public final void add(EventAction<T> action)
    {
        queue.add(action);
    }

    public final void invoke(Object sender, T t)
    {
        //Invoke every iteration of the event.
        for (EventAction<T> action : this)
            action.invoke(sender, t);
    }

    @Override
    public Iterator<EventAction<T>> iterator() {
        return queue.iterator();
    }
}

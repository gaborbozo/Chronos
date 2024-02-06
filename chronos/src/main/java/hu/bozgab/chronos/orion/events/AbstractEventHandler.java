package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.interfaces.IEventExecutor;
import hu.bozgab.chronos.orion.events.interfaces.IEventHandler;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractEventHandler implements IEventHandler {

    protected Map<String, IEventExecutor> events = new HashMap<>();

    @Override
    public final Iterable<String> commands() {
        return events.keySet();
    }

    @Override
    public final IEventExecutor getEvent(String command) {
        return events.get(command);
    }
}

package hu.bozgab.chronos.orion.events.interfaces;

public interface IEventHandler {
    Iterable<String> commands();

    IEventExecutor getEvent(String command);
}

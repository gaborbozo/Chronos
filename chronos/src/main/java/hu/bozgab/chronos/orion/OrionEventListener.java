package hu.bozgab.chronos.orion;

import hu.bozgab.chronos.orion.events.exceptions.MultipleEventsAssignedToCommandException;
import hu.bozgab.chronos.orion.events.interfaces.IEventExecutor;
import hu.bozgab.chronos.orion.events.interfaces.IEventHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrionEventListener extends ListenerAdapter {
    private final String prefix = "!";

    private final String separator = " ";

    private List<IEventHandler> eventHandlers;
    private Map<String, IEventExecutor> events;

    @Autowired
    public OrionEventListener(List<IEventHandler> eventHandlers) {
        this.eventHandlers = eventHandlers;
        buildEventsMap();
        System.out.println(events.keySet());
    }

    private void buildEventsMap(){
        events = new HashMap<>();

        eventHandlers.stream().forEach(eventHandler ->
                eventHandler.commands().forEach(command -> {
                    try {
                        if (events.containsKey(prefix + command))
                            throw new MultipleEventsAssignedToCommandException();

                        events.put(prefix + command, eventHandler.getEvent(command));
                    } catch (MultipleEventsAssignedToCommandException e) {}
                } ));
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Attack ships on fire off the shoulder of Orion.");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(separator);

        if (events.containsKey(args[0]))
            events.get(args[0]).execute(event, args);
    }
}

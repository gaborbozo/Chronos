package hu.bozgab.chronos.orion;

import hu.bozgab.chronos.orion.events.exceptions.MultipleEventsAssignedToCommandException;
import hu.bozgab.chronos.orion.events.interfaces.IEventExecutor;
import hu.bozgab.chronos.orion.events.interfaces.IEventHandler;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class OrionEventListener extends ListenerAdapter {

    private final String prefix;
    private final String separator;

    private List<IEventHandler> eventHandlers;
    private Map<String, IEventExecutor> events;

    @Autowired
    // Instead of injecting String by constructor
    // If it will be used, consider it
    // Not spring specific solution
    //@PostConstruct
    public OrionEventListener(List<IEventHandler> eventHandlers, @Value("${orion.prefix}") String prefix, @Value("${orion.separator}") String separator) {
        this.eventHandlers = eventHandlers;
        this.prefix = prefix;
        this.separator = separator;

        buildEventsMap();
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
        log.info("Attack ships on fire off the shoulder of Orion.");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(separator);

        if (events.containsKey(args[0]))
            events.get(args[0]).execute(event, args);
    }
}

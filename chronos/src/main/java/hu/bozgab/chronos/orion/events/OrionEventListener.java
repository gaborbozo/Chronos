package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.interfaces.IEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrionEventListener extends ListenerAdapter {
    private final String prefix = "!";

    private final String separator = " ";

    private List<IEvent> events;

    @Autowired
    public OrionEventListener(List<IEvent> events) {
        this.events = events;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event)
    {
        System.out.println("Attack ships on fire off the shoulder of Orion.");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(separator);

        events.stream()
                .filter(item -> args[0].equals(prefix + item.command()))
                .forEach(item -> {
                    if (args.length > 1) {
                        item.execute(event, Arrays.copyOfRange(args, 1, args.length));
                    } else {
                        item.execute(event);
                    }
                });
    }
}

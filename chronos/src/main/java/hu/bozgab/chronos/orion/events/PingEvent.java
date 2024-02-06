package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.interfaces.IEventHandler;
import hu.bozgab.chronos.orion.events.interfaces.IEventExecutor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PingEvent extends AbstractEventHandler {

    public PingEvent(){
        events.put("ping", ping);
    }

    static IEventExecutor ping = (event, params) -> {
        long time = System.currentTimeMillis();

        event.getChannel().sendMessage("Pong!")
                .queue(response -> {
                    response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                });
    };
}

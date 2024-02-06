package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.interfaces.IEvent;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class PingEvent implements IEvent {
    @Override
    public String command() {
        return "ping";
    }

    @Override
    public void execute(MessageReceivedEvent event, String... params) {
        long time = System.currentTimeMillis();

        event.getChannel().sendMessage("Pong!")
                .queue(response -> {
                    response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                });
    }


}

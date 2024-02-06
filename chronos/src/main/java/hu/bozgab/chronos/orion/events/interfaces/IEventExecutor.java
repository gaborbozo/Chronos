package hu.bozgab.chronos.orion.events.interfaces;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface IEventExecutor {
    void execute(MessageReceivedEvent event, String... params);
}

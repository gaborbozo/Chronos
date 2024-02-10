package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.interfaces.IEventExecutor;
import net.dv8tion.jda.api.entities.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClearEvent extends AbstractEventHandler {

    public ClearEvent(){
        events.put("clear", clear);
    }

    static IEventExecutor clear = (event, params) -> {
        try {
            if (params.length >= 2) {
                int n = Integer.parseInt(params[1]);
                List<Message> messageList = event.getChannel().getHistory().retrievePast(n + 1).complete();
                event.getChannel().asGuildMessageChannel().deleteMessages(messageList).queue();
            }
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("**Error!**\n").addContent("\nGiven argument is not a number").queue();
        } catch (IllegalArgumentException e) {
            event.getChannel().sendMessage("**Error!**\n").addContent("\nSome messages are trying to be deleted were sent more than two weeks ago").queue();
        } catch (Exception e) {
            event.getChannel().sendMessage("**Error!**\n").addContent("\n" + e.getMessage()).queue();
        }
    };
}

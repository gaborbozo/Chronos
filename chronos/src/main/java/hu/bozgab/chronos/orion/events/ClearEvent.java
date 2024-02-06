package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.interfaces.IEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClearEvent implements IEvent {

    @Override
    public String command() {
        return "clear";
    }

    @Override
    public void execute(MessageReceivedEvent event, String... params) {
        try {
            if (params.length >= 1) {
                int n = Integer.parseInt(params[0]);
                List<Message> messageList = event.getChannel().getHistory().retrievePast(n + 1).complete();
                event.getChannel().asGuildMessageChannel().deleteMessages(messageList).queue();
            }
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("**Error!**\n").addContent("\nGiven argument is not a number").queue();
        } catch (IllegalArgumentException e){
            event.getChannel().sendMessage("**Error!**\n").addContent("\nSome messages are trying to be deleted were sent more than two weeks ago").queue();
        } catch (Exception e){
            event.getChannel().sendMessage("**Error!**\n").addContent("\n" + e.getMessage()).queue();
        }
    }
}

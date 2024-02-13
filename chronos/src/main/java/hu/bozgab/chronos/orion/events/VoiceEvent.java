package hu.bozgab.chronos.orion.events;

import hu.bozgab.chronos.orion.events.components.voice.VoiceManager;
import hu.bozgab.chronos.orion.events.components.voice.exceptions.AudioManagerAlreadyConnectedException;
import hu.bozgab.chronos.orion.events.components.voice.exceptions.AudioManagerNotDefinedException;
import hu.bozgab.chronos.orion.events.interfaces.IEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VoiceEvent extends AbstractEventHandler {

    private static VoiceManager voiceManager;

    @Autowired
    public VoiceEvent(VoiceManager voiceManager){
        this.voiceManager = voiceManager;

        events.put("join", join);
        events.put("leave", leave);
    }

    static IEventExecutor join = (event, params) -> {
        try {
            voiceManager.join(event.getMember().getVoiceState().getChannel().asVoiceChannel());
        } catch (NullPointerException e) {
            event.getChannel().sendMessage("You need to join a voice channel first!").queue();
        } catch (AudioManagerNotDefinedException e) {
            voiceManager.setAudioManager(event.getGuild().getAudioManager());
            try {
                voiceManager.join(event.getMember().getVoiceState().getChannel().asVoiceChannel());
            } catch (AudioManagerAlreadyConnectedException ex) {
                event.getChannel().sendMessage("Already connected to a channel!").queue();
            } catch (AudioManagerNotDefinedException ex) {
                event.getChannel().sendMessage("Inner error").queue();
                e.printStackTrace();
            }
        }
        catch (AudioManagerAlreadyConnectedException e) {
            event.getChannel().sendMessage("Already connected to a channel!").queue();
        }
    };

    static IEventExecutor leave = (event, params) -> {
        try {
            voiceManager.leave();
        } catch (AudioManagerNotDefinedException e){}
    };
}

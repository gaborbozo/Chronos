package hu.bozgab.chronos.orion;

import hu.bozgab.chronos.orion.events.OrionEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OrionInitializer implements CommandLineRunner {
    private final OrionEventListener orionEventListener;

    @Autowired
    public OrionInitializer(OrionEventListener orionEventListener){
        this.orionEventListener = orionEventListener;
    }

    @Override
    public void run(String... args) throws InterruptedException {
        JDA jda = JDABuilder
                .createLight("token",
                        GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS)
                .addEventListeners(orionEventListener)
                .build();

        jda.awaitReady();
    }
}

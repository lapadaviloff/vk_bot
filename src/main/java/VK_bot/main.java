package VK_bot;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.petersamokhin.bots.sdk.callbacks.commands.OnCommandCallback;
import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

public class main {
    private static final String VK_KEY = "d94c7e5e9842ab5cd3c25b5939e7b7ef2a61e438a7122b7888ec44cfe793462050ac785acc28865069d9b";

    public static void main(String[] args) throws SteamApiException {
        Group group = new Group(195024272, VK_KEY);
        Processor processor = new Processor();
        group.onCommand("/", new OnCommandCallback() {
            @Override
            public void OnCommand(Message message) {
                // System.out.println(message.getText());
                new Message()
                        .from(group)
                        .to(message.authorId())
                        .text(processor.process(group, message))
                        .send();
            }
        });
        group.onSimpleTextMessage(message -> {
                    new Message()
                            .from(group)
                            .to(message.authorId())
                            .text(processor.process())
                            .send();
                }
        );
    }


}
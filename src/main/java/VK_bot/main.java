package VK_bot;

import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.petersamokhin.bots.sdk.callbacks.commands.OnCommandCallback;
import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

public class main {
    private static final String VK_KEY = "здесь ваш vk_key";

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

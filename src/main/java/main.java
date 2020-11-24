import com.lukaspradel.steamapi.core.exception.SteamApiException;
import com.petersamokhin.bots.sdk.callbacks.commands.OnCommandCallback;
import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
public class main {
    private static final String VK_KEY = "";
    public  static void main(String[] args) throws SteamApiException {
        Group group = new Group(195024272, VK_KEY);
        group.onCommand("/", new OnCommandCallback() {
            @Override
            public void OnCommand(Message message) {

                new Message()
                        .from(group)
                        .to(message.authorId())
                        .text(processor (message.getText()))
                        .send();
            }
        });
        group.onSimpleTextMessage(message -> {

                    new Message()
                            .from(group)
                            .to(message.authorId())
                            .text(sMes())
                            .send();
                }
        );
    }

    static  String sMes(){
        String s = new String();
        s="здравствуйте, я бот.\n/help";
        return s;
    }
    static  String processor(String string)  {
        switch (string){
            case "/help": return "чем смогу-помогу)))\n" +
                                  "/info  - обо мне\n" +
                                   "/add  - пока пусто\n" +
                                   "/rate - цена доллара в рублях";
            case "/add" : return  string+"  сейчас добавлю что- нибудь)))";
            case "/info" : return  "bot v.1. Mitroshin alexey, lapadaviloff@yandex.ru";
            case "/rate" :{
                        Document doc = null;
                        try {
                            doc = Jsoup.connect("https://yandex.ru/").userAgent("Chrome/4.0.249.0 Safari/532.5")
                                    .get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String title = doc.html();
                        Element masthead = doc.select("span.inline-stocks__value_inner").get(0);
                        return "USD сегодня....."+masthead.text();
                    }
            default:return "нипанятно... перефразируй";

        }
    }
}
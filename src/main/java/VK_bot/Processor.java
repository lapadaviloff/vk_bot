package VK_bot;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

public class Processor {
    public String process() {
        String s = new String();
        s = "здравствуйте, я бот.\n/help";
        return s;
    }

    public String process(Group group, Message message) {
        Functions functions = new Functions();
        String string = message.getText().replaceAll(" .*", "");
        System.out.println(string);
        switch (string) {
            case "/help":
                return functions.listFunction;

            case "/info":
                return functions.info();

            case "/rate":
                return functions.rate();

            case "/qr": {
                return functions.qr(group, message);
            }
            default:
                return "нипанятно... перефразируй";

        }
    }
}

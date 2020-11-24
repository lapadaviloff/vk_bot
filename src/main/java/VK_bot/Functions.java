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

public class Functions {
    String listFunction = "чем смогу-помогу)))\n" +
            "/info  - обо мне\n" +
            "/rate - цена доллара в рублях\n" +
            "/qr строка - создание QR кода\n\n" +
            "картинка и подпись \"/qr\" - распознование штрих и QR кода";

    String info() {

        return "bot v.1. Mitroshin alexey, lapadaviloff@yandex.ru";

    }

    String rate() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://yandex.ru/").userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .get();

        } catch (IOException e) {

            e.printStackTrace();

        }
        // String title = doc.html();
        Element masthead = doc.select("span.inline-stocks__value_inner").get(0);
        return "USD сегодня....." + masthead.text();
    }

    String qr(Group group, Message message) {
        if (!"[]".equals(message.getPhotos().toString())) {
            String returnText = "";
            File QRCodeImage = new File("template_" + message.authorId() + ".png");
            try {
                QRCodeImage.createNewFile();
                if (SystemUtils.IS_OS_UNIX)
                    Files.setPosixFilePermissions(Paths.get(QRCodeImage.getAbsolutePath()), PosixFilePermissions.fromString("rwxrwxrwx"));
            } catch (IOException ignored) {
                System.out.println("Some error occured: " + ignored.toString() + " with file " + QRCodeImage.getAbsolutePath());
            }

            // Downloading image to file
            try {
                FileUtils.copyURLToFile(new URL(
                        message.getBiggestPhotoUrl(message.getPhotos())
                ), QRCodeImage);
            } catch (IOException ignored) {
                return "сервер распознования не отвечает";
            }

            QRCode qr = new QRCode();
            returnText = qr.decode(QRCodeImage);
            // Delete template file
            try {
                Files.delete(Paths.get(QRCodeImage.getAbsolutePath()));
            } catch (IOException ignored) {

            }
            // Send message
            return returnText;
        } else {
            File QRCodeImage = new File("template_" + message.authorId() + ".png");
            try {
                QRCodeImage.createNewFile();
                if (SystemUtils.IS_OS_UNIX)
                    Files.setPosixFilePermissions(Paths.get(QRCodeImage.getAbsolutePath()), PosixFilePermissions.fromString("rwxrwxrwx"));
            } catch (IOException ignored) {
                System.out.println("Some error occured: " + ignored.toString() + " with file " + QRCodeImage.getAbsolutePath());
                new Message()
                        .from(group)
                        .to(message.authorId())
                        .text("невозможно создать файл на сервере")
                        .send();
            }
            System.out.println("setencode");

            // Encode the text to the image

            QRCode qr = new QRCode();

            qr.encode(message.getText().replaceAll("/qr ", ""), QRCodeImage);

            // Send message
            new Message()
                    .from(group)
                    .to(message.authorId())
                    .photo(QRCodeImage.getAbsolutePath())
                    .text("лови")
                    .send();

            // Delete template file
            try {
                Files.delete(Paths.get(QRCodeImage.getAbsolutePath()));
            } catch (IOException ignored) {
            }
            return "";
        }

    }

}

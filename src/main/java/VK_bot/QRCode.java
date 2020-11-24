package VK_bot;

import VK_bot.BufferedImageLuminanceSource;
import com.google.zxing.*;
//import com.google.zxing.client.j2se.VK_bot.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

/**
 * Class for encoding VK_bot.QRCode images and decoding any text to them.
 */
public class QRCode {

    /**
     * Encode text to QR Code image
     * @param what Text
     * @param file Result image file
     */
    public void encode(String what, File file) {

        int size = 250;
        String fileType = "png";
        System.out.println("encode");
        try {

            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 4); /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(what, BarcodeFormat.QR_CODE, size,
                    size, hintMap);
            int CrunchifyWidth = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                    BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
            graphics.setColor(Color.BLACK);

            for (int i = 0; i < CrunchifyWidth; i++) {
                for (int j = 0; j < CrunchifyWidth; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, file);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decode QR Code from image
     * @param file Image file with QR Code
     * @return Decoded text
     */
    public String decode(File file) {

        String stringFromQRCode = "";
        String  qrResult = "";

        try {
            BufferedImage bufferedImage = ImageIO.read(file);

           // BufferedImage bufferedImage = image.getSubimage(0, 0, 250,250);
            BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bufferedImage);

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(bufferedImageLuminanceSource));
            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap);

            stringFromQRCode = qrCodeResult.getText();
            qrResult = qrCodeResult.getBarcodeFormat().toString();

        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return "не распознано, попробуйте снова";
        }

         if(qrResult =="QR_CODE")return stringFromQRCode;
        return "https://barcode-list.ru/barcode/RU/Поиск.htm?barcode="+stringFromQRCode;
    }
}

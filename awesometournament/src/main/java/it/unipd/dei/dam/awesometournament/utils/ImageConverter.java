package it.unipd.dei.dam.awesometournament.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageConverter {

    /**
     * Converts InputStream image to the base64 representation of the image.
     *
     * @param image The InputStream representation of the image.
     * @return The base64 representation of the image.
     */
    public static String convertInputStreamToBase64(InputStream image) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = image.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            byte[] imgBytes = outputStream.toByteArray();

            return Base64.getEncoder().encodeToString(imgBytes);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                image.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

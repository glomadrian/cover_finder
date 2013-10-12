package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Adrian Garcia Lomas
 */
public class URLImageHandler implements IImageHandler{


    private URL imageURL;

    public URLImageHandler(URL imageURL){
        this.imageURL = imageURL;
    }


    @Override
    public InputStream getImage() throws ImageNotFoundException {

        try {
            BufferedImage bufferedImage = ImageIO.read(imageURL);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpeg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;

        } catch (IOException e) {
            throw  new ImageNotFoundException();
        }

    }
}

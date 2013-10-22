package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @author Adrian Garcia Lomas
 */
public class URLImageHandler implements IImageHandler<URL>{

    private final static Logger LOG = Logger.getLogger(URLImageHandler.class.getName());


    @Override
    public InputStream getImage(URL imageURL) throws ImageNotFoundException {



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

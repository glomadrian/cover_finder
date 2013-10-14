package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

/**
 * Custom image handler to get images from local folder passed as parameter.
 * @author Adrian Garcia Lomas
 */
public class CustomFolderImageHandler implements IImageHandler<String> {

    private final static Logger LOG = Logger.getLogger(CustomFolderImageHandler.class.getName());

    @Override
    public InputStream getImage(String completePatch) throws ImageNotFoundException {

        LOG.info("getImage inicialiced...");

        BufferedImage image;


        LOG.info("Try to get image from "+completePatch);

        try {
            File file = new File(completePatch);

            LOG.info("File absolute patch "+file.getAbsolutePath());

            image = ImageIO.read(file);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpeg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            return is;

        } catch (IOException e) {
            throw  new ImageNotFoundException();
        }

    }
}

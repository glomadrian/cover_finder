package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;
import es.gldos.coverFinder.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Logger;

/**
 * Custom image handler to get images from local folder passed as parameter.
 * @author Adrian Garcia Lomas
 */
public class CustomFolderImageHandler implements IImageHandler {

    private final static Logger LOG = Logger.getLogger(CustomFolderImageHandler.class.getName());
    private String folderURI;
    private String imageName;

    public CustomFolderImageHandler(String folderURI,String imageName) {

        this.folderURI = folderURI;
        this.imageName = imageName;
    }

    @Override
    public InputStream getImage() throws ImageNotFoundException {

        LOG.info("getImage inicialiced...");

        BufferedImage image;

        String completePatch = Utils.addSlashToURI(folderURI)+imageName;

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

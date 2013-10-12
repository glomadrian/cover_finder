package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;

import java.io.InputStream;

/**
 * Interface for all image handler
 * @author  Adrian Garcia Lomas
 */
public interface IImageHandler {

    /**
     * Every imageHandler must implement the method getImage
     * @return InputStream
     */
    public InputStream getImage() throws ImageNotFoundException;
}

package es.gldos.coverFinder.provider;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ImageNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import es.gldos.coverFinder.handler.CustomFolderImageHandler;
import es.gldos.coverFinder.handler.URLImageHandler;
import es.gldos.coverFinder.service.invoquer.InvokerService;

import java.io.InputStream;

/**
 * The default cover provider alwais return a image cover, if the serices not return any image cover the provider return a default image
 * @author Adrian Garcia Lomas
 */
public class InputStreamCoverProvider implements ICoverProvider{

    public static final int SMALL_SIZE = 0;
    public static final int MEDIUM_SIZE = 1;
    public static final int LARGE_SIZE = 2;

    private InvokerService invokerService;
    private URLImageHandler urlImageHandler;
    private CustomFolderImageHandler customFolderImageHandler;
    private String defaultImageNotFoundURI;
    private int coverSize;

    public InputStreamCoverProvider(InvokerService invokerService, URLImageHandler urlImageHandler,CustomFolderImageHandler customFolderImageHandler, String defaultImageNotFoundURI, int coverSize) {
        this.invokerService = invokerService;
        this.urlImageHandler = urlImageHandler;
        this.defaultImageNotFoundURI = defaultImageNotFoundURI;
        this.customFolderImageHandler = customFolderImageHandler;
        this.coverSize = coverSize;
    }

    @Override
    public InputStream getCover(String title, String artist) throws ImageNotFoundException {

        CoverResult cover = null;
        InputStream inputStream = null;
        try {
              cover = invokerService.execute(title,artist);

            switch (coverSize){

                case SMALL_SIZE:
                    inputStream =  urlImageHandler.getImage(cover.getSmallCover());
                case MEDIUM_SIZE:
                    inputStream =  urlImageHandler.getImage(cover.getMediumCover());
                case LARGE_SIZE:
                    inputStream = urlImageHandler.getImage(cover.getLargerCover());
            }
        } catch (ServiceErrorException e) {
          return  generateNoCoverResult();
        } catch (CoverNotFoundException e) {
          return generateNoCoverResult();
        } catch (ImageNotFoundException e) {
          return generateNoCoverResult();
        }

        return inputStream;
    }


    private InputStream generateNoCoverResult() throws ImageNotFoundException {
        return customFolderImageHandler.getImage(defaultImageNotFoundURI);
    }
}

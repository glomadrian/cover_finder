package es.gldos.coverFinder.provider;

import es.gldos.coverFinder.exception.ImageNotFoundException;
import es.gldos.coverFinder.handler.CustomFolderImageHandler;
import es.gldos.coverFinder.handler.URLImageHandler;
import es.gldos.coverFinder.service.ICoverService;
import es.gldos.coverFinder.service.ITunesCoverService;
import es.gldos.coverFinder.service.LastFmCoverService;
import es.gldos.coverFinder.service.invoquer.InvokerService;
import junit.framework.TestCase;

import java.io.InputStream;

/**
 * @author Adrian Garcia Lomas
 */
public class InputStreamCoverProviderTest extends TestCase{


    public void testIfInputStreamCoverProviderReturnOk(){

        //Define objects

        //Define all services

        ICoverService lastFM =  new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        ICoverService iTunes = new ITunesCoverService();


        //Create invoker

        InvokerService invoquer = new InvokerService();

        invoquer.addCoverService(lastFM);
        invoquer.addCoverService(iTunes);

        //Create handler
        URLImageHandler urlHandler = new URLImageHandler();
        CustomFolderImageHandler customFolderHandler = new CustomFolderImageHandler();


        InputStreamCoverProvider coverProvider = new InputStreamCoverProvider(invoquer,urlHandler,customFolderHandler,"testFolder/test.png",InputStreamCoverProvider.MEDIUM_SIZE);


        try {
            InputStream imageStream = coverProvider.getCover("Master of puppets","Metallica");
            assertNotNull(imageStream);

        } catch (ImageNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }    public void testIfInputStreamCoverProviderDefaultImage(){

        //Define objects

        //Define all services

        ICoverService lastFM =  new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        ICoverService iTunes = new ITunesCoverService();


        //Create invoker

        InvokerService invoquer = new InvokerService();

        invoquer.addCoverService(lastFM);
        invoquer.addCoverService(iTunes);

        //Create handler
        URLImageHandler urlHandler = new URLImageHandler();
        CustomFolderImageHandler customFolderHandler = new CustomFolderImageHandler();


        InputStreamCoverProvider coverProvider = new InputStreamCoverProvider(invoquer,urlHandler,customFolderHandler,"testFolder/test.png",InputStreamCoverProvider.MEDIUM_SIZE);


        try {
            InputStream imageStream = coverProvider.getCover("Fake name","Fake Artist");
            assertNotNull(imageStream);

        } catch (ImageNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    public void testUncauchException(){

        //Define objects

        //Define all services

        ICoverService lastFM =  new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        ICoverService iTunes = new ITunesCoverService();


        //Create invoker

        InvokerService invoquer = new InvokerService();

        invoquer.addCoverService(lastFM);
        invoquer.addCoverService(iTunes);

        //Create handler
        URLImageHandler urlHandler = new URLImageHandler();
        CustomFolderImageHandler customFolderHandler = new CustomFolderImageHandler();


        InputStreamCoverProvider coverProvider = new InputStreamCoverProvider(invoquer,urlHandler,customFolderHandler,"testFolder/test.png",InputStreamCoverProvider.MEDIUM_SIZE);


        try {
            InputStream imageStream = coverProvider.getCover("Shattered","Setiva");
            assertNotNull(imageStream);

        } catch (ImageNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}

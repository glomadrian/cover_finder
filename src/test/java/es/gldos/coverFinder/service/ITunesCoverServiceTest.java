package es.gldos.coverFinder.service;

import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import junit.framework.TestCase;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author Adrian Garcia Lomas
 */
public class ITunesCoverServiceTest extends TestCase {




    public void testIfITunesCoverServiceWorks(){

        ICoverService service =new ITunesCoverService();
        try {

            ArrayList<URL> urlList = service.searchCover("Master of puppets", "metallica");

            assertNotNull(urlList);
            assertEquals(urlList.size(),1);

        } catch (ServiceErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CoverNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    public void testIfServiceThrowCoverNotFound(){

        ICoverService service = new ITunesCoverService();

        try {

            ArrayList<URL> urlList = service.searchCover("fake_not_exits", "fake_not_exits");

        } catch (ServiceErrorException e) {
            assertNull(e);
        } catch (CoverNotFoundException e) {
            assertNotNull(e);
        }
    }
}

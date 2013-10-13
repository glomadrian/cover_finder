package es.gldos.coverFinder.service;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import junit.framework.TestCase;

/**
 * @author Adrian Garcia Lomas
 */
public class ITunesCoverServiceTest extends TestCase {




    public void testIfITunesCoverServiceWorks(){

        ICoverService service =new ITunesCoverService();
        try {

            CoverResult coverResult = service.searchCover("Master of puppets", "metallica");

            assertNotNull(coverResult);
//            assertEquals(urlList.size(),1);

        } catch (ServiceErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CoverNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


    public void testIfServiceThrowCoverNotFound(){

        ICoverService service = new ITunesCoverService();

        try {

            CoverResult coverResult = service.searchCover("fake_not_exits", "fake_not_exits");

        } catch (ServiceErrorException e) {
            assertNull(e);
        } catch (CoverNotFoundException e) {
            assertNotNull(e);
        }
    }
}

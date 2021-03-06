package es.gldos.coverFinder.service;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import junit.framework.TestCase;

/**
 * @author Adrian Garcia Lomas
 */
public class LastFmCoverServiceTest extends TestCase{


    public void testIfLastFmServiceSearchOk(){

        ICoverService service = new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        try {

            CoverResult coverResult = service.searchCover("Master of puppets", "Metallica");

            assertNotNull(coverResult);
            assertNotNull(coverResult.getSmallCover());
            assertNotNull(coverResult.getMediumCover());
            assertNotNull(coverResult.getLargerCover());

        } catch (ServiceErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CoverNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void testIfServiceThowError(){

        ICoverService service = new LastFmCoverService("FAKE_KEY","FAKE_SECRET");
        try {

            CoverResult coverResult = service.searchCover("Master of puppets", "Metallica");
            assertNull(coverResult);

        } catch (ServiceErrorException e) {
            assertNotNull(e);
        } catch (CoverNotFoundException e) {
            assertNull(e);
        }

    }


    public void testIfServiceThrowCoverNotFound(){

        ICoverService service = new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        try {

            CoverResult coverResult = service.searchCover("fake_not_exits", "fake_not_exits");


        } catch (ServiceErrorException e) {
           assertNull(e);
        } catch (CoverNotFoundException e) {
           assertNotNull(e);
        }
    }
}

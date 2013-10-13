package es.gldos.coverFinder.service.invoker;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import es.gldos.coverFinder.service.ICoverService;
import es.gldos.coverFinder.service.ITunesCoverService;
import es.gldos.coverFinder.service.LastFmCoverService;
import es.gldos.coverFinder.service.invoquer.InvokerService;
import junit.framework.TestCase;

/**
 * @author Adrian Garcia Lomas
 */
public class InvokerServiceTest extends TestCase {


    public void testInvoquerService(){

        //Define all services

        ICoverService lastFM =  new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        ICoverService iTunes = new ITunesCoverService();


        //Create invoker

        InvokerService invoquer = new InvokerService();
        invoquer.addCoverService(lastFM);
        invoquer.addCoverService(iTunes);

        try {
            CoverResult cover = invoquer.execute("Master of puppets","Metallica");
            assertNotNull(cover);

        } catch (ServiceErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CoverNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

    public void testInvoquerSericeSecondService(){

        //Define all services

        ICoverService lastFM =  new LastFmCoverService("e9e976f1bd9fdbd495b141e8fcafed4f","3b149e83de6c948805a9e7c8907d51c0");
        ICoverService iTunes = new ITunesCoverService();


        //Create invoker

        InvokerService invoquer = new InvokerService();
        invoquer.addCoverService(lastFM);
        invoquer.addCoverService(iTunes);

        //This song is not in lastFM but its in iTunes, the resuult is cover not null

        try {
            CoverResult cover = invoquer.execute("Pre Bank Hopes, Post Bank Blues","The Great American  Beast");
            assertNotNull(cover);
        } catch (ServiceErrorException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (CoverNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }
}

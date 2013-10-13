package es.gldos.coverFinder.service.invoquer;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import es.gldos.coverFinder.service.ICoverService;

import java.util.ArrayList;

/**
 * InvokerService is a invoquer from command pattern, every Cover service is a command this invoquer have a arraylist for services in use an execute it.
 * @author Adrian Garcia Lomas
 */
public class InvokerService {

    ArrayList<ICoverService> servicesQueque;
    private int position = 0;

    public InvokerService() {

        servicesQueque = new ArrayList<ICoverService>();

    }

    public void addCoverService(ICoverService coverService){

        servicesQueque.add(coverService);
    }


    /**
     * This method will execute one by one all serivces in queque, when one return a valid result the invoquer stop and back to de position 0
     * @return
     */
    public CoverResult execute(String title, String artist) throws ServiceErrorException, CoverNotFoundException {

        CoverResult coverResult = null;

        while (coverResult==null){

            try {
                coverResult = executeService(title,artist);

            } catch (ServiceErrorException e) {
                if(!hasNext())
                {
                    position = 0;
                    throw  e;
                }
            } catch (CoverNotFoundException e) {
                if(!hasNext())
                {
                    position = 0;
                    throw  e;
                }

            }

            nextService();
        }

        position = 0;
        return coverResult;
    }


    private boolean hasNext(){
        return servicesQueque.size() > position;
    }

    private CoverResult executeService(String title,String artist) throws ServiceErrorException, CoverNotFoundException {

        return  servicesQueque.get(position).searchCover(title,artist);
    }

    private boolean nextService(){

        if (hasNext()){
            position++;
            return true;
        }
        else return false;

    }
}

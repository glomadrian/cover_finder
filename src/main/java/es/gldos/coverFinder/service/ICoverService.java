package es.gldos.coverFinder.service;

import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;

import java.net.URL;
import java.util.ArrayList;

/**
 * Interface for all cover services, the cover services must implements this interface for working wuth the invoquer
 * @author Adrian Garcia Lomas
 * @see es.gldos.coverFinder.service.invoquer.InvoquerService
 */
public interface ICoverService {


    ArrayList<URL> searchCover(String title,String artist) throws ServiceErrorException, CoverNotFoundException;
}

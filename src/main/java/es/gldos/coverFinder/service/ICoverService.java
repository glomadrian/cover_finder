package es.gldos.coverFinder.service;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;

/**
 * Interface for all cover services, the cover services must implements this interface for working wuth the invoquer
 * @author Adrian Garcia Lomas
 * @see es.gldos.coverFinder.service.invoquer.InvokerService
 */
public interface ICoverService {


    CoverResult searchCover(String title,String artist) throws ServiceErrorException, CoverNotFoundException;
}

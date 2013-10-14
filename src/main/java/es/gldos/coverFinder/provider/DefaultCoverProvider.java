package es.gldos.coverFinder.provider;

import es.gldos.coverFinder.cover.CoverResult;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ImageNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import es.gldos.coverFinder.service.invoquer.InvokerService;

/**
 * @author Adrian Garcia Lomas
 */
public class DefaultCoverProvider implements ICoverProvider<CoverResult> {

    private InvokerService invokerService;

    public DefaultCoverProvider(InvokerService invokerService) {
        this.invokerService = invokerService;
    }

    @Override
    public CoverResult getCover(String title, String artist) throws ImageNotFoundException {

        CoverResult cover = null;

        try {
            cover = invokerService.execute(title,artist);
            return cover;

        } catch (ServiceErrorException e) {
           throw  new ImageNotFoundException();
        } catch (CoverNotFoundException e) {
          throw  new ImageNotFoundException();
        }

    }
}

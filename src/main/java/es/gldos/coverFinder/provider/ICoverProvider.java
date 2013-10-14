package es.gldos.coverFinder.provider;

import es.gldos.coverFinder.exception.ImageNotFoundException;

/**
 * Interface for all cover providers
 *
 * @author Adrian Garcia Lomas
 */
public interface ICoverProvider <T> {

        T getCover(String title, String artist) throws ImageNotFoundException;
}

package es.gldos.coverFinder.cover;

import java.net.URL;

/**
 * TO get the result use this class
 * @author Adrian Garcia Lomas
 */
public class CoverResult {

    private URL smallCover;
    private URL mediumCover;
    private URL largerCover;

    public URL getSmallCover() {
        return smallCover;
    }

    public void setSmallCover(URL smallCover) {
        this.smallCover = smallCover;
    }

    public URL getMediumCover() {
        return mediumCover;
    }

    public void setMediumCover(URL mediumCover) {
        this.mediumCover = mediumCover;
    }

    public URL getLargerCover() {
        return largerCover;
    }

    public void setLargerCover(URL largerCover) {
        this.largerCover = largerCover;
    }

    public boolean hasSmallCover(){

        if(this.smallCover.equals(null))
            return false;
        else
            return true;
    }

    public boolean hasMediumCover(){

        if(this.mediumCover.equals(null))
            return false;
        else
            return true;
    }

    public boolean hasLargerCover(){

        if(this.largerCover.equals(null))
            return false;
        else
            return true;
    }

}

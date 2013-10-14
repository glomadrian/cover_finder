package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Adrian Garcia Lomas
 */
public class URLImageHandlerTest extends TestCase{


    public void testIfUrlImageHandlerWorks(){

        URL url = null;
        InputStream imageInputStream = null;

        try {
            url = new URL("http://upload.wikimedia.org/wikipedia/commons/3/3e/Phalaenopsis_JPEG.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }



        IImageHandler imageHandler = new URLImageHandler();
        try {
            imageInputStream =  imageHandler.getImage(url);
        } catch (ImageNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        Assert.assertNotNull(imageInputStream);
    }



    public  void testIfURLThatNotExitsRaiseException(){

        URL url = null;
        InputStream imageInputStream = null;

        try {
            url = new URL("http://notweb.com/notExistingimage.png");
        } catch (MalformedURLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        IImageHandler imageHandler = new URLImageHandler();
        try {
            imageInputStream =  imageHandler.getImage(url);
        } catch (ImageNotFoundException e) {
            Assert.assertNotNull(e);
        }



    }
}

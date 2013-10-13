package es.gldos.coverFinder.handler;

import es.gldos.coverFinder.exception.ImageNotFoundException;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.InputStream;

/**
 * @author Adrian Garcia Lomas
 */
public class CustomFolderImageHandlerTest extends TestCase {


    public void testIfCustomFolderlImageHandlerWorks(){

        InputStream imageInputStream = null;


        IImageHandler imageHandler = new CustomFolderImageHandler("src/test/testFolder","test.png");

        try {

            InputStream inputStream = imageHandler.getImage();
            assertNotNull(inputStream);

        } catch (ImageNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }


    public void testIfCustomFolderImageHandlerRaiseException(){


        InputStream imageInputStream = null;


        IImageHandler imageHandler = new CustomFolderImageHandler("fakeFolder","fakeImage.png");

        try {

            InputStream inputStream = imageHandler.getImage();
            assertNull(inputStream);

        } catch (ImageNotFoundException e) {
            Assert.assertNotNull(e);
        }
    }




}

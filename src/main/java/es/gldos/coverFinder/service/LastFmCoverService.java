package es.gldos.coverFinder.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.gldos.coverFinder.exception.APIKeyServiceException;
import es.gldos.coverFinder.exception.CoverNotFoundException;
import es.gldos.coverFinder.exception.ServiceErrorException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Adrian Garcia Lomas
 */
public class LastFmCoverService implements ICoverService {

    private final static Logger LOG = Logger.getLogger(LastFmCoverService.class.getName());

    /**
     * Serivces url and method
     */
    private static final String SERVICE_URL =  "http://ws.audioscrobbler.com/2.0/";
    private static final String METHOD = "?method=track.getInfo&";
    private static final String TYPE = "json";

    /**
     * URL acepted params
     */

    private static final String ARTIST_PARAM = "artist";
    private static final String TRACK_PARAM = "track";
    private static final String FORMAT_PARAM = "format";
    private static final String APIKEY_PARAM = "api_key";
    /**
     * Service objects name
     */
    private static final String TRACK_OBJECT = "track";
    private static final String ALBUM_OBJECT = "album";
    private static final String IMAGE_OBJECT = "image";
    private static final String TEXT_PROPERTIE = "#text";
    private static final String ERROR_PROPERTIE = "error";

    private ArrayList<URL> imageUrlList;
    private String apiKey;
    private String secret;

    public LastFmCoverService(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    @Override
    public ArrayList<URL> searchCover(String title, String artist) throws ServiceErrorException, CoverNotFoundException {

        try {
            String jsonResponse = performSearch(title,artist);

            JsonElement jelement = new JsonParser().parse(jsonResponse);
            JsonObject jobject = jelement.getAsJsonObject();

            checkValidResult(jobject);

            JsonObject trackObject = jobject.getAsJsonObject(TRACK_OBJECT);
            JsonObject album = trackObject.get(ALBUM_OBJECT).getAsJsonObject();
            JsonArray imageArray = album.get(IMAGE_OBJECT).getAsJsonArray();

            if(imageArray.size()<=0)
                throw new CoverNotFoundException();

            //If not problem until here, now create arraylist

            imageUrlList = new ArrayList<URL>();

            for(int i=0;i<imageArray.size();i++){

                String urlString = imageArray.get(i).getAsJsonObject().get(TEXT_PROPERTIE).getAsString();
                URL url = new URL(urlString);
                imageUrlList.add(url);
            }


            return imageUrlList;

        } catch (IOException e ) {
            throw new ServiceErrorException();
        } catch (URISyntaxException e) {
            throw new ServiceErrorException();
        } catch (HttpException e) {
            throw new ServiceErrorException();
        } catch (APIKeyServiceException e) {
            throw new ServiceErrorException();
        }


    }

    private String performSearch(String title, String artist) throws IOException, URISyntaxException, HttpException {


        //Objeto de tipo httpCliente que hara la conexion

        DefaultHttpClient httpClient = new DefaultHttpClient();


        List<NameValuePair> params = new LinkedList<NameValuePair>();

        params.add(new BasicNameValuePair(ARTIST_PARAM,artist));
        params.add(new BasicNameValuePair(TRACK_PARAM, title));
        params.add(new BasicNameValuePair(FORMAT_PARAM, TYPE));
        params.add(new BasicNameValuePair(APIKEY_PARAM, apiKey));



        String paramString = URLEncodedUtils.format(params, "utf-8");

        HttpGet httpGet = new HttpGet(SERVICE_URL+METHOD+paramString);


        //Se crea un objeto httpResponse para almacenar la respuesta
        HttpResponse httpResponse = httpClient.execute(httpGet);

        //Respuesta
        HttpEntity httpEntity = httpResponse.getEntity();

        //is es un inputStream
        InputStream is = httpEntity.getContent();

        //Se crea un reader que leera el objeto is (inputStream)
        BufferedReader reader = new BufferedReader(new InputStreamReader(is), 8);
        //sb es un ojbeto de tipo stringbuildier, sirve para ir creando un string mientras se lee el reader
        StringBuilder sb = new StringBuilder();
        //linea que se leera del buffer
        String line = null;

        //Bucle que leera lineas del buffer mientras no sea nulo,
        while ((line = reader.readLine()) != null) {

            //Cada linea extraida del buffer se añade al String buildier
            sb.append(line + "\n");
        }
        //Finalmente se cierra el inputStream
        is.close();

        //Guardamos el String creado en una variable de tipo String, contiene el objeto en notacion JSON

        LOG.info("LastFM Search: "+SERVICE_URL+METHOD+paramString);
        LOG.info("LastFm Return: "+sb.toString());

        return sb.toString();

    }

    /**
     * Check if a json response if valid and contains the data what looking for, if not throw correct exception
     * @param jsonResponseObject
     * @return
     */
    private void checkValidResult(JsonObject jsonResponseObject) throws APIKeyServiceException, CoverNotFoundException {

        //TODO check result, if Invalid api key throw service exception if not found artist or song throw coverNotFoundException

         if(jsonResponseObject.has(ERROR_PROPERTIE)){

             int error = jsonResponseObject.get(ERROR_PROPERTIE).getAsInt();

            switch (error){

                case 10:
                    throw new APIKeyServiceException();
                case 6:
                    throw new CoverNotFoundException();
            }

         };

    }
}

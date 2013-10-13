package es.gldos.coverFinder.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import es.gldos.coverFinder.cover.CoverResult;
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
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Adrian Garcia Lomas
 */
public class ITunesCoverService implements ICoverService {

    private final static Logger LOG = Logger.getLogger(ITunesCoverService.class.getName());


    /**
     * Serivces url and method
     */

    private static final String SERVICE_URL = "https://itunes.apple.com/search?";

    /**
     * URL acepted params
     */

    private static final String TERM_PARAM = "term";
    private static final String LIMIT_PARAM = "limit";

     /**
     *Service object name
     */

    private static final String RESULT_COUNT = "resultCount";
    private static final String RESULT_ARRAY = "results";
    private static final String ARTWORK_30 = "artworkUrl30";
    private static final String ARTWORK_60 = "artworkUrl60";
    private static final String ARTWORK_100 = "artworkUrl100";



     private CoverResult coverResult;


    private String performSearch(String title, String artist) throws IOException, URISyntaxException, HttpException {


        String searchParam = artist+" "+title;

        //Objeto de tipo httpCliente que hara la conexion

        DefaultHttpClient httpClient = new DefaultHttpClient();


        List<NameValuePair> params = new LinkedList<NameValuePair>();

        params.add(new BasicNameValuePair(TERM_PARAM,searchParam));
        params.add(new BasicNameValuePair(LIMIT_PARAM, "1"));


        String paramString = URLEncodedUtils.format(params, "utf-8");

        HttpGet httpGet = new HttpGet(SERVICE_URL+paramString);


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

        LOG.info("ITunes Search: "+SERVICE_URL+paramString);
        LOG.info("ITunes Return: "+sb.toString());

        return sb.toString();

    }

    @Override
    public CoverResult searchCover(String title, String artist) throws ServiceErrorException, CoverNotFoundException {

        try {
            String jsonResponse = performSearch(title,artist);

            JsonElement jelement = new JsonParser().parse(jsonResponse);
            JsonObject jobject = jelement.getAsJsonObject();
            checkValidResult(jobject);

            JsonArray results = jobject.getAsJsonArray(RESULT_ARRAY);

            if (results.size()>0){

                coverResult = new CoverResult();


                JsonObject track = results.get(0).getAsJsonObject();

                if(track.has(ARTWORK_100))
                {
                    String urlString = track.get(ARTWORK_100).getAsString();
                    URL url = new URL(urlString);
                    coverResult.setLargerCover(url);
                }
                else if (track.has(ARTWORK_60))
                {
                    String urlString = track.get(ARTWORK_60).getAsString();
                    URL url = new URL(urlString);
                    coverResult.setMediumCover(url);
                }
                else if (track.has(ARTWORK_30))
                {
                    String urlString = track.get(ARTWORK_30).getAsString();
                    URL url = new URL(urlString);
                    coverResult.setSmallCover(url);
                }
                else {
                    throw  new CoverNotFoundException();
                }


            }

        } catch (IOException e) {
            throw new ServiceErrorException();
        } catch (URISyntaxException e) {
            throw new ServiceErrorException();
        } catch (HttpException e) {
            throw new ServiceErrorException();
        }

        return coverResult;
    }


    private void checkValidResult(JsonObject jsonResponseObject) throws CoverNotFoundException, ServiceErrorException {

        //TODO check result, if Invalid api key throw service exception if not found artist or song throw coverNotFoundException

        if(jsonResponseObject.has(RESULT_COUNT)){

            int error = jsonResponseObject.get(RESULT_COUNT).getAsInt();

            switch (error){

                case 0:
                    throw new CoverNotFoundException();
            }

        }
        else
        {
            throw new ServiceErrorException();
        }

    }

    }

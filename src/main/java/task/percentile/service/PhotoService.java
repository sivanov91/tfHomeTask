package task.percentile.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import task.percentile.dto.PhotoInfoDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;


public class PhotoService {

    private static String PHOTO_INFO_URL = "https://api.unsplash.com/photos";
    // token в репозитории т.к. зареген на временную почту и не представляет ценности.
    private static String AUTH_KEY = "f6dff49ee385c66eff893596c59194193321399b3171f6a7a4fd65ab4810ddf4";

    /**
     * Метод возвращает список методанных заданного количество фотографий.
     *
     * @param page номер страницы.
     * @param size количество записей на странице.
     * @return список методанных фотограций.
     */
    public List<PhotoInfoDto> getPhotos(Integer page, Integer size) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            URI uri = new URIBuilder(PHOTO_INFO_URL)
                    .setParameter("per_page", size.toString())
                    .setParameter("page", page.toString())
                    .setParameter("order_by", "latest")
                    .build();

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Authorization", String.format("Bearer %s", AUTH_KEY));

            HttpResponse response = httpclient.execute(httpGet);

            return toDto(response);

        } catch (URISyntaxException | IOException e) {
            // TODO обработка ошибок
            throw new RuntimeException(e);
        }
    }

    private List<PhotoInfoDto> toDto(HttpResponse response) throws IOException {
        return getMapper()
                .readValue(EntityUtils.toString(response.getEntity()), new TypeReference<List<PhotoInfoDto>>() {
                });
    }

    private ObjectMapper getMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


}

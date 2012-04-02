/**
 * User: Maciej Poleski
 * Date: 30.03.12
 * Time: 21:57
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.*;
import com.google.gdata.client.photos.*;
import com.google.gdata.data.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.photos.*;
import com.google.gdata.util.AuthenticationException;

class Z {
    public static void main(String[] args) throws AuthenticationException, MalformedURLException {
        PicasawebService myService = new PicasawebService("exampleCo-exampleApp-1");
        myService.setUserCredentials("liz@gmail.com", "mypassword");
        URL feedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/username");

        Query myQuery = new Query(feedUrl);
        myQuery.setStringCustomParameter("kind", "photo");
        myQuery.setStringCustomParameter("tag", "puppy");

        AlbumFeed searchResultsFeed = myService.query(myQuery, AlbumFeed.class);

        for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
            System.out.println(photo.getTitle().getPlainText());
        }
    }
}
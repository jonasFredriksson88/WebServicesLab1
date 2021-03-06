package plugin;

import spi.Spi;
import spi.Url;
import utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

@Url("/files")
public class GetFile implements Spi {
    @Override
    public Response handleRequest(Request request) throws IOException {

        Response response = new Response();

        if (request.type == HTTPType.POST) {
            return HttpStatus.status400();
        }
        response.type = request.type;

        File file;

        if (request.urlParams.containsKey("image")) {
            String image = request.urlParams.get("image");
            file = Path.of("/web/images", image).toFile();
        } else if (request.urlParams.containsKey("file")) {
            String requestedFile = request.urlParams.get("file");
            file = Path.of("/web/files", requestedFile).toFile();
        } else {
            return HttpStatus.status400();
        }

        if (!(file.exists() && !file.isDirectory())) {
            return HttpStatus.status404();
        } else {
            return bodyFromFile(response, file);
        }
    }

    private Response bodyFromFile(Response response, File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] body = new byte[(int) file.length()];
        fileInputStream.read(body);
        response.contentType = Utils.getContentType(file);
        response.body = body;
        response.status = HttpStatus.status200();
        return response;

    }
}

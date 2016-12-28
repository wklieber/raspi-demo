/*
 *     Raspberry PI sample code
 *     Copyright (C) 2016 -  2016  Werner Klieber
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package wklieber.raspi.ws;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wklieber on 28.12.2016.
 */
@Path("/camera")
@Produces(MediaType.APPLICATION_JSON)
public class CameraResource {
    private final AtomicLong counter;
    private final SimpleDateFormat formatter;
    private final RPiCamera camera;

    public CameraResource() throws FailedToRunRaspistillException {
        this.counter = new AtomicLong();
        camera = new RPiCamera();

        String dateFormat = "yyyy-MM-dd-HH-mm-ss-SSS";
        formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Produces("image/png")
    public Response takeImage(@QueryParam("width") Integer width,
                              @QueryParam("height") Integer height) throws IOException, InterruptedException {
        //2592x1944
        if (width == null) width = 2592;
        if (height == null) height = 1944;

        BufferedImage bufferedImage = camera.takeBufferedStill(width, height);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", out);
        byte[] imageData = out.toByteArray();


        String filename;
        synchronized (formatter) {
            filename = "image" + formatter.format(new Date(System.currentTimeMillis())) + ".png";
        }

        return Response.ok(new ByteArrayInputStream(imageData)).header("Content-Disposition",
                "attachment; filename=" + filename).build();
    }

    @GET
    @Timed
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format("raspberry", name.or("pi"));
        return new Saying(counter.incrementAndGet(), value);
    }
}

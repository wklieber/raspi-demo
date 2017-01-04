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

import com.hopding.jrpicam.RPiCamera;
import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wklieber on 28.12.2016.
 */
@Path("/camera")
//@Api("/camera")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CameraResource {
    private final AtomicLong counter;
    private final SimpleDateFormat formatter;
    private final RPiCamera camera;
    //private final Webcam webcam;

    public CameraResource() throws FailedToRunRaspistillException {
        this.counter = new AtomicLong();
        camera = new RPiCamera();

        //webcam = Webcam.getDefault();
        //webcam.open();

        String dateFormat = "yyyy-MM-dd-HH-mm-ss-SSS";
        formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @GET
    @Produces("image/png")
    @Path("/takeImage")
    public Response takeImage(@QueryParam("width") Integer width,
                              @QueryParam("height") Integer height) throws IOException, InterruptedException {
        boolean useApi2 = false;

        //2592x1944
        if (width == null) width = 2592;
        if (height == null) height = 1944;


        BufferedImage bufferedImage;

        //if(width != null) {
        //webcam.setViewSize(new Dimension(width, height));
        //} else bufferedImage = webcam.getImage();
        bufferedImage = camera.takeBufferedStill(width, height);


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

    /**
     * take video until timout reached and retrieves it. If no timeout set, video is streamed directly
     *
     * @param length length of the video in ms. default is 10 sec. 0 means no timeout.
     */
    @GET
    @Produces("video/avi")
    @Path("/takeVideo")
    public Response takeVideo(@QueryParam("width") Integer width,
                              @QueryParam("height") Integer height,
                              @QueryParam("length") Integer length) throws IOException, InterruptedException {

        System.out.println("takeVideo: width=" + width + ", height=" + height + ", length=" + length);

        //1920x1080
        if (width == null) width = 1920;
        if (height == null) height = 1080;
        if (length == null) length = 10 * 1000;

        boolean directStream = (length == 0);
        File file = new File("/tmp/testvid.h264");

        System.out.println("START PROGRAM, direct=" + directStream);
        String command = "raspivid -w " + width + " -h " + height + " -n -t " + length + " -o -";
        System.out.println(command);
        Process p = Runtime.getRuntime().exec(command);

        InputStream responseStream;
        if (directStream) {
            responseStream = new BufferedInputStream(p.getInputStream());
        } else {
            streamToFile(file, p);
            System.out.println("END PROGRAM: size=" + file.length() + "B");
            responseStream = new FileInputStream(file);
        }

        String filename;
        synchronized (formatter) {
            filename = "testvid.h264";//"video" + formatter.format(new Date(System.currentTimeMillis())) + ".h264";
        }

        try {

            return Response.ok(responseStream).header("Content-Disposition",
                    "attachment; filename=" + filename).build();
        } catch (Exception e) {
            System.err.println("error streaming file: " + file);
            e.printStackTrace();
            throw e;
        }

    }

    private void streamToFile(File file, Process p) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(p.getInputStream());
             //Direct methode p.getInputStream().read() also possible, but BufferedInputStream gives 0,5-1s better performance
             FileOutputStream fos = new FileOutputStream(file, false);) {

            System.out.println("start writing");
            int read = bis.read();
            fos.write(read);

            while (read != -1) {
                read = bis.read();
                fos.write(read);
            }
        } catch (Exception e) {
            System.err.println("error writing file: " + file);
            e.printStackTrace();
            throw e;
        }
    }
}

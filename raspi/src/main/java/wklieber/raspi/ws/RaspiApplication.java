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

import com.hopding.jrpicam.exceptions.FailedToRunRaspistillException;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * web servica microservice to provide some remote call to transfer raspi data to a pc.
 * Created by wklieber on 28.12.2016.
 */
public class RaspiApplication extends Application<RaspiConfiguration> {
    public static void main(String[] args) throws Exception {
        args = new String[]{"server", System.getProperty("user.dir") + "/conf/default.yml"};
        new RaspiApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<RaspiConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(RaspiConfiguration configuration,
                    Environment environment) throws FailedToRunRaspistillException {
        final CameraResource resource = new CameraResource(
                //configuration.getTemplate(),
                //configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck = new TemplateHealthCheck("");
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);

        final CameraResource piCameraResource = new CameraResource();
        environment.jersey().register(piCameraResource);
    }

}

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

package wklieber.raspi;

import com.pi4j.system.NetworkInfo;
import com.pi4j.system.SystemInfo;
import wklieber.raspi.tools.DemoView;
import wklieber.raspi.tools.MiscTools;

import java.io.IOException;
import java.text.ParseException;

public class Raspi {

    public static void main(String[] args) throws Exception {
        Raspi tool = new Raspi();
        //tool.checkRaspi();
        //tool.printHwDetails();

        tool.image();
    }

    public void image() throws Exception {
        //RaspiStill camera = new RaspiStill();
        //RPiCamera piCamera = new RPiCamera("/home/pi/Pictures");
        //BufferedImage image = piCamera.takeBufferedStill();

        DemoView demoView = new DemoView();
        demoView.show();
    }

    public static void checkRaspi() throws Exception {
        try {
            // next call will fail when not  on raspi
            SystemInfo.getSerial();
        } catch (Exception e) {
            throw new Exception("You're not running on The PI Platform");
        }
    }

    private static void printHwDetails() throws IOException, InterruptedException, ParseException {

        // display a few of the available system information properties
        System.out.println("----------------------------------------------------");
        System.out.println("HARDWARE INFO");
        System.out.println("----------------------------------------------------");
        System.out.println("Serial Number     :  " + SystemInfo.getSerial());
        System.out.println("CPU Revision      :  " + SystemInfo.getCpuRevision());
        System.out.println("CPU Architecture  :  " + SystemInfo.getCpuArchitecture());
        System.out.println("CPU Part          :  " + SystemInfo.getCpuPart());
        System.out.println("CPU Temperature   :  " + SystemInfo.getCpuTemperature());
        System.out.println("CPU Core Voltage  :  " + SystemInfo.getCpuVoltage());
        System.out.println("CPU Model Name    :  " + SystemInfo.getModelName());
        System.out.println("Processor         :  " + SystemInfo.getProcessor());
        System.out.println("Hardware Revision :  " + SystemInfo.getRevision());
        System.out.println("Is Hard Float ABI :  " + SystemInfo.isHardFloatAbi());
        System.out.println("Board Type        :  " + SystemInfo.getBoardType().name());

        System.out.println("----------------------------------------------------");
        System.out.println("MEMORY INFO");
        System.out.println("----------------------------------------------------");
        System.out.println("Total Memory      :  " + MiscTools.formatNumber(SystemInfo.getMemoryTotal(), 1));
        System.out.println("Used Memory       :  " + MiscTools.formatNumber(SystemInfo.getMemoryUsed(), 1));
        System.out.println("Free Memory       :  " + MiscTools.formatNumber(SystemInfo.getMemoryFree(), 1));
        System.out.println("Shared Memory     :  " + SystemInfo.getMemoryShared());
        System.out.println("Memory Buffers    :  " + SystemInfo.getMemoryBuffers());
        System.out.println("Cached Memory     :  " + SystemInfo.getMemoryCached());
        System.out.println("SDRAM_C Voltage   :  " + SystemInfo.getMemoryVoltageSDRam_C());
        System.out.println("SDRAM_I Voltage   :  " + SystemInfo.getMemoryVoltageSDRam_I());
        System.out.println("SDRAM_P Voltage   :  " + SystemInfo.getMemoryVoltageSDRam_P());

        System.out.println("----------------------------------------------------");
        System.out.println("OPERATING SYSTEM INFO");
        System.out.println("----------------------------------------------------");
        System.out.println("OS Name           :  " + SystemInfo.getOsName());
        System.out.println("OS Version        :  " + SystemInfo.getOsVersion());
        System.out.println("OS Architecture   :  " + SystemInfo.getOsArch());
        System.out.println("OS Firmware Build :  " + SystemInfo.getOsFirmwareBuild());
        System.out.println("OS Firmware Date  :  " + SystemInfo.getOsFirmwareDate());

        System.out.println("----------------------------------------------------");
        System.out.println("JAVA ENVIRONMENT INFO");
        System.out.println("----------------------------------------------------");
        System.out.println("Java Vendor       :  " + SystemInfo.getJavaVendor());
        System.out.println("Java Vendor URL   :  " + SystemInfo.getJavaVendorUrl());
        System.out.println("Java Version      :  " + SystemInfo.getJavaVersion());
        System.out.println("Java VM           :  " + SystemInfo.getJavaVirtualMachine());
        System.out.println("Java Runtime      :  " + SystemInfo.getJavaRuntime());

        System.out.println("----------------------------------------------------");
        System.out.println("NETWORK INFO");
        System.out.println("----------------------------------------------------");

        // display some of the network information
        System.out.println("Hostname          :  " + NetworkInfo.getHostname());
        for (String ipAddress : NetworkInfo.getIPAddresses())
            System.out.println("IP Addresses      :  " + ipAddress);
        for (String fqdn : NetworkInfo.getFQDNs())
            System.out.println("FQDN              :  " + fqdn);
        for (String nameserver : NetworkInfo.getNameservers())
            System.out.println("Nameserver        :  " + nameserver);

        System.out.println("----------------------------------------------------");
        System.out.println("CODEC INFO");
        System.out.println("----------------------------------------------------");
        System.out.println("H264 Codec Enabled:  " + SystemInfo.getCodecH264Enabled());
        System.out.println("MPG2 Codec Enabled:  " + SystemInfo.getCodecMPG2Enabled());
        System.out.println("WVC1 Codec Enabled:  " + SystemInfo.getCodecWVC1Enabled());

        System.out.println("----------------------------------------------------");
        System.out.println("CLOCK INFO");
        System.out.println("----------------------------------------------------");
        System.out.println("ARM Frequency     :  " + SystemInfo.getClockFrequencyArm());
        System.out.println("CORE Frequency    :  " + SystemInfo.getClockFrequencyCore());
        System.out.println("H264 Frequency    :  " + SystemInfo.getClockFrequencyH264());
        System.out.println("ISP Frequency     :  " + SystemInfo.getClockFrequencyISP());
        System.out.println("V3D Frequency     :  " + SystemInfo.getClockFrequencyV3D());
        System.out.println("UART Frequency    :  " + SystemInfo.getClockFrequencyUART());
        System.out.println("PWM Frequency     :  " + SystemInfo.getClockFrequencyPWM());
        System.out.println("EMMC Frequency    :  " + SystemInfo.getClockFrequencyEMMC());
        System.out.println("Pixel Frequency   :  " + SystemInfo.getClockFrequencyPixel());
        System.out.println("VEC Frequency     :  " + SystemInfo.getClockFrequencyVEC());
        System.out.println("HDMI Frequency    :  " + SystemInfo.getClockFrequencyHDMI());
        System.out.println("DPI Frequency     :  " + SystemInfo.getClockFrequencyDPI());


        System.out.println();
        System.out.println();
    }
}
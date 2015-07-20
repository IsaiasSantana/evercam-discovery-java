package io.evercam.network;

import java.util.ArrayList;

import io.evercam.Vendor;
import io.evercam.network.discovery.DiscoveredCamera;
import io.evercam.network.discovery.MacAddress;
import io.evercam.network.discovery.PortScan;
import io.evercam.network.query.EvercamQuery;

public abstract class IdentifyCameraRunnable implements Runnable
{
    private String ip;

    public IdentifyCameraRunnable(String ip)
    {
        this.ip = ip;
    }

    @Override
    public void run()
    {
        try
        {
            String macAddress = MacAddress.getByIpAndroid(ip);
            if (!macAddress.equals(Constants.EMPTY_MAC))
            {
                Vendor vendor = EvercamQuery.getCameraVendorByMac(macAddress);
                if (vendor != null)
                {
                    String vendorId = vendor.getId();
                    if (!vendorId.isEmpty())
                    {
                        // Then fill details discovered from IP scan
                        DiscoveredCamera camera = new DiscoveredCamera(ip);
                        camera.setMAC(macAddress);
                        camera.setVendor(vendorId);

                        // Start port scan
                        PortScan portScan = new PortScan(null);
                        portScan.start(ip);
                        ArrayList<Integer> activePortList = portScan.getActivePorts();

                        if(activePortList.size() > 0)
                        {
                        	camera = camera.mergePorts(activePortList);

                        	onCameraFound(camera, vendor);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        onFinished();
    }
    
    public abstract void onCameraFound(DiscoveredCamera discoveredCamera, Vendor vendor);
    public abstract void onFinished();
}
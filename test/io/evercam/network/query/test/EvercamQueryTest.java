package io.evercam.network.query.test;

import static org.junit.Assert.*;
import io.evercam.API;
import io.evercam.EvercamException;
import io.evercam.Vendor;
import io.evercam.network.query.EvercamQuery;

import org.junit.Test;

public class EvercamQueryTest 
{
	@Test
	public void testGetCameraVendorByMac() 
	{
		//FIXME: Remove developer key and ID
		API.setDeveloperKeyPair("83d44e31781b88c0c73bded662be28bc", "614f4cb6");
		assertNull(EvercamQuery.getCameraVendorByMac("00:00:00"));
		assertNotNull(EvercamQuery.getCameraVendorByMac("8c:e7:48"));
	}
	
	@Test
	public void testGetDefaults() throws EvercamException
	{
		//FIXME: Remove developer key and ID
		API.setDeveloperKeyPair("83d44e31781b88c0c73bded662be28bc", "614f4cb6");
		Vendor hikvision = EvercamQuery.getCameraVendorByMac("8c:e7:48");
		assertEquals("admin", EvercamQuery.getDefaultUsernameByVendor(hikvision));
		assertEquals("12345",EvercamQuery.getDefaultPasswordByVendor(hikvision));
		assertEquals("Streaming/Channels/1/picture", EvercamQuery.getDefaultJpgUrlByVendor(hikvision));
		assertEquals("h264/ch1/main/av_stream", EvercamQuery.getDefaultH264UrlByVendor(hikvision));
	}
}
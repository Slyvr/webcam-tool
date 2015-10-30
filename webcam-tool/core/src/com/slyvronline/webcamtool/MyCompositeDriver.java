package com.slyvronline.webcamtool;

import com.github.sarxos.webcam.WebcamCompositeDriver;
import com.github.sarxos.webcam.ds.buildin.WebcamDefaultDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;

public class MyCompositeDriver extends WebcamCompositeDriver {

	public MyCompositeDriver() {
		add(new WebcamDefaultDriver());
		add(new IpCamDriver());
	}
}

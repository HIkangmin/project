package com.munbanggu.common.base;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BaseRepoPath {

	public static String getImagePath() {
		// 개발시에만 사용 할 것. 경우에 따라선, 베포시 동작하지 않을 수 있음.
		String folderPaString = "/file_repo";

		String os = System.getProperty("os.name");

		String ubuntuPathString = "/var/lib/tomcat9/webapps" + folderPaString;
		// "/var/lib/tomcat9/webapps/file_repo"

		String macPathString = "mac Path is undefine" + folderPaString;
		// 미정

		if (os.contains("Win")) {
			return System.getProperty("wtp.deploy").split(".metadata")[0].replace('\\', '/') + "/" + "munbanggu"
					+ folderPaString;
		} else if (os.contains("Mac")) {
			return macPathString;
		} else if (os.contains("Linux")) {
			return ubuntuPathString;
		} else {
			return "No path is defined for this operating system., com.munbanggu.common.base.BaseRepoPath";

		}

	}
}

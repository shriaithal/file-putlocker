package edu.sjsu.cloud.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlerHelper {

	private Map<String, String> fileProperties = new HashMap<String, String>();
	//Logger LOG = LoggerFactory.getLogger(AWSHelper.class);

	public Map<String, String> getFileProperties() {
		return fileProperties;
	}

	public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	@PostConstruct
	private void loadPropertFiles() {
		Properties properties = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("/config/application.properties");
		if (input != null) {

			try {
				properties.load(input);
			} catch (IOException e) {
				//LOG.error("I/O exception", e);
			} catch (IllegalArgumentException e) {
				//LOG.error("Malfunction properties format", e);
			}

			for (String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				fileProperties.put(key, value);
			}
		} else {
			//LOG.error("I/O exception");
		}
	}

}

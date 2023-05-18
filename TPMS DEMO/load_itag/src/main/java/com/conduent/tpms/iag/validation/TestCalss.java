package com.conduent.tpms.iag.validation;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class TestCalss {

	public void run() throws IOException, InterruptedException {
		FileParserImpl parser = new GenericITagFileParserImpl();
//		parser.fileParseTemplate(null);
	}

}

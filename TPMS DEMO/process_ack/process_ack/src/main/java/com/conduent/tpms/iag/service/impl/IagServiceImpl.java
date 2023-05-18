package com.conduent.tpms.iag.service.impl;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conduent.tpms.iag.parser.agency.IagFixlengthParser;
import com.conduent.tpms.iag.service.IagService;

@Service
public class IagServiceImpl implements IagService {

	@Autowired
	IagFixlengthParser iagFixlengthParser;

	@Override
	public void process() {
		try {
			iagFixlengthParser.fileParseTemplate();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
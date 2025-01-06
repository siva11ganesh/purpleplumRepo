package com.products.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class PdfService {

	public byte[] generatePdf() throws Exception {
		InputStream xmlFoStream = new ClassPathResource("data.xml").getInputStream();
		InputStream xstlStream = new ClassPathResource("template.xsl").getInputStream();
		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(new StreamSource(xstlStream));
		StreamSource src = new StreamSource(xmlFoStream);
		SAXResult res = new SAXResult(fop.getDefaultHandler());
		transformer.transform(src, res);
		return out.toByteArray();
	}

}

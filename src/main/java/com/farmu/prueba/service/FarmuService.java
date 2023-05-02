package com.farmu.prueba.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.farmu.prueba.dto.URLRequest;
import com.farmu.prueba.entity.URL;
import com.farmu.prueba.repository.FarmuRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * Class for builder the logic that response the values
 * 
 * For this challenge, I used some method and function, but there are many other
 * possibilities to encode, decode, upload and download image
 * 
 * @author Jhon Lara
 *
 */
@Service
public class FarmuService {

	@Autowired
	private FarmuRepository farmuRepository;

	// Application constants
	private final static String characters = "0123456789A*BCEFGIJKLMNÑLFOPQZ";
	private final static int pivot = 12;
	private final static String folder = "C:\\Users\\SoporteIT\\Pictures";

	public FarmuService() {
	}

	/**
	 * Method that shorts an url and gets identifier
	 * 
	 * @param request This has large url
	 * @return identifier encoded
	 */
	public String shortUrl(URLRequest request) throws Exception {
		var url = new URL();
		url.setLongUrl(request.getLongUrl());
		var entity = farmuRepository.save(url);
		return encoder(entity.getId());
	}

	/**
	 * This method gets the large url by its id
	 * 
	 * @param idShortUrl Url's identifier
	 * @return Large url with the identifier that sent
	 */
	public String getLargeUrl(String idShortUrl) {
		var entity = farmuRepository.findById(decoder(idShortUrl))
				.orElseThrow(() -> new EntityNotFoundException("There is not entity with " + idShortUrl));
		return entity.getLongUrl();
	}

	/**
	 * This method resize an image to new size that the user indicate
	 * 
	 * @param sourceFile The image that user upload
	 * @param imageSize  The new size that user indicate
	 * @return Process state
	 */
	public boolean resizeImage(MultipartFile sourceFile, Integer imageSize) {
		try {
			BufferedImage bufferedImage = ImageIO.read(sourceFile.getInputStream());
			BufferedImage outputImage = Scalr.resize(bufferedImage, imageSize);
			String newFileName = FilenameUtils.getBaseName(sourceFile.getName()) + "_" + imageSize.toString() + "."
					+ "jpg";
			Path path = Paths.get(folder, newFileName);
			File newImageFile = path.toFile();
			ImageIO.write(outputImage, "JPG", newImageFile);
			outputImage.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Method that encodes an id with algorithm based on Cesar cipher
	 * 
	 * @param id URLs identifier
	 * @return Id encoded
	 */
	public String encoder(Long id) {
		String encodedText = "";
		String chainText = String.valueOf(id);
		char caracter;
		for (int i = 0; i < chainText.length(); i++) {
			caracter = chainText.charAt(i);
			int pos = characters.indexOf(caracter);
			if (pos == -1) {
				encodedText += caracter;
			} else {
				encodedText += characters.charAt((pos + pivot) % characters.length());
			}
		}
		return encodedText;
	}

	/**
	 * Method that decodes an id with algorithm based on Cesar cipher
	 * 
	 * @param id URLs identifier
	 * @return Id decoded
	 */
	public Long decoder(String chainText) {
		String textDecoded = "";
		char character;
		for (int i = 0; i < chainText.length(); i++) {
			character = chainText.charAt(i);
			int pos = characters.indexOf(character);
			if (pos == -1) {
				textDecoded += character;
			} else {
				if (pos - pivot < 0) {
					textDecoded += characters.charAt(characters.length() + (pos - pivot));
				} else {
					textDecoded += characters.charAt((pos - pivot) % characters.length());
				}
			}
		}
		return Long.parseLong(textDecoded);
	}

}

package com.farmu.prueba.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.farmu.prueba.dto.URLRequest;
import com.farmu.prueba.service.FarmuService;

/**
 * Controller for expose the services
 * 
 * @author jhonlara10
 */
@RestController
@RequestMapping("/farmu/api")
public class FarmuController {

	@Autowired
	private FarmuService farmuService;

	@PostMapping("url")
	public String shortUrl(@RequestBody URLRequest request) throws Exception {
		return farmuService.shortUrl(request);
	}

	@GetMapping("url/{idShortUrl}")
	public ResponseEntity<Void> getLargeUrlAndRedirect(@PathVariable String idShortUrl) throws Exception {
		var url = farmuService.getLargeUrl(idShortUrl);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
	}

	@PostMapping("image/uploadImage")
	public ResponseEntity<Void> uploadImage(@RequestParam("file") MultipartFile imageFile,
			@RequestParam("imageSize") Integer imageSize, RedirectAttributes redirectAttributes) {
		boolean resizeResult = farmuService.resizeImage(imageFile, imageSize);
		return resizeResult ? ResponseEntity.status(HttpStatus.OK).build()
				: ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
	}

}

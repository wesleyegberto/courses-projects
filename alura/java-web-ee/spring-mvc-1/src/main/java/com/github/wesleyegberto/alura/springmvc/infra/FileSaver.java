package com.github.wesleyegberto.alura.springmvc.infra;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {
	@Autowired
	private HttpServletRequest request;

	public String write(String baseFolder, MultipartFile file) {
		try {
			// obtem o caminho real no servidor
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			// gera o caminho completo com o nome do arquivo
			String path = realPath + "/" + file.getOriginalFilename();
			
			file.transferTo(new File(path));
			// retorna o caminho relativo ao contexto com o nome do arquivo
			return baseFolder + "/" + file.getOriginalFilename();
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
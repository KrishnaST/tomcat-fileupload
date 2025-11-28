package com.paytech.fileupload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    private final AppConfig appConfig;

    private static final String UPLOAD_DIR = "D:/uploads/";

    FileUploadController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }  // Directory to store uploaded files

    
    @GetMapping(value = { "/", "/upload"}, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String uploadFile() {
    	return "<!DOCTYPE html>\r\n"
    			+ "<html>\r\n"
    			+ "<head>\r\n"
    			+ "	<style type=\"text/css\">\r\n"
    			+ "		/* From Uiverse.io by guilhermeyohan */ \r\n"
    			+ "		.container {\r\n"
    			+ "		  max-width: 300px;\r\n"
    			+ "		  margin: 0 auto;\r\n"
    			+ "		  padding: 20px;\r\n"
    			+ "		  background-color: #13121269;\r\n"
    			+ "		  border-radius: 5px;\r\n"
    			+ "		}\r\n"
    			+ "		\r\n"
    			+ "		label {\r\n"
    			+ "		  font-weight: bold;\r\n"
    			+ "		  display: block;\r\n"
    			+ "		  margin-bottom: 10px;\r\n"
    			+ "		}\r\n"
    			+ "		\r\n"
    			+ "		.inpdddut[type=\"file\"] {\r\n"
    			+ "		  padding: 10px;\r\n"
    			+ "		  margin-bottom: 20px;\r\n"
    			+ "		  border: none;\r\n"
    			+ "		  background-color: #1aa3bb;\r\n"
    			+ "		  border-radius: 5px;\r\n"
    			+ "		  width: 100%;\r\n"
    			+ "		  cursor: pointer;\r\n"
    			+ "		}\r\n"
    			+ "		\r\n"
    			+ "		.inpdddut[type=\"submit\"] {\r\n"
    			+ "		  padding: 10px 20px;\r\n"
    			+ "		  background-color: #008CBA;\r\n"
    			+ "		  color: #fff;\r\n"
    			+ "		  border: none;\r\n"
    			+ "		  border-radius: 5px;\r\n"
    			+ "		  cursor: pointer;\r\n"
    			+ "		}\r\n"
    			+ "		\r\n"
    			+ "		.inpdddut[type=\"submit\"]:hover {\r\n"
    			+ "		  background-color: #006F8F;\r\n"
    			+ "		}\r\n"
    			+ "    \r\n"
    			+ "	</style>\r\n"
    			+ "\r\n"
    			+ "<meta charset=\"UTF-8\">\r\n"
    			+ "<title>Insert title here</title>\r\n"
    			+ "</head>\r\n"
    			+ "<body>\r\n"
    			+ "	<!-- From Uiverse.io by guilhermeyohan -->\r\n"
    			+ "	<div class=\"container\">\r\n"
    			+ "		<form method=\"POST\" action=\"https://gomouat.saraswatbank.com/exchange/upload\" enctype=\"multipart/form-data\">\r\n"
    			+ "			<label for=\"file\">Choose a file:</label> \r\n"
    			+ "			<input class=\"inpdddut\" name=\"file\" id=\"file\" type=\"file\">\r\n"
    			+ "    		<button type=\"submit\" class=\"inpdddut\">Send</button>\r\n"
    			+ "		</form>\r\n"
    			+ "	</div>\r\n"
    			+ "</body>\r\n"
    			+ "</html>";
    }
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file) {
    	System.out.println("upload request."+file.getName());
        if (!file.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Path filePath = uploadPath.resolve(file.getOriginalFilename());
                Files.write(filePath, file.getBytes());
                return "File Uploaded Successfully.";
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }
        return "File Upload Failed.";
    }
    
    @PostMapping(value = "/send", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String uploadFile(@RequestBody byte[] string) {
    	System.out.println("uploading file");
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if(!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(UUID.randomUUID().toString());
            Files.write(filePath, string);
            return "File Uploaded Successfully."+filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    
    }
}
package de.exxeta;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.jxls.template.SimpleExporter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.apachecommons.CommonsLog;

@SpringBootApplication
@RestController
@CommonsLog
public class JxlsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JxlsDemoApplication.class, args);
	}
	
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public void export(HttpServletResponse response) {
		List<Person> persons = Arrays.asList(new Person("Steffen", "Mall"), new Person("Daniel", "Weisser"));
		List<String> headers = Arrays.asList("First Name", "Last Name");
		try {
			response.addHeader("Content-disposition", "attachment; filename=People.xlsx");
			response.setContentType("application/vnd.ms-excel");
			new SimpleExporter().gridExport(headers, persons, "firstName, lastName, ", response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}
	}

}

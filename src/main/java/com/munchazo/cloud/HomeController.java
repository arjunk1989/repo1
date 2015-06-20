package com.munchazo.cloud;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.munchazo.cloud.dao.LocationDao;
import com.munchazo.cloud.persistance.Location;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

/**
 * Handles requests for the application home page.
 */
@RestController(value="/")
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	@Autowired
	private LocationDao locationDao;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@ApiOperation(value = "Get time details", notes = "This Service will return time and locale", response = HomeController.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 404, message = "Request not found") })
	@RequestMapping(value = "/time", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@ApiOperation(value = "Get location list", notes = "This Service will return the list of locations", response = HomeController.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 404, message = "Request not found") })
	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public List<Location> getLocations() {
		return locationDao.getLocations();
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@ApiOperation(value = "Get location list", notes = "This Service will return the list of locations", response = HomeController.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid Request"),
			@ApiResponse(code = 404, message = "Request not found") })
	@RequestMapping(value = "/addlocation", method = RequestMethod.POST)
	public String insertLocation(@RequestParam String id ,@RequestParam String locationName,
			@RequestParam String locationType) {
		Location location = new Location(Integer.parseInt(id), locationName, locationType);
		locationDao.insertLocation(location);
		return "success";
	}

}

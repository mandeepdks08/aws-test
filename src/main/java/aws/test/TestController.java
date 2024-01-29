package aws.test;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value = "/hotel-searchquery-list", method = RequestMethod.POST)
	public String getSearchResult(HttpServletResponse response, @RequestBody Object searchRequest) {
		response.setHeader("Content-Type", "application/json");
		return HotelSearchHandler.getSearchResult(searchRequest);
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public String pingPong() {
		return "pong";
	}
}

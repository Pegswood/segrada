package org.segrada.controller;

import com.google.inject.Inject;
import com.google.inject.servlet.RequestScoped;
import com.sun.jersey.api.view.Viewable;
import org.segrada.search.SearchEngine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright 2015 Maximilian Kalus [segrada@auxnet.de]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Search controller
 */
@Path("/search")
@RequestScoped
public class SearchController {
	@Inject
	private SearchEngine searchEngine;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Viewable index(
			@QueryParam("s") String term,
			@QueryParam("page") String page
	) {
		// filters:
		Map<String, String> filters = new HashMap<>();
		if (page != null) filters.put("page", page);

		// create model map
		Map<String, Object> model = new HashMap<>();
		model.put("paginationInfo", searchEngine.search(term, filters));
		model.put("searchTerm", term);
		return new Viewable("search/index", model);
	}
}

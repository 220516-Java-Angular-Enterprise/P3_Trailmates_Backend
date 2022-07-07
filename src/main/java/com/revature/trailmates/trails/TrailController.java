package com.revature.trailmates.trails;

import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trail")
public class TrailController {

    @Inject
    private final TrailService trailService;

    @Inject
    @Autowired
    public TrailController(TrailService trailService) {
        this.trailService = trailService;
    }

    /**
     * Returns a Trail Object with a matching id
     * @param id the id of the trail
     * @return Returns a Trail Object
     */
    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Optional<Trail> getTrailById(@PathVariable String id) { return trailService.getTrail(id); }

    /**
     * Returns All Trails on a given Page (10 Trails Per Page)
     * @param page
     * @return A List of at most 10 trails at a certain page
     */
    @CrossOrigin
    @GetMapping(value = "/getAll/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> getAllTrailsByPage(@PathVariable int page) { return trailService.getAllTrailsPage(page); }

    /**
     * Returns a List of Every Single Trail in the Database
     * @return List<Trail>
     */
    @CrossOrigin
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> getAllTrails() { return trailService.getAllTrails(); }

    /**
     * Returns a List of Trails the fit the search criteria of search_name on a specific page
     * @param search_name The value the user types into our search bar
     * @param page The page that they want to go to.
     * @return List<Trail> A list of at most 10 trails
     */
    @CrossOrigin
    @GetMapping(value = "search/{page}/{search_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> searchTrailByName(@PathVariable("search_name") String search_name, @PathVariable("page") int page) { return trailService.searchTrailByName(search_name, page); }

    /**
     * Returns a List of Trails that fit the State search criteria on a specified page
     * @param state The state value user is inserting
     * @param page The page the user is on
     * @return List<Trail> A List of 10 trails or less if on the last page
     */
    @CrossOrigin
    @GetMapping(value = "searchState/{page}/{state}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> searchTrailByState(@PathVariable("state") String state, @PathVariable("page") int page) { return trailService.searchTrailByState(state, page); }

}

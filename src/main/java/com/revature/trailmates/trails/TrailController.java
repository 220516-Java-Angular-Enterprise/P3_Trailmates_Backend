package com.revature.trailmates.trails;

import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @CrossOrigin
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Trail getTrailById(@PathVariable String id) { return trailService.getTrailAPI(id); }

    @CrossOrigin
    @GetMapping(value = "/getAll/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> getAllTrails(@PathVariable int page) { return trailService.getAllTrailsAPI(page); }

    @CrossOrigin
    @GetMapping(value = "search/{search_name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Trail> searchTrailByName(@PathVariable String search_name) { return trailService.searchTrailByName(search_name, 0); }


}
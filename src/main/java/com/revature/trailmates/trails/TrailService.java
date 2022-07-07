package com.revature.trailmates.trails;

import com.fasterxml.jackson.databind.JsonNode;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrailService {

    @Inject
    private final TrailAPIConnector trailAPIConnector;
    private final TrailRepository trailRepository;

    @Inject
    @Autowired
    public TrailService(TrailAPIConnector trailAPIConnector, TrailRepository trailRepository) {
        this.trailAPIConnector = trailAPIConnector;
        this.trailRepository = trailRepository;
    }

    public List<Trail> searchTrailByName(String name, int page) {
        List<Trail> allTrails = trailRepository.getAllTrails();
        List<Trail> searchedTrails = new ArrayList<>();

        for (int i = 0; i < allTrails.size(); i++) {
            try {
                if (allTrails.get(i).getName().toLowerCase().contains(name.toLowerCase())) searchedTrails.add(allTrails.get(i));
            } catch (IndexOutOfBoundsException ignore) { }
        }

        List<Trail> trails = new ArrayList<>();
        int total = searchedTrails.size() - (page * 10);
        if (total > 10) {
            for (int i = 0; i < 10; i++) {
                trails.add(searchedTrails.get(page*10 + i));
            }
        }

        else if (total > 0){
            for (int i = 0; i < total; i++) {
                trails.add(searchedTrails.get(page*10 + i));
            }
        }
        return trails;
    }

    public List<Trail> searchTrailByState(String state, int page) {
        List<Trail> allTrails = trailRepository.getAllTrails();
        List<Trail> searchedTrails = new ArrayList<>();

        for (int i = 0; i < allTrails.size(); i++) {
            try {
                if (allTrails.get(i).getStates().toLowerCase().contains(state.toLowerCase())) searchedTrails.add(allTrails.get(i));
            } catch (IndexOutOfBoundsException ignore) { }
        }

        List<Trail> trails = new ArrayList<>();
        int total = searchedTrails.size() - (page * 10);
        if (total > 10) {
            for (int i = 0; i < 10; i++) {
                trails.add(searchedTrails.get(page*10 + i));
            }
        }

        else if (total > 0){
            for (int i = 0; i < total; i++) {
                trails.add(searchedTrails.get(page*10 + i));
            }
        }
        return trails;
    }

    public List<Trail> searchTrailByParkName(String parkName, int page) {
        List<Trail> allTrails = trailRepository.getAllTrails();
        List<Trail> searchedTrails = new ArrayList<>();

        for (int i = 0; i < allTrails.size(); i++) {
            try {
                if (allTrails.get(i).getPark_name().toLowerCase().contains(parkName.toLowerCase())) searchedTrails.add(allTrails.get(i));
            } catch (IndexOutOfBoundsException ignore) { }
        }

        List<Trail> trails = new ArrayList<>();
        int total = searchedTrails.size() - (page * 10);
        if (total > 10) {
            for (int i = 0; i < 10; i++) {
                trails.add(searchedTrails.get(page*10 + i));
            }
        }

        else if (total > 0){
            for (int i = 0; i < total; i++) {
                trails.add(searchedTrails.get(page*10 + i));
            }
        }
        return trails;
    }

    public List<Trail> getAllTrailsPage(int page) {
        List<Trail> allTrails = trailRepository.getAllTrails();
        List<Trail> trails = new ArrayList<>();

        int total = allTrails.size() - (page * 10);
        if (total > 10) {
            for (int i = 0; i < 10; i++) {
                trails.add(allTrails.get(page*10 + i));
            }
        }
        else if (total > 0){
            for (int i = 0; i < total; i++) {
                trails.add(allTrails.get(page*10 + i));
            }
        }
        return trails;
    }

    public Optional<Trail> getTrail(String id) { return trailRepository.findById(id); }
    public List<Trail> getAllTrails() { return trailRepository.getAllTrails(); }

    //<editor-fold desc="Functions Connected to the NPS Trail API">


    public List<Trail> getAllTrailsAPI(int page) {
        JsonNode content = trailAPIConnector.getAllPlainJSON(page);
        List<Trail> trails = new ArrayList<>();

        int total = content.get("total").asInt() - (page * 10);
        System.out.println(total);
        if (total > 10) {
            for (int i = 0; i < 10; i++) {
                trails.add(getTrailAPI(content.at("/data").get(i).get("id").asText()));
                addTrail(trails.get(trails.size()-1));
            }
        }
        else if (total > 0){
            for (int i = 0; i < total; i++) {
                trails.add(getTrailAPI(content.at("/data").get(i).get("id").asText()));
                addTrail(trails.get(trails.size()-1));
            }
        }
        return trails;
    }

    public Trail getTrailAPI(String id) {

        JsonNode content = trailAPIConnector.getPlainJSON(id);

        Trail trail = new Trail();
        trail.setId(getId(content));
        trail.setName(getName(content));
        trail.setPark_name(getParkName(content));
        trail.setShort_desc(getShort_desc(content));
        trail.setLong_desc(getLong_desc(content));
        trail.setImage_url(getImage_url(content));
        trail.setWebsite_url(getWebsite_url(content));
        trail.setReservationRequired(getReservationRequired(content));
        trail.setArePetsPermitted(getPetsPermitted(content));
        trail.setDoFeesApply(getFeesApply(content));
        trail.setDuration(getDuration(content));
        trail.setStates(getState(content));
        trail.setParkCode(getParkCode(content));
        trail.setLatitude(getLatitude(content));
        trail.setLongitude(getLongitude(content));

        return trail;
    }

    private String getId(JsonNode content) {
        String id = content.get("id").asText();
        return id;
    }

    private String getParkName(JsonNode content) {
        String name = "";
        if (content.at("/relatedParks").get(0) != null)
            name = content.at("/relatedParks").get(0).get("fullName").asText();
        return name;
    }

    private String getShort_desc(JsonNode content) {
        String short_desc = content.get("shortDescription").asText();
        return short_desc;
    }

    private String getLong_desc(JsonNode content) {
        String long_desc = content.get("longDescription").asText();
        return long_desc;
    }

    private String getImage_url(JsonNode content) {
        String image_url = "";
        if (content.at("/images").get(0) != null)
            image_url = content.at("/images").get(0).get("url").asText();
        return image_url;
    }

    private String getWebsite_url(JsonNode content) {
        String web_url = "";
        if (content.at("/relatedParks").get(0) != null)
            web_url = content.at("/relatedParks").get(0).get("url").asText();
        return web_url;
    }

    private Boolean getReservationRequired(JsonNode content) {
        Boolean reservation = content.get("isReservationRequired").asBoolean();
        return reservation;
    }

    private Boolean getPetsPermitted(JsonNode content) {
        Boolean pets = content.get("arePetsPermitted").asBoolean();
        return pets;
    }

    private Boolean getFeesApply(JsonNode content) {
        Boolean fees = content.get("doFeesApply").asBoolean();
        return fees;
    }

    private String getDuration(JsonNode content) {
        String duration = content.get("duration").asText();
        return duration;
    }

    private String getState(JsonNode content) {
        String state = "";
        if (content.at("/relatedParks").get(0) != null)
            state = content.at("/relatedParks").get(0).get("states").asText();
        return state;
    }
    private String getParkCode(JsonNode content) {
        String code = "";
        if (content.at("/relatedParks").get(0) != null)
            code = content.at("/relatedParks").get(0).get("parkCode").asText();
        return code;
    }

    private String getName(JsonNode content) {
        String name = "";
        name = content.get("title").asText();
        return name;
    }

    private String getLatitude(JsonNode content) {
        String name = "";
        name = content.get("latitude").asText();
        return name;
    }
    private String getLongitude(JsonNode content) {
        String name = "";
        name = content.get("longitude").asText();
        return name;
    }
    //</editor-fold>

    public void addTrail(Trail trail) {
        if ( trail.getPark_name() != null)
            trailRepository.saveTrailName(trail.getId(), trail.getName(), trail.getPark_name(), trail.getShort_desc(), trail.getLong_desc(), trail.getImage_url(), trail.getWebsite_url(), trail.getReservationRequired(), trail.getArePetsPermitted(), trail.getDoFeesApply(), trail.getDuration(), trail.getStates(), trail.getParkCode(), trail.getLatitude(), trail.getLongitude());
    }


}

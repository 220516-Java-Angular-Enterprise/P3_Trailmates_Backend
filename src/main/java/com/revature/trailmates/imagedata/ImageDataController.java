package com.revature.trailmates.imagedata;

import com.amazonaws.HttpMethod;
import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.imagedata.dtos.requests.NewImageDataRequest;
import com.revature.trailmates.trailflag.TrailFlag;
import com.revature.trailmates.trailflag.dtos.requests.NewTrailFlagRequest;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/image")
public class ImageDataController {
    @Inject
    private ImageDataService imageDataService;
    @Inject
    private TokenService tokenService;
    @Autowired
    public ImageDataController(ImageDataService imageDataService, TokenService tokenService) {
        this.imageDataService = imageDataService;
        this.tokenService = tokenService;
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/gen-url/{extension}")
    public String generateUploadUrl(@RequestHeader("Authorization") String token,@PathVariable String extension) {
        Principal user = tokenService.noTokenThrow(token);
        return imageDataService.generatePreSignedUrl(UUID.randomUUID()+"."+extension, "trailmates-images", HttpMethod.PUT);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/{url}")
    public ImageData getByUrl(@RequestHeader("Authorization") String token,@PathVariable String url) {
        Principal user = tokenService.noTokenThrow(token);
        return imageDataService.getByUrl(url);
    }
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value="/{url}")
    public String deleteByUrl(@RequestHeader("Authorization") String token,@PathVariable String url) {
        Principal user = tokenService.noTokenThrow(token);
        if(imageDataService.deleteByUrl(url)){
            return "Image data deleted.";
        } else return "Unable to delete image data.";
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ImageData saveImageData(@RequestHeader("Authorization") String token, @RequestBody NewImageDataRequest request) {
        Principal user = tokenService.noTokenThrow(token);
        return imageDataService.saveNewImageData(request, user);
    }

}

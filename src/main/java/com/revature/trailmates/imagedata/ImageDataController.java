package com.revature.trailmates.imagedata;

import com.amazonaws.HttpMethod;
import com.revature.trailmates.auth.TokenService;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.imagedata.dtos.requests.NewImageDataRequest;
import com.revature.trailmates.imagedata.dtos.responses.SecureUrlResponse;
import com.revature.trailmates.util.annotations.Inject;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    /**
     * returns the imagedata for the authenticated user with the most recent timestamp of filetype 'PROFILE'
     * @param token Authorization token from header
     * @return an ImageData object with the authenticated user's most recent profile picture.'
     */
     @GetMapping(value="/profpic/{u}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageData getLatestProfPic(@RequestHeader("Authorization") String token, @PathVariable String u) {
        tokenService.noTokenThrow(token);
        return imageDataService.getLatestProfPic(u);
    }
    /**
     * Generates a secure URL for making a PUT request to the trailmates-images S3 bucket
     * @param token Authorization token from header
     * @param extension the file extension (jpeg, png, etc) under which it will be saved on S3
     * @return A string that is the URL to which the PUT request should be sent.
     */

    @GetMapping(value="/gen-url/{extension}",produces = MediaType.APPLICATION_JSON_VALUE)
    public SecureUrlResponse generateUploadUrl(@RequestHeader("Authorization") String token, @PathVariable String extension) {
        tokenService.noTokenThrow(token);
        return new SecureUrlResponse(imageDataService.generatePreSignedUrl(UUID.randomUUID()+"."+extension, "trailmates-images", HttpMethod.PUT));
    }

    /**
     * Obtains an ImageData object given its unique url on S3 (does not query S3; the url is a primary key in database)
     * @param token Authorization token from header
     * @param url A string that is the unique url at which the image resides on S3
     * @return An ImageData object,which contains its url, the user who created it, the timestamp at which it was created, and a string with filetype information
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value="/{url}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ImageData getByUrl(@RequestHeader("Authorization") String token,@PathVariable String url) {
        tokenService.noTokenThrow(token);
        return imageDataService.getByUrl(url);
    }

    /**
     * Deletes an ImageData object from the database given its unique url on S3 (does not interact with S3; the image will need to be deleted on S3 as well)
     * @param token Authorization token from header
     * @param url A string that is the unique url at which the image resides on S3
     * @return A string "Image data deleted" if deletion works, "Unable to delete image data" if it fails and no other exception was thrown.
     */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value="/{url}",produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteByUrl(@RequestHeader("Authorization") String token,@PathVariable String url) {
        Principal user = tokenService.noTokenThrow(token);
        if(imageDataService.deleteByUrl(url, user)){
            return "Image data deleted.";
        } else return "Unable to delete image data.";
    }

    /**
     * Saves image data to the database
     * @param token Authorization token from header
     * @param request a JSON request body containing two fields: url and filetype.
     *                url is a string with the unique url at which the image resides on S3.
     *                filetype is a string with file type information
     * @return
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ImageData saveImageData(@RequestHeader("Authorization") String token, @RequestBody NewImageDataRequest request) {
        Principal user = tokenService.noTokenThrow(token);
        return imageDataService.saveNewImageData(request, user);
    }

}

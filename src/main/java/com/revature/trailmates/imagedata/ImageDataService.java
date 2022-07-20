package com.revature.trailmates.imagedata;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.imagedata.dtos.requests.NewImageDataRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class ImageDataService {
    @Inject
    private final ImageDataRepository imageDataRepo;
    @Autowired
    public ImageDataService(ImageDataRepository imageDataRepo) {this.imageDataRepo= imageDataRepo;}
    public ImageData saveNewImageData(NewImageDataRequest request, Principal user){
        ImageData newImage = new ImageData(request, user, new Date());
        try {
            imageDataRepo.save(newImage);
        } catch (Exception e) {
            throw new InvalidRequestException("Couldn't save image data.");
        }
        return newImage;
    }
    public ImageData getLatestProfPic(String userID){
        //initialize the object that will hold what we get from database.
        Optional<List<ImageData>> retrievedImageData = Optional.of(new ArrayList<ImageData>());
        //get imagedata from database, throw exception if we can't.
        try{
            retrievedImageData=imageDataRepo.getLatestPPByUserId(userID);
        } catch (Exception e) {
            throw new InvalidRequestException("There was an error accessing the database to obtain a profile pic.");
        }
        //if the result is an empty list, throw exception
        if (!retrievedImageData.isPresent()){
            throw new InvalidRequestException("Unable to locate profile pic for this user.");
        }
        //return the entry we found
        return retrievedImageData.get().get(0);
    }
    public ImageData getByUrl(String url){
        Optional<ImageData> returnImageData=imageDataRepo.findById(url);
        if (!returnImageData.isPresent()){
            throw new InvalidRequestException("No image data found for that url");
        }
        return returnImageData.get();
    }
    public boolean deleteByUrl(String url, Principal user){
        Optional<ImageData> returnImageData=imageDataRepo.findById(url);
        //make sure the thing exists before trying to delete it
        if (!returnImageData.isPresent()){
            throw new InvalidRequestException("No image data found for that url");
        }
        //make sure the person deleting the image is its owner.
        if (!returnImageData.get().getUserId().getId().equals(user.getId())){
            throw new UnauthorizedException("Cannot delete image data for an image that you did not upload.");
        }
        try{
            imageDataRepo.deleteById(url);
            return true;
        } catch (Exception e) {
            throw new InvalidRequestException("Could not delete image data.");
        }
    }
}

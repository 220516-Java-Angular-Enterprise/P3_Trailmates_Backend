package com.revature.trailmates.imagedata;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.imagedata.dtos.requests.NewImageDataRequest;
import com.revature.trailmates.util.annotations.Inject;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
@Service
@Transactional
public class ImageDataService {
    @Inject
    private final AmazonS3 amazonS3;
    @Inject
    private final ImageDataRepository imageDataRepo;
    @Autowired
    public ImageDataService(AmazonS3 amazonS3, ImageDataRepository imageDataRepo) {this.amazonS3 = amazonS3; this.imageDataRepo= imageDataRepo;}

    public String generatePreSignedUrl(String filePath, String bucketName, HttpMethod httpMethod) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 10); //url valid for 10 minutes
        return amazonS3.generatePresignedUrl(bucketName, filePath, calendar.getTime(), httpMethod).toString();
    }
    //for saving to DB
    public ImageData saveNewImageData(NewImageDataRequest request, Principal user){
        ImageData newImage = new ImageData(request, user, new Date());
        //input validation
        //check if URL actually connects to anything in S3 bucket
        //save to database
        try {
            imageDataRepo.save(newImage);
        } catch (Exception e) {
            throw new InvalidRequestException("Couldn't save image data.");
        }
        return newImage;
    }
    public ImageData getByUrl(String url){
        Optional<ImageData> returnImageData=imageDataRepo.findById(url);
        if (!returnImageData.isPresent()){
            throw new InvalidRequestException("No image data found for that url");
        }
        return returnImageData.get();
    }
    public boolean deleteByUrl(String url){
        Optional<ImageData> returnImageData=imageDataRepo.findById(url);
        if (!returnImageData.isPresent()){
            throw new InvalidRequestException("No image data found for that url");
        }
        try{
            imageDataRepo.deleteById(url);
            return true;
        } catch (Exception e) {
            throw new InvalidRequestException("Could not delete image data.");
        }
    }
}

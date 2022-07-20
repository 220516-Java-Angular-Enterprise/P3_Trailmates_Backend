package com.revature.trailmates.imagedata;

import com.revature.trailmates.auth.dtos.response.Principal;
import com.revature.trailmates.imagedata.dtos.requests.NewImageDataRequest;
import com.revature.trailmates.user.User;
import com.revature.trailmates.util.custom_exception.InvalidRequestException;
import com.revature.trailmates.util.custom_exception.UnauthorizedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.MockitoAnnotations.initMocks;
@ExtendWith(MockitoExtension.class)
class ImageDataServiceTest {
    @Mock
    private ImageDataRepository repo;
    @InjectMocks
    private ImageDataService service;
    @Spy
    NewImageDataRequest newImageDataRequest = new NewImageDataRequest();
    @Spy
    ImageData dummyImageData = new ImageData();
    @Spy
    User dummyUser = new User();
    @Spy
    Principal dummyPrincipal = new Principal("dummy id","dummy username","dummy role");

    @Test
    void saveNewImageDataFails() {
        //initialize request
        initMocks(repo);
        newImageDataRequest.setUrl("foo");
        newImageDataRequest.setFiletype("bar");
        Mockito.doThrow(new RuntimeException("Shoot dang it, it didn't work.")).when(repo).save(any(ImageData.class));
        Exception e = assertThrows(InvalidRequestException.class, ()-> service.saveNewImageData(newImageDataRequest,dummyPrincipal));
        assertEquals("Couldn't save image data.", e.getMessage());
    }
    @Test
    void saveNewImageDataWorks(){
        initMocks(repo);
        newImageDataRequest.setUrl("foo");
        newImageDataRequest.setFiletype("bar");
        ImageData outData = service.saveNewImageData(newImageDataRequest,dummyPrincipal);
        assertEquals(newImageDataRequest.getUrl(),outData.getUrl());
        assertEquals(newImageDataRequest.getFiletype(),outData.getFiletype());
        assertEquals(dummyPrincipal.getId(),outData.getUserId().getId());
    }
    @Test
    void getLatestProfPicDatabaseError() {
        Mockito.doThrow(new RuntimeException("Shoot dang it, it didn't work.")).when(repo).getLatestPPByUserId(any(String.class));
        Exception e = assertThrows(InvalidRequestException.class, ()-> service.getLatestProfPic("foo"));
        assertEquals("There was an error accessing the database to obtain a profile pic.", e.getMessage());
    }
    @Test
    void getLatestProfPicNoPicFound() {
        Mockito.doReturn(Optional.empty()).when(repo).getLatestPPByUserId(any(String.class));
        Exception e = assertThrows(InvalidRequestException.class, ()-> service.getLatestProfPic("foo"));
        assertEquals("Unable to locate profile pic for this user.", e.getMessage());
    }
    @Test
    void getLatestProfPicWorks() {
        List<ImageData> dummyList = new ArrayList<ImageData>();
        dummyList.add(dummyImageData);
        Mockito.doReturn(Optional.of(dummyList)).when(repo).getLatestPPByUserId("foo");
        assertEquals(dummyImageData,service.getLatestProfPic("foo"));
    }
    @Test
    void getByUrlNotFound() {
        Mockito.doReturn(Optional.empty()).when(repo).findById("foo");
        Exception e = assertThrows(InvalidRequestException.class, ()-> service.getByUrl("foo"));
        assertEquals("No image data found for that url", e.getMessage());
    }
    @Test
    void getByUrlWorks() {
        Mockito.doReturn(Optional.of(dummyImageData)).when(repo).findById("foo");
        assertEquals(dummyImageData,service.getByUrl("foo"));
    }

    @Test
    void deleteByUrlNotFound() {
        Mockito.doReturn(Optional.empty()).when(repo).findById("foo");
        Exception e = assertThrows(InvalidRequestException.class, ()-> service.deleteByUrl("foo", dummyPrincipal));
        assertEquals("No image data found for that url", e.getMessage());

    }
    @Test
    void deleteByUrlNotYourPicture() {
        dummyUser.setId("foo");
        dummyImageData.setUserId(dummyUser);
        Mockito.doReturn(Optional.of(dummyImageData)).when(repo).findById("foo");
        dummyPrincipal.setId("bar");
        Exception e = assertThrows(UnauthorizedException.class, ()-> service.deleteByUrl("foo", dummyPrincipal));
        assertEquals("Cannot delete image data for an image that you did not upload.", e.getMessage());
    }
    @Test
    void deleteByUrlDatabaseError() {
        dummyUser.setId("foo");
        dummyPrincipal.setId("foo");
        dummyImageData.setUserId(dummyUser);
        Mockito.doReturn(Optional.of(dummyImageData)).when(repo).findById("foo");
        Mockito.doThrow(new RuntimeException("Shoot dang it, it didn't work.")).when(repo).deleteById(any(String.class));
        Exception e = assertThrows(InvalidRequestException.class, ()-> service.deleteByUrl("foo", dummyPrincipal));
        assertEquals("Could not delete image data.", e.getMessage());
    }
    @Test
    void deleteByUrlWorks() {
        dummyUser.setId("foo");
        dummyPrincipal.setId("foo");
        dummyImageData.setUserId(dummyUser);
        Mockito.doReturn(Optional.of(dummyImageData)).when(repo).findById("foo");
        assertTrue(service.deleteByUrl("foo",dummyPrincipal));
    }


}
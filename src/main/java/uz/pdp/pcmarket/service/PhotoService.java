package uz.pdp.pcmarket.service;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.pcmarket.payload.AddResult;

import java.io.IOException;

public interface PhotoService {
    AddResult add(MultipartFile request, MultipartHttpServletRequest  multipartHttpServletRequest) throws IllegalStateException, IOException;

    AddResult edit(MultipartFile request, MultipartHttpServletRequest  multipartHttpServletRequest,Integer photo_id) throws IllegalStateException, IOException;
}

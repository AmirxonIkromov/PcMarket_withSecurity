package uz.pdp.pcmarket.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.pcmarket.entity.Photo;
import uz.pdp.pcmarket.entity.Photo_content;
import uz.pdp.pcmarket.payload.AddResult;
import uz.pdp.pcmarket.repository.PhotoRepository;
import uz.pdp.pcmarket.repository.Photo_ContentRepository;

import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final Photo_ContentRepository photo_contentRepository;


    @Override
    public AddResult add(MultipartFile request, MultipartHttpServletRequest multipartHttpServletRequest) throws IllegalStateException, IOException {

        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
        MultipartFile file = multipartHttpServletRequest.getFile(fileNames.next());


        if (file != null) {

            Photo photo = new Photo();
            photo.setName(request.getOriginalFilename());
            photo.setSize(request.getSize());
            photo.setType(request.getContentType());

            Photo save = photoRepository.save(photo);

            Photo_content photo_content = new Photo_content();
            photo_content.setPhoto(save);
            photo_content.setBytes(file.getBytes());

            photo_contentRepository.save(photo_content);

            return new AddResult(true, save);
        }
        return new AddResult(false, null);
    }

    @Override
    public AddResult edit(MultipartFile request, MultipartHttpServletRequest multipartHttpServletRequest, Integer photo_id) throws IllegalStateException, IOException {

        Optional<Photo> byId = photoRepository.findById(photo_id);


        if (byId.isPresent()) {
            Photo photo = byId.get();
            photo.setName(request.getOriginalFilename());
            photo.setSize(request.getSize());
            photo.setType(request.getContentType());

            Photo save = photoRepository.save(photo);

            Optional<Photo_content> byPhotoId = photo_contentRepository.findByPhotoId(photo_id);
            if (byPhotoId.isPresent()) {
                Photo_content photo_content = byPhotoId.get();
                photo_content.setPhoto(save);
                photo_content.setBytes(request.getBytes());

                photo_contentRepository.save(photo_content);

                return new AddResult(true, save);
            }
        }

        return new AddResult(false, null);

    }

}

package com.example.blogapplication.Services.Implementation;

import com.example.blogapplication.Services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String UploadImage(String path, MultipartFile file) throws IOException {

        // getting the file name
        String name = file.getOriginalFilename();

        // random name generation
        String randomId= UUID.randomUUID().toString();
        String fileName1=randomId.concat(name.substring(name.lastIndexOf(".")));


        //fullPath
        String filePath=path + File.separator + fileName1;

        // create folder if not created

        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }


        // copy the file
        Files.copy(file.getInputStream(), Paths.get(filePath));




return name;

    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
      String fullPath=path + File.separator+fileName;
      InputStream is=new FileInputStream(fullPath);



        return is;
    }
}

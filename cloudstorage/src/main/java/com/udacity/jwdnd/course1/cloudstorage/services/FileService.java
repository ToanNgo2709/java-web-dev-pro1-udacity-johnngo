package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.ResponseFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    public ResponseFile getResponseFile(File file) {
        String base64 = Base64.getEncoder().encodeToString(file.getFiledata());
        String dataURL = "data:" + file.getContenttype() + ";base64," + base64;
        return new ResponseFile(file.getFileId(), file.getFilename(), dataURL);
    }

    public List<ResponseFile> getFileByUserId(int userid) throws Exception {
        List<File> files = fileMapper.findByUserId(userid);
        if (files == null) {
            return new ArrayList<>();
        }
        return files.stream().map(this::getResponseFile).collect(Collectors.toList());
    }

    public void addFile(MultipartFile fileUpload, int userid) throws IOException {
        File file = new File();
        try {
            file.setContenttype(fileUpload.getContentType());
            file.setFiledata(fileUpload.getBytes());
            file.setFilename(fileUpload.getOriginalFilename());
            file.setFilesize(Long.toString(fileUpload.getSize()));
        } catch (IOException e) {
            throw e;
        }
        fileMapper.insert(file, userid);
    }

    public void deleteFile(int fileid) {
        fileMapper.delete(fileid);
    }
}

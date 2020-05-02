package com.scau2018computer.photoviewserver.controller;

import com.scau2018computer.photoviewserver.entity.ImageNode;
import com.scau2018computer.photoviewserver.service.ImageNodeService;
import com.scau2018computer.photoviewserver.utils.BASE64Encoder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Getter
@Setter
@RestController
@RequestMapping("/myImages")
public class ImagesController {

    @Resource
    private ImageNodeService imageNodeService;

    @GetMapping(value = "/getImagesDivideIntoPages")
    public List<ImageNode> imagesDivideIntoPages(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "1") int size) {
        return imageNodeService.findByPage(page, size).getContent();
    }

    @DeleteMapping(value = "/deleteImages")
    public String deleteImages(@RequestParam("ids") String ids) {
        String[] idList = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for (String idPoint : idList) {
            list.add(Integer.getInteger(idPoint));
        }
        imageNodeService.deleteBatch(list);
        return null;
    }

    @PostMapping(value = "/addImages")
    public String addImages(@RequestParam("fileBody") MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) {
            return "null";
        } else {
            File toFile = multipartFileToFile(multipartFile);
            if(toFile == null) return "null";
            byte[] buffer = new byte[(int) toFile.length()];
            FileInputStream fis = new FileInputStream(toFile);
            fis.read(buffer);
            fis.close();
            toFile.delete();
            String base64EncodedImages = new BASE64Encoder().encode(buffer);
            imageNodeService.add(base64EncodedImages);
            return "OK";
        }
    }
    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        String fileRealName = multipartFile.getOriginalFilename();//获得原始文件名;
        if (fileRealName == null) return null;
        int pointIndex =  fileRealName.lastIndexOf(".");//点号的位置
        String fileSuffix = fileRealName.substring(pointIndex);//截取文件后缀
        String fileNewName = "temp".concat(fileSuffix);
        File file = new File( "D:\\" + fileNewName);
        if(file.exists()){
            file.delete();
        }
        multipartFile.transferTo(file);
        return file;
    }
}

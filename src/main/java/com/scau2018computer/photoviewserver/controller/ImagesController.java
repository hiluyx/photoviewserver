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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
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
    public String addImages(@RequestParam("files") MultipartFile[] multipartFiles) throws IOException {
        if (multipartFiles.length > 0) {
            for (MultipartFile mpf : multipartFiles) {
                File toFile = multipartFileToFile(mpf);
                imageNodeService.add(new ImageNode(toFile.getName(), toBASE64Encoded(toFile)));
            }
            return "OK";
        }else{
            return "NULL";
        }
    }
    public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        String fileRealName = multipartFile.getOriginalFilename();//获得原始文件名;
        System.out.println(fileRealName);
        InputStream ins = multipartFile.getInputStream();
        assert fileRealName != null;
        File file = new File(fileRealName);
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    public static String toBASE64Encoded(File file) throws IOException {
        byte[] buffer = new byte[(int) file.length()];
        FileInputStream fis;
        fis = new FileInputStream(file);
        fis.read(buffer);
        fis.close();
        File del = new File(file.toURI());
        del.delete();
        return new BASE64Encoder().encode(buffer);
    }
}

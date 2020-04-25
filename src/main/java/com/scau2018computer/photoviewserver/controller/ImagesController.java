package com.scau2018computer.photoviewserver.controller;

import com.scau2018computer.photoviewserver.entity.ImageNode;
import com.scau2018computer.photoviewserver.service.ImageNodeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/myImages")
public class ImagesController {

    @Resource
    private ImageNodeService imageNodeService;

    @GetMapping(value = "/getImagesDivideIntoPages")
    public List<ImageNode> imagesDivideIntoPages(
            @RequestParam("page") int page,
            @RequestParam("size") int size ){
        return imageNodeService.findByPage(page,size).getContent();
    }

    @DeleteMapping(value = "/deleteImages")
    public String deleteImages(@RequestParam("ids") String ids){
        String[] idList = ids.split(",");
        List<Integer> list = new ArrayList<>();
        for(String idPoint : idList){
            list.add(Integer.getInteger(idPoint));
        }
        imageNodeService.deleteBatch(list);
        return null;
    }

    @PostMapping(value = "/addImages")
    public String addImages(@RequestParam("images") String[] images){
        List<ImageNode> imageNodeList;
        imageNodeList = new ArrayList<>();
        for(String s : images){
            imageNodeList.add(new ImageNode(s));
        }
        imageNodeService.addBatch(imageNodeList);
        return null;
    }
}

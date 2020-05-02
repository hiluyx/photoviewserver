package com.scau2018computer.photoviewserver.service;

import com.scau2018computer.photoviewserver.entity.ImageNode;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImageNodeService {
    Page<ImageNode> findByPage(int page, int size);
    void add(String base64EncodedImage);
    void deleteBatch(List<Integer> idList);
}

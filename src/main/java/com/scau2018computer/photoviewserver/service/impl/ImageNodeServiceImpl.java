package com.scau2018computer.photoviewserver.service.impl;

import com.scau2018computer.photoviewserver.DAO.ImageNodeDAO;
import com.scau2018computer.photoviewserver.entity.ImageNode;
import com.scau2018computer.photoviewserver.service.ImageNodeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageNodeServiceImpl  implements ImageNodeService {

    @Getter
    @Setter
    @Resource
    private ImageNodeDAO imageNodeDAO;

    @Override
    public Page<ImageNode> findByPage(int page, int size) {
        Pageable pageRequest = PageRequest.of(page,size,Sort.Direction.DESC,"id");
        return imageNodeDAO.findAll(pageRequest);
    }

    @Override
    public void add(ImageNode imageNode) {
        imageNodeDAO.save(imageNode);
    }

    @Override
    public void deleteBatch(List<Integer> idList) {
        imageNodeDAO.deleteImageNodes(idList);
    }
}

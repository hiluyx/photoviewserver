package com.scau2018computer.photoviewserver.DAO;

import com.scau2018computer.photoviewserver.entity.ImageNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ImageNodeDAO extends JpaRepository<ImageNode,Integer> {

    @Modifying
    @Transactional
    @Query("delete from imageNodes where id in (:ids)")
    void deleteImageNodes(@Param("ids") List<Integer> ids);
}

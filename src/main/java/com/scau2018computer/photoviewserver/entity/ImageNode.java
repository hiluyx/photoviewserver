package com.scau2018computer.photoviewserver.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "images")
@Data
@Entity(name = "imageNodes")
public class ImageNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base64EncodedImage")
    private String base64EncodedImage;

    public ImageNode(String base64EncodedImage){
        this.setBase64EncodedImage(base64EncodedImage);
    }

    @Override
    public String toString(){
        return this.getId() + ":" + this.getBase64EncodedImage();
    }
}

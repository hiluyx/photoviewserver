package com.scau2018computer.photoviewserver.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "images")
@Data
@Entity(name = "image_node")
public class ImageNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image_string")
    private String imageString;

    public ImageNode(String base64EncodedImage){
        this.setImageString(base64EncodedImage);
    }

    public ImageNode(){

    }
    @Override
    public String toString(){
        return this.getId() + ":" + this.getImageString();
    }
}

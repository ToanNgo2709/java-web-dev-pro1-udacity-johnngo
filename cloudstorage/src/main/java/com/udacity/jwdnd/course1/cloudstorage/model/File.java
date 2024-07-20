package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class File {

    /**
     * File Id
     */
    private int fileId;

    /**
     * File Name
     */
    private String filename;

    /**
     * Content Type
     */
    private String contenttype;

    /**
     * File Size
     */
    private String filesize;

    /**
     * File Data
     */
    private byte[] filedata;
}

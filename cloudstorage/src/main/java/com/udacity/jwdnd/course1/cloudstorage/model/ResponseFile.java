package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFile {

    /**
     * Id
     */
    private int id;

    /**
     * Name
     */
    private String name;

    /**
     * Data URL
     */
    private String dataUrl;
}

package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    /**
     * Note Id
     */
    private int noteid;

    /**
     * Note Title
     */
    private String notetitle;

    /**
     * Note Description
     */
    private String notedescription;
}

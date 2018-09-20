/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldigitec.validator.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author rescudero
 */
public class ArchivoUtil {

    public static String subirArchivo(String extencion, byte[] data) throws Exception {
        String filename = (new Date().getTime()) + "";
        String destinationDir = System.getProperty("java.io.tmpdir");
        String newFileName = destinationDir + System.getProperty("file.separator") + filename + "." + extencion;
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(new File(newFileName));
            fileOutputStream.write(data, 0, data.length);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new Exception("Error in writing captured image.", e);
        }
        return newFileName;
    }

    public static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }
}

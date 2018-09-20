/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worldigitec.validator.web;

import com.iecisa.validator.Validator;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Usuario
 */
@ManagedBean
@ViewScoped
public class ValidatorMB implements Serializable {

    /**
     * Creates a new instance of ValidatorMB
     */
    private UploadedFile file;
    private String validacion;

    public ValidatorMB() {
    }

    public String getValidacion() {
        return validacion;
    }

    public void setValidacion(String validacion) {
        this.validacion = validacion;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void guardar() {
        try {
            if (file != null) {
                String nombre_archivo = ArchivoUtil.subirArchivo(
                        ArchivoUtil.getExtension(file.getFileName()),
                        file.getContents());
                Validator val = new Validator();
                List consulta = new ArrayList();
                consulta.add("PMP");
                consulta.add("bogota");
                consulta.add("RICARDO");
                consulta.add("Artesania-SIART");
                System.out.println("entrooo==");
                int respuesta = val.validacion(nombre_archivo, consulta);
                System.out.println(respuesta);
                this.validacion = "El porcentaje de validación es =" + respuesta + "%";
            } else {
                this.validacion = "El porcentaje de validación es = 0 %";
            }


        } catch (Exception ex) {
            addError("Se presento un error ==> " + ex.getMessage());
        }
    }

    private void addMessage(String msg, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(msg);
        message.setSeverity(severity);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ctx.addMessage(null, message);
    }

    public void addError(String msg) {
        addMessage(msg, FacesMessage.SEVERITY_ERROR);
    }
}

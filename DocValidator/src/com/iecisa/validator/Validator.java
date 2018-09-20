/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iecisa.validator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 *
 * @author Usuario
 */
public class Validator {


    /**
    public static void main(String[] args) {
        try {
            Tesseract instance = Tesseract.getInstance();
            File imageFile = new File("C:\\Rafa\\Proyectos\\ImageValidator\\Fuentes\\tess4j-code-r200-Tess4J_3-trunk\\eurotext.bmp");
            String result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (TesseractException ex) {
            Logger.getLogger(TesseractExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    **/

    /**
     * Metodo que realiza la validacion del documento, donde se recibe el path del archivo a validar
     * y el listado de frases que son usadas para comprobar si el documento contiene estas frases y asi
     * validar el documento.
     * La validacion la realiza convirtiendo el archivo a imagen en blanco y negro
     * luego extrae mediante OCR el texto y realiza la busqueda de las fraces en el texto extraido
     * @param path
     * @param frases
     * @return Porcentaje de la busqueda
     * @throws Exception 
     */
    public int validacion(String path, List<String> frases) throws Exception {
        String info = new File("").getAbsolutePath() + System.getProperty("file.separator");
        try {
            String destinationDir = System.getProperty("java.io.tmpdir");
            info += " [" + destinationDir + "]";
            info += " [file==>" + path + "]";
            File origen = new File(path);

            String ext = FilenameUtils.getExtension(origen.getName());

            StringBuilder result = new StringBuilder();
            Tesseract instance = Tesseract.getInstance();

            info += " [1]";
            Date now = new Date();
            if (!ext.equals("pdf")) {
                info += " [2]";
                BufferedImage f = ImageIO.read(origen);
                BufferedImage bn = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
                //se traspasan los colores Pixel a Pixel
                for (int i = 0; i < f.getWidth(); i++) {
                    for (int j = 0; j < f.getHeight(); j++) {
                        bn.setRGB(i, j, f.getRGB(i, j));
                    }
                }
                info += " [3]";
                StringBuffer temp = new StringBuffer(destinationDir).append(System.getProperty("file.separator")).append(now.getTime()).append(".png");
                File outputfile = new File(temp.toString());
                info += " [4]";
                ImageIO.write(bn, "png", outputfile);
                result.append(instance.doOCR(outputfile));
                info += " [5]";
            } else {
                if (origen.exists()) {
                    PDDocument document = PDDocument.load(path);
                    @SuppressWarnings("unchecked")
                    List<PDPage> list = document.getDocumentCatalog().getAllPages();
                    int pageNumber = 1;
                    for (PDPage page : list) {
                        BufferedImage f = page.convertToImage();
                        BufferedImage bn = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
                        //se traspasan los colores Pixel a Pixel
                        for (int i = 0; i < f.getWidth(); i++) {
                            for (int j = 0; j < f.getHeight(); j++) {
                                bn.setRGB(i, j, f.getRGB(i, j));
                            }
                        }
                        File outputfile = new File(destinationDir + now.getTime() + "_" + pageNumber + ".png");
                        ImageIO.write(bn, "png", outputfile);
                        result.append(instance.doOCR(outputfile));
                        pageNumber++;
                    }
                    document.close();
                } else {
                    throw new Exception(origen.getName() + " File does not exist");
                }
            }
            if (result.toString().trim().equals("")) {
                return 0;
            } else if (frases == null || frases.size() < 1) {
                return 100;
            } else {
                int respuesta = 0;
                for (String o : frases) {
                    respuesta = respuesta + porcentajeBusqueda(result.toString(), o);
                }
                if (respuesta == 0) {
                    return 10;
                } else {
                    return respuesta / frases.size();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getCause());
            throw new Exception("(" + info + ") No se puede validar el archivo [" + e + "]");
        }
    }

    /**
     * Metodo que realiza una busqueda dentro de un texto, donde realiza la 
     * transformacion de los caracteres como tildes y acentos a caracteres del alfabeto.
     * @param texto
     * @param busqueda
     * @return Porcentaje de la busqueda
     */
    public int porcentajeBusqueda(String texto, String busqueda) {
        texto = ElimCaracteres.CHANGE_CARACTERES_INVALIDOS(texto);
        busqueda = ElimCaracteres.CHANGE_CARACTERES_INVALIDOS(busqueda);
        if (texto.toUpperCase().indexOf(busqueda.toUpperCase()) > 1) {
            return 100;
        }
        List<String> items = Arrays.asList(busqueda.split(" "));
        int i = 0;
        for (String o : items) {
            if (texto.toUpperCase().indexOf(o.toUpperCase()) > 1) {
                i++;
            }
        }
        return (i * 90) / items.size();
    }

    /**
     * Metodo que transforma la imagen a B/N para resaltar el texto
     * @param f
     * @return 
     */
    public BufferedImage set_Blanco_y_Negro(BufferedImage f) {
        BufferedImage bn = new BufferedImage(f.getWidth(), f.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        //se traspasan los colores Pixel a Pixel
        for (int i = 0; i < f.getWidth(); i++) {
            for (int j = 0; j < f.getHeight(); j++) {
                bn.setRGB(i, j, f.getRGB(i, j));
            }
        }
        return bn;
    }
}

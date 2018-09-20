/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iecisa.validator;

import com.test.TesseractExample;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
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

    public int validacion(String path, List<String> palabra) throws Exception {
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
            } else if (palabra == null || palabra.size() < 1) {
                return 100;
            } else {
                int respuesta = 0;
                for (String o : palabra) {
                    respuesta = respuesta + porcentajeBusqueda(result.toString(), o);
                }
                if (respuesta == 0) {
                    return 10;
                } else {
                    return respuesta / palabra.size();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getCause());
            throw new Exception("(" + info + ") No se puede validar el archivo [" + e + "]");
        }
    }

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
        return (i * 100) / items.size();
    }

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

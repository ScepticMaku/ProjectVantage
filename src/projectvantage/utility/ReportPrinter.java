/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectvantage.utility;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.*;

/**
 *
 * @author Markj
 */
public class ReportPrinter implements Printable {

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        
        Image image = Toolkit.getDefaultToolkit().getImage("/src/projectvantage/resources/img/ProjectLogo.png");
        g.drawImage(image, 50, 50, null);
        
        g.drawString("Test", 100, 100);
        
        return PAGE_EXISTS;
    }
    
}

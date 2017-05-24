package ml.hro.moneymakers.client;

import java.awt.*;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.*;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.lang.Math.*;


public class PrinterDymo400 {
    
    public String klantnaam, rekeningnummer;
    public int bedrag;

    public static void print(String _klantnaam, String _rekeningnummer, int _bedrag) {
        
    PrinterJob pj = PrinterJob.getPrinterJob();
        if (pj.printDialog()) {
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();    
            double width = fromCMToPPI(5.2);
            double height = fromCMToPPI(10.0);    
            paper.setSize(width, height);
            paper.setImageableArea(
                            fromCMToPPI(0.25), 
                            fromCMToPPI(0.5), 
                            width - fromCMToPPI(0.35), 
                            height - fromCMToPPI(1));       
            pf.setOrientation(PageFormat.PORTRAIT);
            pf.setPaper(paper);                
            dump(pf);    
            PageFormat validatePage = pj.validatePage(pf);
            MyPrintable MyPrintable = new MyPrintable();
            MyPrintable.setData(_klantnaam, _rekeningnummer, _bedrag);
            pj.setPrintable(MyPrintable, pf);
            try {
                pj.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }    
        }    
    }

    protected static double fromCMToPPI(double cm) {            
        return toPPI(cm * 0.393700787);            
    }

    protected static double toPPI(double inch) {            
        return inch * 72d;            
    }

    protected static String dump(Paper paper) {            
        StringBuilder sb = new StringBuilder(64);
        sb.append(paper.getWidth()).append("x").append(paper.getHeight())
           .append("/").append(paper.getImageableX()).append("x").
           append(paper.getImageableY()).append(" - ").append(paper
       .getImageableWidth()).append("x").append(paper.getImageableHeight());            
        return sb.toString();            
    }

    protected static String dump(PageFormat pf) {    
        Paper paper = pf.getPaper();            
        return dump(paper);    
    }

    public static class MyPrintable implements Printable {
            
            public String klantnaam;
            public String rekeningnummer;
            public int bedrag;
        
        public void setData(String _klantnaam, String _rekeningnummer, int _bedrag) {
            this.klantnaam = _klantnaam;
            this.rekeningnummer = _rekeningnummer;
            this.bedrag = _bedrag;
        }

        @Override
        public int print(Graphics graphics, PageFormat pageFormat, 
            int pageIndex) throws PrinterException {   
            int result = NO_SUCH_PAGE;        
            Graphics2D g2d = (Graphics2D) graphics;                       
            double width = pageFormat.getImageableWidth();
            double height = pageFormat.getImageableHeight();    
            g2d.translate((int) pageFormat.getImageableX(), 
                (int) pageFormat.getImageableY());                    
            //g2d.draw(new Rectangle2D.Double(1, 1, width - 1, height - 1));                    
            FontMetrics fm = g2d.getFontMetrics();

            int regelhoogte = 15;
            int inspringen = 5;

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat date = new SimpleDateFormat("dd/MM/YY");

            g2d.drawString("The Money Generators", inspringen, fm.getAscent()+ 0);
            g2d.drawString("" + klantnaam, inspringen, fm.getAscent()+ 2 * regelhoogte);
            g2d.drawString("" + rekeningnummer, inspringen, fm.getAscent()+ 3 * regelhoogte);
            g2d.drawString("Bedrag:", inspringen, fm.getAscent()+ 6 * regelhoogte);
            g2d.drawString("Automaat = Client-001", inspringen, fm.getAscent()+ 13 * regelhoogte);
            g2d.drawString("Ticket nr. : " + (int)(10000 + Math.random()*1000), inspringen, fm.getAscent()+ 14 * regelhoogte);
            g2d.drawString("Tijd : " + time.format(cal.getTime()), inspringen, fm.getAscent()+ 15 * regelhoogte);
            g2d.drawString("Datum : " + date.format(cal.getTime()), inspringen, fm.getAscent()+ 16 * regelhoogte);
            g2d.setFont(new Font(g2d.getFont().getFontName(), g2d.getFont().getStyle(), 25));
            g2d.drawString("€ "+ bedrag + ",-", inspringen, fm.getAscent()+ 7 * regelhoogte + 10);
            result = PAGE_EXISTS;  
            return result;    
        }
    }
}
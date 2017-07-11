package Geek.inc.client;
 // gecreerd door middel van window builder/ JFrame builder 
//Import packages
import javax.swing.*;
import java.awt.*;


public class Gui_Geek extends JFrame{
	//Declare variables
	protected JFrame frame, main_frame;
    protected JPanel PAGE_START, LINE_START, CENTER, LINE_END, PAGE_END;
	protected JButton agreed, bs_1, bs_2, end, Button_a, Button_b, Button_c, Button_d;
	protected JLabel Label_title, Label_center1, Label_center2, Label_a, Label_b, Label_c, Label_d;
    protected String text_title, text_main1, text_main2, text_a, text_b, text_c, text_d;
    protected boolean bool_a, bool_b, bool_c, bool_d;
    
	//static JTextField;

	public void start(String title_input){
        
        text_title = title_input;
        text_main1 = "";
        text_main2 = "";
        text_a =  "";
        text_b =  "";
        text_c =  "";
        text_d = "";
        bool_a = bool_b = bool_c = bool_d = true;
        
        //BorderLayout -- https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
        /**********Create JFrame**********/
        this.setTitle("Geek Incorporated bank");
        this.setSize (800,800);
        //this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        /**********Fill BorderLayout with JPanels**********/
        PAGE_START = new JPanel(); 
        PAGE_START.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        this.add(PAGE_START, BorderLayout.PAGE_START);
        
        LINE_START = new JPanel(); 
        LINE_START.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        this.add(LINE_START, BorderLayout.LINE_START);
        
        CENTER = new JPanel(); 
        CENTER.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        CENTER.setLayout(new GridLayout(2,0,20,20));
        this.add(CENTER, BorderLayout.CENTER);
        
        LINE_END = new JPanel(); 
        LINE_END.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));
        LINE_END.setLayout(new GridLayout(4,2,10,25));
        this.add(LINE_END, BorderLayout.LINE_END);
        
        PAGE_END = new JPanel(); 
        PAGE_END.setBorder(BorderFactory.createEmptyBorder(20,50,20,50));
        PAGE_END.setLayout(new GridLayout(0,4,20,20));
        this.add(PAGE_END, BorderLayout.PAGE_END);
        
        
        /***********Creating Fonts***********/
        Font font_text = new Font("Serif", Font.PLAIN, 75);
        Font font_description = new Font("Serif", Font.PLAIN, 50);
        Font font_title = new Font("Serif", Font.BOLD, 100);
        
        
        /***********Creating JLabels and JButtons***********/
        Label_title = new JLabel(text_title, SwingConstants.CENTER);
        Label_title.setFont(font_title);
        PAGE_START.add(Label_title);
        
        Label_center1 = new JLabel(text_main1, SwingConstants.CENTER);
        Label_center1.setFont(font_text);
        CENTER.add(Label_center1);
        
        Label_center2 = new JLabel(text_main2, SwingConstants.CENTER);
        Label_center2.setFont(font_description);
        CENTER.add(Label_center2);
        
        
        /****************BOTTUM BUTTONS****************/
        agreed = new JButton("Accepteer");
        agreed.setFont(font_text);
        agreed.setBackground(Color.GREEN);
        agreed.setOpaque(true);
        agreed.setPreferredSize(new Dimension(80, 80));
        PAGE_END.add(agreed);
        
        bs_1 = new JButton("Al.Ni.Tho");
        bs_1.setVisible(false);
        PAGE_END.add(bs_1);
        
        bs_2 = new JButton("ex.ek.mas");
        bs_2.setVisible(false);
        PAGE_END.add(bs_2);
        
        end = new JButton("Beeindig");
        end.setFont(font_text);
        end.setBackground(Color.RED);
        end.setOpaque(true);
        end.setPreferredSize(new Dimension(80, 80));
        PAGE_END.add(end);
        
        
        /*************SIDE BUTTONS & SIDE LABELS********************/
        //LABEL A
        Label_a = new JLabel(text_a, SwingConstants.CENTER);
        Label_a.setFont(font_description);
        Label_a.setVisible(bool_a);
        LINE_END.add(Label_a);
        
        //BUTTON A
        Button_a = new JButton("A");
        Button_a.setFont(font_text);
        Button_a.setVisible(bool_a);
        LINE_END.add(Button_a);
        
        //LABEL B
        Label_b = new JLabel(text_b, SwingConstants.CENTER);
        Label_b.setFont(font_description);
        Label_b.setVisible(bool_b);
        LINE_END.add(Label_b);
        
        //BUTTON B
        Button_b = new JButton("B");
        Button_b.setFont(font_text);
        Button_b.setVisible(bool_b);
        LINE_END.add(Button_b);
        
        //LABEL C
        Label_c = new JLabel(text_c, SwingConstants.CENTER);
        Label_c.setFont(font_description);
        Label_c.setVisible(bool_c);
        LINE_END.add(Label_c);
        
        //BUTTON C
        Button_c = new JButton("C");
        Button_c.setFont(font_text);
        Button_c.setVisible(bool_c);
        LINE_END.add(Button_c);
        
        //LABEL D
        Label_d = new JLabel(text_d, SwingConstants.CENTER);
        Label_d.setFont(font_description);
        Label_d.setVisible(bool_d);
        LINE_END.add(Label_d);
        
        //BUTTON D
        Button_d = new JButton("D");
        Button_d.setFont(font_text);
        Button_d.setVisible(bool_d);
        LINE_END.add(Button_d);


        this.setVisible(true);
        
	}
    
    
    public void update(
                    String text_main1, String text_main2,
                    boolean bool_a, String text_a,
                    boolean bool_b, String text_b,
                    boolean bool_c, String text_c,
                    boolean bool_d, String text_d){
        
        Label_center1.setText(text_main1);
        Label_center2.setText(text_main2);
        
        Label_a.setText(text_a);
        Label_a.setVisible(bool_a);
        Button_a.setVisible(bool_a);
        
        Label_b.setText(text_b);
        Label_b.setVisible(bool_b);
        Button_b.setVisible(bool_b);
        
        Label_c.setText(text_c);
        Label_c.setVisible(bool_c);
        Button_c.setVisible(bool_c);
        
        Label_d.setText(text_d);
        Label_d.setVisible(bool_d);
        Button_d.setVisible(bool_d);
        agreed.setText("Accepteer");
        agreed.setVisible(true);
        end.setVisible(true);
        
        this.repaint();
    }
    
    public void accepteerOff(boolean a){
    	if(a == true){
    		agreed.setText("Accepteer");
    		agreed.setVisible(true);	
    	}
    	else{
    		agreed.setVisible(false);
    	}
    }
    
    public void snelpinnenOn(boolean a){
    	if(a == true){
    		agreed.setText("Snelpinnen");
    		agreed.setVisible(true);	
    	}
    	else{
    		agreed.setVisible(false);
    	}
    }
    public void endOff(boolean a){
        end.setVisible(a);
    }
    
    public void end(){
        this.dispose();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubeskuy;


import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChatClient_server extends JFrame implements Runnable{
    static TextArea area = new TextArea(10,50); //menggunakan kelas textarea dengan ukuran max baris 10 col 50
    private TextField field = new TextField(50); //membuat text diketik dengan ukuran col 50
    private Button b1 = new Button("Send"); //membuat button send dan 
    private Button b2 = new Button("Quit"); //membuat button quit
    private Choice cho = new Choice(); //untuk membuat pilihan user
    private MenuBar mnbr = new MenuBar(); //membuat menubar 
    
    static ServerSocket ssoc; //membuat port di server biar bisa dimasukin socket dari client
    static Socket soc; //menerima serverSocket 
    static DataInputStream din; //menerima chat
    static DataOutputStream dout;//mengirim data chatnya
    
    public ChatClient_server(){
        super("Chat Server");
	b1.setSize(20,1);//mengeset ukuran btn
        b1.setBackground(new Color(63, 255, 128)); //mewarnai btn
	b2.setSize(20,1); 
        b2.setBackground(new Color(250, 99, 71));
	Menu file =  new Menu("File"); //menginisiasi variabel obyek untuk menu file
	Menu help =  new Menu("Help");
	MenuItem quit =  new MenuItem("Quit");//menginisiasi variabel obyek untuk menu file
        //membuat menu item untuk quit
	quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
		System.exit(0);		
            }
        });
        //menambahkan file agar menu item quit dapat ditambhkan
        file.add(quit);
        //menambahkan file dan quit ke dalam menu bar
	mnbr.add(file);
	mnbr.add(help);
        // untuk mengeset ke dalam menu bar
	this.setMenuBar(mnbr);
        
    }
    
    public void launchFrame () {
        //untuk mengeset ukuran gui agar ke samping kiri kanan
	this.setLayout(new FlowLayout(FlowLayout.LEADING));
        //untuk menginisialisasi panel ke dalam gui
	JPanel panel = new JPanel();
        //mengeset warna
        area.setBackground(new Color(224, 255, 255));
        //mengeset ukuran layout panel
        panel.setBackground(new Color(70,130,180));
	panel.setLayout(new GridLayout(5,1));
        getContentPane().setBackground(new Color(70,130,180));
        //menmanggil actionlistener untuk mengirimkan pesan
	b1.addActionListener(new sendChat());
        //memanggil actionlistener untuk keluar dr chat
	b2.addActionListener(new ActionListener(){
			
        public void actionPerformed(ActionEvent e) {
            System.exit(0);	
        }
    });
        //memberikan pilihan user
        cho.add("Alya ");
        cho.add("Soya ");
        cho.add("Rabby ");
        cho.add("Anony ");
        //menambahkan ke dalam panel
        panel.add(b1);
        panel.add(b2);
        panel.add(cho);
        //mengeset ukuran
        this.setSize(470,300);
        //menambahkan dalam panel
        this.add(area);
        this.add(panel);
        this.add(field);
        //mengeset agar muncul dan tidak bisa diresize
        this.setVisible(true);
        this.setResizable(false);
    }
//implements abstract runnable agar bisa saling terhubung
    @Override
    public void run(){}
    //membuat class sendChat
        class sendChat implements ActionListener{
            //membuat agar class dapat mengirimkan pesan
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //membuat agar teks dapat dikirim
                    String msg = cho.getSelectedItem()+" : "+field.getText();	
                    //mengeset text yang dikirimkan
                    area.setText(area.getText()+"\n"+msg);
                    //untuk menuliskan teks ke agar terkirim di output stream
                    dout.writeUTF(msg);
                    //agar tulisan pesan setalah diketik lalu hilang
                    field.setText("");
        }catch(Exception e1){}
        }
    }   //membuat method untuk menjalankan pesan
        public void actionPerformed(ActionEvent e) {		
		
	}
    public static void main(String[] args){
        //menginsisiasi untuk memanggil method ChatClient server
        ChatClient_server frame = new ChatClient_server();
        //menampilkan gui
        frame.launchFrame();
        String msginput = "";
        try{
            //menginisiasi port untuk socket
            ssoc = new ServerSocket(6001);
            while(true){
                //menerima socket dari server
                soc = ssoc.accept();
                //membuat variabel stream input untuk mengeluarkan pesan
                din = new DataInputStream(soc.getInputStream());
                dout = new DataOutputStream(soc.getOutputStream());
            
	        while(true){
                    //mendeteksi anatar dua server buat baca pesan di dua server sm buat ngirim 
	            msginput = din.readUTF();
                    area.setText(area.getText()+"\n"+msginput);
            	}
            }
            
        }catch(Exception e){}
    }
}
        
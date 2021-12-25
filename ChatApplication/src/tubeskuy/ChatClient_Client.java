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
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
//membuat method runnable agar menghubungkan ke socket
public class ChatClient_Client extends JFrame implements Runnable{
    static Socket soc; //menerima serverSocket
    static DataInputStream din;//menginputkan pesan chat
    static DataOutputStream dout;//mengirim pesan chat
    //menampilkan text area
    static TextArea area = new TextArea(10,50);
    private static TextField field;//menampilakn text field
    private Button b1 = new Button("Send");//menampilkan button
    private Button b2 = new Button("Quit");
    private Choice cho = new Choice();//menampilkan pilihan choicebox
    private MenuBar mnbr = new MenuBar();//menampilkan gui menubar
    
    public ChatClient_Client(String name){
        super(name);//menamai gui
	b1.setSize(20,1);//ukuran button
        b1.setBackground(new Color(63, 255, 128));//warna button
	b2.setSize(20,1);
        b2.setBackground(new Color(250, 99, 71));
        field = new TextField(50);//ukuran gui nya menerima pesan 50 karakter
	Menu file =  new Menu("File");//menambahin file dan help di menu bar
	Menu help =  new Menu("Help");
	MenuItem quit =  new MenuItem("Quit");//menambahkan item ke menu bar
        //quit untuk menu item dari menubar
	quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
		System.exit(0);//mengeluarkan chat
				
            }
        });
        //menambahkan menu item ke menubar
        file.add(quit);
        //menu dalam menu bar ditempel ke panel
	mnbr.add(file);
	mnbr.add(help);
	this.setMenuBar(mnbr);
        
    }
    
    public void launchFrame(){
        //menampilkan ketika resize sesuai urutan ke kanan kiri
	this.setLayout(new FlowLayout(FlowLayout.LEADING));
        //membuat panel 
	JPanel panel = new JPanel();
        panel.setBackground(new Color(70,130,180));//mewarnai panel
        area.setBackground(new Color(224, 255, 255));//mewarnai area
	panel.setLayout(new GridLayout(5,1));
        getContentPane().setBackground(new Color(70,130,180));//mewarnai bagian frame
	b1.addActionListener(new sendChat());//menjalankan method agar terkirim
	b2.addActionListener(new ActionListener(){//menjalankan method agar quit
			
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
            
        }
    });
        //memilih user
        cho.add("Alya ");
        cho.add("Soya ");
        cho.add("Rabby");
        cho.add("Anony ");
        //button2nya menambahkan ke dalam panel 
        panel.add(b1);
        panel.add(b2);
        panel.add(cho);
        //memberikan ukuran frame
        this.setSize(470,300);
        //menambahkan text area ke dalam frame
        this.add(area);
        this.add(panel);//menambhakan panel ke frame
        this.add(field);//menambahkan field ke frame
        this.setVisible(true);//agar muncul framenya
        this.setResizable(false);//agar tidak bisa diubah ukurannya
    }
    //membuat void run untuk menajlankan thread
    @Override
    public void run(){}
    //membuat class sendchat untuk menjalankan pesan agar terkirim
        class sendChat implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //menjalankan choicebox bisa terpilih dan ikut terkirim ke dalam pesan
                    String msg = cho.getSelectedItem()+" : "+field.getText();
                    //menampilkan text ke dalam text area 
                    area.setText(area.getText()+"\n"+msg);
                    //mengetikkan pesan agar terkirim ke server
                    dout.writeUTF(msg);
                    //agar pesan yang sudah terkirim langsung terhapus otomatis
                    field.setText("");
        }catch(Exception e1){}
		
        }
    }  
       //mengirimkan pesan 
        public void actionPerformed(ActionEvent e){
        
        }
//method untuk menampilkan gui dan mengirim pesan
    public static void main(String[] args){
        //membuat memanggil class chat client_client dengan parameter chatroomclient
        ChatClient_Client frame = new ChatClient_Client("Chat Room Client");
        frame.launchFrame(); 
        try{
            //membuat koneksi untuk menerima server socket dengan alamat ip, port yang sama
            soc = new Socket("127.0.0.1", 6001);
            //untuk menerima pesan yang dikirim dari server dengan memanggil data inputstream
            din  = new DataInputStream(soc.getInputStream());
            //untuk mengirim pesan ke server dengan memanggil data outputsream
            dout = new DataOutputStream(soc.getOutputStream());
            
//            String msginput = "";
            
	    while(true){
	        msginput = din.readUTF();
            	area.setText(area.getText()+"\n"+msginput);
            }
            
        }catch(Exception e){}
    }


}
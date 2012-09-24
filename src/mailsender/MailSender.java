package mailsender;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
/*
 * 
 * @author Somsubhra
 */
public class MailSender {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Personal Mail Sender");
        JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(3,2));
       
        JLabel fromlabel = new JLabel("From:");
        final JTextField fromtextfield = new JTextField(40);
        JLabel tolabel = new JLabel("To:");
        JLabel subjectlabel = new JLabel("Subject:");
        final JTextField subjectfield = new JTextField(40);
        final JTextField totextfield = new JTextField(40);
        final JTextArea messagearea = new JTextArea(40,40);
        JButton sendbutton = new JButton("Send");
        fromtextfield.setText("somsubhra.bairi@gmail.com");
        
        class SendListener implements ActionListener{

            @Override
            public void actionPerformed(ActionEvent e) {
                String from = fromtextfield.getText();
                String to = totextfield.getText();
                String subject = subjectfield.getText();
                
                String message = messagearea.getText();
                SendMail sendMail = new SendMail(from,to,subject,message);
                sendMail.send();
                
            }
        
        }
        
        ActionListener listener = new SendListener();
        sendbutton.addActionListener(listener);
        panel.add(fromlabel);
        panel.add(fromtextfield);
        panel.add(tolabel);
        panel.add(totextfield);
        panel.add(subjectlabel);
        panel.add(subjectfield);
        panel.add(sendbutton);
        panel.add(messagearea);
        
        frame.add(panel);
        frame.setResizable(false);
        frame.setSize(500,700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        }
    
    static class SendMail{
    private String from;
    private String to;
    private String subject;
    private String text;
    
    
    public SendMail(String from, String to, String subject, String text){
        this.from=from;
        this.to = to;
        this.subject = subject;
        this.text = text;
        }
    
    
    public void send(){
        String host = "smtp.gmail.com";
        String userid = "somsubhra.bairi@gmail.com";
        String password = "password";
        
        try{
            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", "true"); 
            props.put("mail.smtp.host", host); 
            props.setProperty("mail.transport.protocol", "smtps");
            props.put("mail.smtp.user", userid); 
            props.put("mail.smtp.password", password); 
            props.put("mail.smtp.port", "465"); 
            props.put("mail.smtps.auth", "true"); 
            Session session = Session.getDefaultInstance(props, null); 
            MimeMessage message = new MimeMessage(session); 
            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;
            try {
                 fromAddress = new InternetAddress(from);
                 toAddress = new InternetAddress(to);
                } catch (AddressException e) {

                    e.printStackTrace();
            }
        message.setFrom(fromAddress);
        message.setRecipient(RecipientType.TO, toAddress);
        message.setSubject(subject);
        message.setText(text); 

   

        Transport transport = session.getTransport("smtps"); 
        transport.connect(host, userid, password); 
        transport.sendMessage(message, message.getAllRecipients()); 
        transport.close(); 
        
        }catch (MessagingException e) {
        e.printStackTrace();
        }
        
        } 
    }
}

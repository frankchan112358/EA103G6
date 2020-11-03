package com.mail.service;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;

public class MailNoSSL {
	
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String subject, String messageText) {
			
	   try {
		   // 設定使用連線至 Gmail smtp Server
		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.port", "587");

       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
       // ●須將myGmail的【安全性較低的應用程式存取權】打開
	     final String myGmail = "ea103g6@gmail.com";
	     final String myGmail_password = "etvehbslckxoyavc";
		   Session session = Session.getInstance(props, new Authenticator() {
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(myGmail, myGmail_password);
			   }
		   });

		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(myGmail));
		   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		  
		   //設定信中的主旨  
		   message.setSubject(subject);
		   //設定信中的內容 
		   message.setText(messageText);

		   Transport.send(message);
		   System.out.println("傳送成功!");
     }catch (MessagingException e){
	     System.out.println("傳送失敗!");
	     e.printStackTrace();
     }
   }
	
	
	public void sendHTMLMail(String to,String subject, String messageText){
		try {
		   // 設定使用SSL連線至 Gmail smtp Server
		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.port", "587");
		   
		
		String html="<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<title>Insert title here</title>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"<div style=\"background-color:#ffffff\">\r\n" + 
				"        <div style=\"margin:0px auto;max-width:600px\">\r\n" + 
				"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\r\n" + 
				"                <tbody>\r\n" + 
				"                    <tr>\r\n" + 
				"                        <td style=\"direction:ltr;font-size:0px;padding:0;text-align:center\">\r\n" + 
				"                            <div class=\"m_5805568617308718997mj-column-per-100\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\r\n" + 
				"                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\r\n" + 
				"                                    <tbody>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td align=\"center\" style=\"font-size:0px;padding:20px;word-break:break-word\">\r\n" + 
				"                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\r\n" + 
				"                                                    <tbody>\r\n" + 
				"                                                        <tr>\r\n" + 
				"                                                            <td style=\"width:150px\"><a href=\"https://i.imgur.com/3AM005I.jpg\" target=\"_blank\" data-saferedirecturl=\"https://i.imgur.com/3AM005I.jpg\"><img height=\"auto\" src=\"https://i.imgur.com/3AM005I.jpg\" style=\"border:0;display:block;outline:none;text-decoration:none;height:auto;width:100%;font-size:13px\" width=\"150\" class=\"CToWUd\"> </a></td>\r\n" + 
				"                                                        </tr>\r\n" + 
				"                                                    </tbody>\r\n" + 
				"                                                </table>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </tbody>\r\n" + 
				"                                </table>\r\n" + 
				"                            </div>\r\n" + 
				"                        </td>\r\n" + 
				"                    </tr>\r\n" + 
				"                </tbody>\r\n" + 
				"            </table>\r\n" + 
				"        </div>\r\n" + 
				"        <div style=\"margin:0px auto;max-width:600px\">\r\n" + 
				"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\r\n" + 
				"                <tbody>\r\n" + 
				"                    <tr>\r\n" + 
				"                        <td style=\"direction:ltr;font-size:0px;padding:0;text-align:center\">\r\n" + 
				"                            <div class=\"m_5805568617308718997mj-column-per-100\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\r\n" + 
				"                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\r\n" + 
				"                                    <tbody>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td align=\"center\" style=\"font-size:0px;padding:0;word-break:break-word\">\r\n" + 
				"                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:collapse;border-spacing:0px\">\r\n" + 
				"                                                    <tbody>\r\n" + 
				"                                                        <tr>\r\n" + 
				"                                                            <td style=\"width:600px\"><img height=\"auto\" src=\"https://i.imgur.com/K3yQHfj.png\" style=\"border:0;display:block;outline:none;text-decoration:none;height:65%;width:100%;font-size:13px\" width=\"600\" class=\"CToWUd a6T\" tabindex=\"0\">\r\n" + 
				"                                                                <div class=\"a6S\" dir=\"ltr\" style=\"opacity: 0.01; left: 383px; top: 230px;\">\r\n" + 
				"                                                                    <div id=\":12t\" class=\"T-I J-J5-Ji aQv T-I-ax7 L3 a5q\" role=\"button\" tabindex=\"0\" aria-label=\"下載附件「」\" data-tooltip-class=\"a1V\" data-tooltip=\"下載\">\r\n" + 
				"                                                                        <div class=\"aSK J-J5-Ji aYr\"></div>\r\n" + 
				"                                                                    </div>\r\n" + 
				"                                                                </div>\r\n" + 
				"                                                            </td>\r\n" + 
				"                                                        </tr>\r\n" + 
				"                                                    </tbody>\r\n" + 
				"                                                </table>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </tbody>\r\n" + 
				"                                </table>\r\n" + 
				"                            </div>\r\n" + 
				"                        </td>\r\n" + 
				"                    </tr>\r\n" + 
				"                </tbody>\r\n" + 
				"            </table>\r\n" + 
				"        </div>\r\n" + 
				"        <div style=\"margin:0px auto;border-radius:300px;max-width:600px\">\r\n" + 
				"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;border-radius:300px\">\r\n" + 
				"                <tbody>\r\n" + 
				"                    <tr>\r\n" + 
				"                        <td style=\"background-image:url(https://ci4.googleusercontent.com/proxy/dId1S_DH3lkIcQLFHcz6euo-zCxYYgSZj1d1kmoVEQ_E6n4L2q4m-5L3ay5S6pOh6SsLLLUl8oj1Ayb4Tu5u8_dhlziUTsa7kmTy-RpQ1kZS728Ds_9JLWdKqvZWDs7EF7pjt6wWynJST2LYvedCtkFUD_g-0WUF380=s0-d-e1-ft#https://gallery.mailchimp.com/2eb5bda4eb395c6f009c75344/images/8476e18b-9d17-44bb-8b73-f8cc8762b114.png);background-position:bottom;background-size:auto;background-repeat:no-repeat;margin:0px auto;max-width:600px;padding:30px 20px\">\r\n" + 
				"                            <div class=\"m_5805568617308718997mj-column-per-100\" style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\r\n" + 
				"                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\r\n" + 
				"                                    <tbody>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td align=\"left\" class=\"m_5805568617308718997title\" style=\"font-size:0px;padding:10px 25px;padding-bottom:10px;word-break:break-word\">\r\n" + 
				"                                                <div style=\"font-family:Helvetica,Arial;font-size:20px;font-weight:bold;letter-spacing:0.5px;line-height:150%;text-align:left;color:#3c4043\"><span style=\"color:3c4043\">WelCome<br>\r\n" + 
				"                                                        點擊下方按鈕啟用帳號</span></div>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td align=\"left\" class=\"m_5805568617308718997small-content\" style=\"font-size:0px;padding:0px 25px;word-break:break-word\">\r\n" + 
				"                                                <div style=\"font-family:Helvetica,Arial;font-size:14px;letter-spacing:0.5px;line-height:26px;text-align:left;color:#3c4043\">\r\n" + 
				"                                                    <p>Work Join Learn以提供學生終身學習為目標。<wbr>平台結合實體課程落實扎實學習，在上完實體課程後學生可以在平台上複習上課影片<wbr>，並提供學生管理上課相關的事務，成為學生轉職學習時的最佳後盾。<br>\r\n" + 
				"                                                        \r\n" + 
				"                                                </div>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td style=\"font-size:0px;word-break:break-word\">\r\n" + 
				"                                                <div style=\"height:30px\">&nbsp;</div>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td align=\"center\" style=\"font-size:0px;padding:0px 25px;word-break:break-word\">\r\n" + 
				"                                                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:separate;line-height:100%\">\r\n" + 
				"                                                    <tbody>\r\n" + 
				"                                                        <tr>\r\n" + 
				"                                                            <td align=\"center\" bgcolor=\"#f45e43\" role=\"presentation\" style=\"border:none;border-radius:10px;background:#f45e43\" valign=\"middle\"><a href="+messageText+" style=\"display:inline-block;background:#f45e43;color:white;font-family:Helvetica,Arial;font-size:16px;font-weight:normal;line-height:120%;letter-spacing:0.5px;margin:0;text-decoration:none;text-transform:none;padding:15px 30px;border-radius:10px\" target=\"_blank\" data-saferedirecturl="+messageText+">啟用帳號</a></td>\r\n" + 
				"                                                        </tr>\r\n" + 
				"                                                    </tbody>\r\n" + 
				"                                                </table>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td style=\"font-size:0px;word-break:break-word\">\r\n" + 
				"                                                <div style=\"height:30px\">&nbsp;</div>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </tbody>\r\n" + 
				"                                </table>\r\n" + 
				"                            </div>\r\n" + 
				"                        </td>\r\n" + 
				"                    </tr>\r\n" + 
				"                </tbody>\r\n" + 
				"            </table>\r\n" + 
				"        </div>\r\n" + 
				"        <div style=\"margin:0px auto;max-width:600px\">\r\n" + 
				"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\r\n" + 
				"                <tbody>\r\n" + 
				"                    <tr>\r\n" + 
				"                        <td style=\"direction:ltr;font-size:0px;padding:0px 0;text-align:center\">\r\n" + 
				"                            <div style=\"margin:0px auto;max-width:600px\">\r\n" + 
				"                               \r\n" + 
				"                                                </div>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </tbody>\r\n" + 
				"                                </table>\r\n" + 
				"                            </div>\r\n" + 
				"                            <div style=\"margin:0px auto;max-width:600px\">\r\n" + 
				"                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%\">\r\n" + 
				"                                    <tbody>\r\n" + 
				"                                        <tr>\r\n" + 
				"                                            <td style=\"direction:ltr;font-size:0px;padding:0 20px;text-align:center\">\r\n" + 
				"                                                <div style=\"font-size:0px;text-align:left;direction:ltr;display:inline-block;vertical-align:top;width:100%\">\r\n" + 
				"                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top\" width=\"100%\">\r\n" + 
				"                                                        <tbody>\r\n" + 
				"                                                            \r\n" + 
				"                                                            <tr>\r\n" + 
				"                                                                <td style=\"font-size:0px;word-break:break-word\">\r\n" + 
				"                                                                    <div style=\"height:0px\">&nbsp;</div>\r\n" + 
				"                                                                </td>\r\n" + 
				"                                                            </tr>\r\n" + 
				"                                                            <tr>\r\n" + 
				"                                                                <td align=\"center\" class=\"m_5805568617308718997content\" style=\"font-size:0px;padding:0;word-break:break-word\">\r\n" + 
				"                                                                    <div style=\"font-family:Helvetica,Arial;font-size:12px;letter-spacing:0.5px;line-height:180%;text-align:center;color:#989898\">2020 © Work Join Learn</div>\r\n" + 
				"                                                                </td>\r\n" + 
				"                                                            </tr>\r\n" + 
				"                                                            <tr>\r\n" + 
				"                                                                <td style=\"font-size:0px;word-break:break-word\">\r\n" + 
				"                                                                    <div style=\"height:0px\">&nbsp;</div>\r\n" + 
				"                                                                </td>\r\n" + 
				"                                                            </tr>\r\n" + 
				"                                                        </tbody>\r\n" + 
				"                                                    </table>\r\n" + 
				"                                                </div>\r\n" + 
				"                                            </td>\r\n" + 
				"                                        </tr>\r\n" + 
				"                                    </tbody>\r\n" + 
				"                                </table>\r\n" + 
				"                            </div>\r\n" + 
				"                        </td>\r\n" + 
				"                    </tr>\r\n" + 
				"                </tbody>\r\n" + 
				"            </table>\r\n" + 
				"        </div>\r\n" + 
				"    </div>\r\n" + 
				"</body>\r\n" + 
				"</html>";
		
		
        	
        	Multipart multiPart = new MimeMultipart();
        	MimeBodyPart htmlPart = new MimeBodyPart(); 
			htmlPart.setContent(html, "text/html;charset=UTF-8");
			multiPart.addBodyPart(htmlPart);
			
		     final String myGmail = "ea103g6@gmail.com";
		     final String myGmail_password = "etvehbslckxoyavc";
			   Session session = Session.getInstance(props, new Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
					   return new PasswordAuthentication(myGmail, myGmail_password);
				   }
			   });
        	
        	Message message = new MimeMessage(session);
        	message.setFrom(new InternetAddress(myGmail));
        	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
  		   	message.setSubject(subject);
  		   	message.setContent(multiPart);
  		   	Transport.send(message);
  		   	
            System.out.println("寄送成功");
			
		} catch (Exception e) {
			System.out.println("傳送失敗!");
		     e.printStackTrace();
		}
	}

	
	 public static void main (String args[]){

		 String to = "yymm55680@gmail.com";
      
		 String subject = "java測試";
      
//		 String ch_name = "peter1";
//		 String passRandom = "111";
//		 String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" +" (已經啟用)"; 
       
		 MailNoSSL mailService = new MailNoSSL();
//      	mailService.sendMail(to, subject, messageText);
		 mailService.sendHTMLMail(to, subject, "https://www.google.com.tw/");
		 
		 
		 System.out.println("傳送成功");
		 

   }


}


package com.kj.cherryengineering20.payroll;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kj.cherryengineering20.employees.EmployeeDatabase;

public class EmailManager {
    public void emailPayroll(Context context) {

        final String username = "kmjohnson107@gmail.com";
        final String password = "jcoh behh omeh qnpn";
        EmployeeDatabase db = new EmployeeDatabase(context);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bball2471990@yahoo.com"));
            message.setSubject("Payroll");
            message.setText(db.getAllEmployeeTotals());

            Transport.send(message);

            Log.i("Email", "Email sent successfully!");

        } catch (MessagingException e) {
            Log.e("Email", "Email sending failed: " + e.getMessage(), e);
        }
    }
}

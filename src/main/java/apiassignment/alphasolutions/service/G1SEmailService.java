package apiassignment.alphasolutions.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;

public class G1SEmailService {

    private final String SENDGRIP_API_KEY = "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";


    public void sendEmail(String to, String subject, String content) throws IOException {
        Email from = new Email("Zahaawii@gmail.com");
        Email toEmail = new Email(to);
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from,subject,toEmail,emailContent);

        SendGrid sg = new SendGrid(SENDGRIP_API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("Response body " + response.getBody());
            System.out.println("Respone headers: " + response.getHeaders());
        } catch (IOException e) {
            throw new RuntimeException("failed to send email: " + e.getMessage());
        }
    }

}


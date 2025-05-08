package service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Service pour l'envoi de SMS via l'API REST de Twilio
 */
public class TwilioSMSService {

    private static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
    private static final String FROM_NUMBER = System.getenv("TWILIO_FROM_NUMBER");

    private static boolean initialized = false;

    public static void init() {
        if (ACCOUNT_SID == null || AUTH_TOKEN == null || FROM_NUMBER == null) {
            throw new IllegalStateException("Les variables d'environnement Twilio ne sont pas correctement définies.");
        }
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        initialized = true;
    }

    public static void sendSMS(String toNumber, String messageBody) {
        if (!initialized) init();
        Message message = Message.creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(FROM_NUMBER),
                        messageBody)
                .create();
        System.out.println("SMS envoyé avec l'ID: " + message.getSid());
    }
}

package kz.arta.ext.z3950.service;

import kz.arta.ext.z3950.util.CodeConstants;
import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by timur on 9/9/14.
 * Слушатель для событий изменеия книг для переиндексации в Zebra
 */
@SuppressWarnings({"CdiInjectionPointsInspection", "UnusedDeclaration"})
@MessageDriven(name = "IndexZebraQueue",
        activationConfig =
                {
                        @ActivationConfigProperty(propertyName = "destinationType",
                                propertyValue = "javax.jms.Queue"),
                        @ActivationConfigProperty(propertyName = "destination",
                                propertyValue = CodeConstants.INDEX_ZEBRA_JMS_DESTINATION)
                })
public class UpdateIndexZebraListener implements MessageListener {

    @Inject
    private Logger log;

    @Inject
    private ExporterForIndexer exporter;

    @Override
    public void onMessage(Message message) {
        log.info(" zebra get message {}", message);
        if (!(message instanceof TextMessage)) {
            return;
        }
        try {
            String dataUUID = ((TextMessage) message).getText();
            log.info("--------------UPDATE ZEBRA INDEX FOR {}-----------------", dataUUID);
            exporter.export(dataUUID, true);
        } catch (JMSException e) {
            log.error("error do message - {}", message);
        }

    }
}

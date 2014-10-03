package kz.arta.ext.sms.service;

import org.slf4j.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 * Created by admin on 02.10.14.
 */
@Singleton
@Startup
public class SmsSendManager {
    @Inject
    private Logger log;

    @Inject
    private SmsSender sender;

    @Schedule(second = "0", minute ="0", hour = "*/1")
    public void runTask() {
        log.info("smsSend: run task sender sms");
        sender.runSmsSender();
        log.info("smsSend: finish task sender sms");
    }
}

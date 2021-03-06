package kz.arta.ext.skud.rest;

import kz.arta.ext.api.config.ConfigReader;
import kz.arta.ext.api.config.ConfigUtils;
import kz.arta.ext.api.rest.AFormsReader;
import kz.arta.ext.api.rest.RestQueryContext;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Администратор on 04.09.2014.
 * сервис для портала с вызовом СКУД
 */
@Path("/rest")
public class SkudService extends AFormsReader {

    public static final String USER_ADDITIONAL_FORM_UUID = "user.additional.form.uuid";
    public static final String USER_ADDITIONAL_IIN_FIELD = "user.additional.iin.field";
    private final String formUUID;
    private final String iinField;
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private Logger log;
    @Resource(mappedName = "java:jboss/datasources/SkudDS")
    private DataSource ds;

    public SkudService() {
        formUUID = ConfigReader.getPropertyValue(USER_ADDITIONAL_FORM_UUID);
        iinField = ConfigReader.getPropertyValue(USER_ADDITIONAL_IIN_FIELD);
    }


    @GET
    @Path("/getUserIDByCardUID")
    @Produces(MediaType.TEXT_PLAIN)
    public String getUserInfoByCardUid(@QueryParam("cardUID") String cardUID) {
        try {
            String staffIIN = getStaffIinByCardUidFromSkudDB(cardUID);

            if (staffIIN != null) {
                // Формируем запрос по получению информации о сотруднике (читателе) из "системы"
                String query = "/rest/api/filecabinet/get_by_field_value?formUUID=" + formUUID
                        + "&value=" + staffIIN + "&fieldName=" + iinField;

                // Получаем результат и его же отправляем как результат
                RestQueryContext context = ConfigUtils.getQueryContext();
                return doGetQuery(context, query);
            }
        } catch (Exception e) {
            log.error("error read user info", e);
        }
        return null;

    }

    @GET
    @Path("/getUserIinByCardUID")
    @Produces(MediaType.APPLICATION_JSON)
    //    @Interceptors(JacksonJsonpInterceptor.class)
    public SkudPerson getPersonIinByCardUid(@QueryParam("cardUID") String cardUID) {
        String iin = getStaffIinByCardUidFromSkudDB(cardUID);
        return iin != null ? new SkudPerson(iin) : null;
    }


    /**
     * Получение ИИН сотрудника из БД СКУД по UID-карте
     *
     * @param cardUID - UID-карты сотрудника
     * @return ИИН сотрудника
     */
    private String getStaffIinByCardUidFromSkudDB(String cardUID) {
        log.debug("getStaffIinByCardUidFromSkudDB - {}", cardUID);
        Connection con = null;
        Statement stmt = null;
        try {
            con = ds.getConnection();
            stmt = con.createStatement();
            // Получаем из БД СКУД идентификатор актуального сотрудника по UID карте
            ResultSet rs = stmt.executeQuery(
                    String.format("SELECT t1.ID_STAFF FROM STAFF t1 LEFT JOIN STAFF_CARDS t2 ON t1.ID_STAFF = t2.STAFF_ID WHERE t2.IDENTIFIER = %s"
                            //" and t1.DATE_DISMISS IS NULL"
                            , cardUID)
            );
            if (rs.next()) {
                // Если существует актуальный сотрудник, берем его идентификатор
                long staffId = rs.getLong(1);
                log.debug("find staffID {} by card {}", staffId, cardUID);
                if (staffId > 0) {
                    // Получаем ИИН по идентификатору сотрудника
                    rs = stmt.executeQuery(
                            String.format("SELECT INFO_DATA FROM STAFF_INFO_DATA_STR WHERE REF_ID=45619 AND STAFF_ID=%d", staffId));
                    if (rs.next()) {
                        String staffIIN = rs.getString(1);
                        return staffIIN != null ? staffIIN : null;
                    } else{
                        log.debug("can't find iin for staffId {}", staffId);
                    }
                }
            }else{
                log.error("can't find any staffId in BD");
            }
        } catch (Exception e) {
            log.error("error get iin from SKUD", e);
        } finally {
            if (stmt != null) try {
                stmt.close();
            } catch (Exception ee) {
                log.error("error close statement to  SKUD", ee);
            }
            if (con != null) try {
                con.close();
            } catch (Exception ee) {
                log.error("error close connection to SKUD", ee);
            }
        }
        return null;
    }
}

ALTER TABLE sms_gate ADD CHECK_STATUS VARCHAR(80);
ALTER TABLE sms_gate ADD SMSID_START VARCHAR(50);
ALTER TABLE sms_gate ADD CHECK_STATUS_OK VARCHAR(50);
ALTER TABLE sms_jurnal ADD SMSID_SEND VARCHAR(50);
ALTER TABLE sms_order ADD COUNT_TRY int;
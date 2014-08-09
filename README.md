enu.z39.50
==========
![logo](src/main/webapp/img/arta.png)

##���������
* �� ������� ���������� yaz
 * debian - apt-get install libyaz4-dev swig
 * windows - ������� � ���������� [�����������](http://ftp.indexdata.dk/pub/yaz/win32/yaz_5.3.0.exe)
* �� ������� ���������� ���������� ��������� ��� yaz
 * debian
    * LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/path/to/libyaz4j.so
    * export LD_LIBRARY_PATH
 * windows
    * set PATH=%PATH;C:\Program Files (x86)\YAZ\bin\yaz4j.dll
* ������ ��. �������� ������ [init.sql](init.sql) �� �� books
* ��������� JBOSS
 * �������� �������� � �� books 
    * Name - **BookDS**
    * JNDI - **java:jboss/datasources/BookDS**
 * �������� JMS Queue
    * Name - **MigrateWorkerQueue**
    * JNDI - **java:jboss/queues/Synergy/MigrateWorkerQueue**
* ��������� ������ [������](esot.properties)
 * ��������� ���������� ��������� �� ����������� � ������� _synergy.address_, _user.login_, _user.password_
 * ��������� ���������� ������������� **������� �������** _order.registry.uuid_, _order.form.uuid_
 * ����������� ���� �������� � jboss � ������������ _(�� ��������� standalone/configuration)_
* ���������� war � JBOSS

 
##������
��������� ���������� � ������� [migrate](http://localhost:8080/z3950/)

--------------------------------------------------------
--  FUNCTION for Table PRODUCT BLOB
--------------------------------------------------------

CREATE OR REPLACE  DIRECTORY MEDIA_DIR AS 'C:/DB_photos1/'; 

CREATE OR REPLACE FUNCTION load_blob( myFileName VARCHAR) RETURN BLOB as result BLOB;  
  myBFILE      BFILE;
  myBLOB       BLOB;
BEGIN
    myBFILE := BFILENAME('MEDIA_DIR',myFileName);
    dbms_lob.createtemporary(myBLOB, TRUE);
    dbms_lob.fileopen(myBFILE,dbms_lob.file_readonly);
    dbms_lob.loadfromfile(myBLOB,myBFILE,dbms_lob.getlength(myBFILE) );
    dbms_lob.fileclose(myBFILE);
    RETURN myBLOB;
END load_blob;

--------------------------------------------------------
--  Update Photo 使用者 WJLUSER
--------------------------------------------------------

UPDATE WJLUSER SET PHOTO=load_blob('U000001.jpg') WHERE USERNO = 'U000001';
UPDATE WJLUSER SET PHOTO=load_blob('U000002.jpg') WHERE USERNO = 'U000002';
UPDATE WJLUSER SET PHOTO=load_blob('U000004.jpg') WHERE USERNO = 'U000004';
UPDATE WJLUSER SET PHOTO=load_blob('U000005.jpg') WHERE USERNO = 'U000005';
UPDATE WJLUSER SET PHOTO=load_blob('U000007.jpg') WHERE USERNO = 'U000007';
UPDATE WJLUSER SET PHOTO=load_blob('U000008.jpg') WHERE USERNO = 'U000008';
UPDATE WJLUSER SET PHOTO=load_blob('U000009.jpg') WHERE USERNO = 'U000009';

COMMIT;
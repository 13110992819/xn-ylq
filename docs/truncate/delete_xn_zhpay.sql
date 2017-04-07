SET SQL_SAFE_UPDATES = 0;
delete from tsys_config where system_code = 'CD-CZH000001';
delete from tsys_dict where system_code = 'CD-CZH000001';

delete from tyqs_hzb where system_code = 'CD-CZH000001';
delete from tyqs_hzb_mgift where system_code = 'CD-CZH000001';

delete from tyqs_hzb_template where system_code = 'CD-CZH000001';
delete from tyqs_hzb_yy where system_code = 'CD-CZH000001';

delete from tyydb_jewel where system_code = 'CD-CZH000001';
delete from tyydb_jewel_record where system_code = 'CD-CZH000001';

delete from tyydb_jewel_record_number where system_code = 'CD-CZH000001';
delete from tyydb_jewel_template where system_code = 'CD-CZH000001';
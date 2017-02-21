部署步骤：
1，eclipse导出war包

2. 本地war包上传至服务器
   scp -P 22 xn-zhpay.war root@121.43.101.148:/home  
   T6dh%$%$ss1

3. 备份原先配置(如果第一次部署，跳过此步骤)
  ssh root@121.43.101.148 -p 22

  cd /home/wwwroot/zhpay/tomcat_zhpay_biz/webapps
  cp ./xn-zhpay/WEB-INF/classes/application.properties .
  cp ./xn-zhpay/WEB-INF/classes/config.properties .
  rm -rf xn-zhpay*
  mv /home/xn-zhpay.war .
   
4. 已备份配置文件放回原处,重启tomcat
  mv -f application.properties ./xn-zhpay/WEB-INF/classes/
  mv -f config.properties ./xn-zhpay/WEB-INF/classes/
  
  ../bin/shutdown.sh
  ../bin/startup.sh
  
6. 验证程序
  http://121.43.101.148:5602/xn-zhpay/api


# 智能图书管理系统-小程序

微信小程序，JAVA后端
~~~
登陆流程：
localhost:9999/api/user/login   
METHOD: POST
application/x-www-form-urlencoded
mobile      String
password    String
Eg: localhost:/api/user/login?moblie=xxx&password=xxx

然后请求资源携带token
在header添加   token ：value（此为登陆返回的token）
~~~

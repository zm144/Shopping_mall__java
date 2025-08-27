本项目是依据黑马程序员的javaweb商城项目搭建的，使用servlet+jdbc开发技术，需要tomcat（10.0版本tomcat可能和项目不兼容），链接如下：
https://www.bilibili.com/video/BV1AN4y1t7Re/?spm_id_from=333.337.search-card.all.click&vd_source=49fad50260a74fc1ff986866fe8202b1

邮箱注册激活和支付模块没做，因此也没用到redis。

注意更换MySQL connector java驱动版本，版本太老了，5.0.4版本的。 mysql是5.5.7版本的。JDK用的1.8版本。
附上MySQL connector驱动链接：
https://downloads.mysql.com/archives/c-j/
运行的时候一定要打开数据库，不然前台的用户模块会报错。
可能会出现包导不进去的问题，需要自行百度查询解决。
有数据库文件 store38.sql

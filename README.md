# java-ssm-mmall

商城：用到了支付宝支付、guava本地缓存(util/TokenCache.java)、讀取自定义properties文件(util/PropertiesUtil.java)、集合类工具、ftpclient上传到ftp服务器(util/FTPUtil.java)、mybatis-generator-core 反向生成java代码(util/generatorConfig.xml)、joda-time日期时间处理(util/DateTimeUtil.java)

```
<?xml version="1.0" encoding="UTF-8"?>

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>cn.xiaochi</groupId>
  <artifactId>mmall</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>war</packaging>

  <name>mmall Maven Webapp</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>

    <org.springframework.version>4.2.3.RELEASE</org.springframework.version>
    <org.mybatis.version>3.4.1</org.mybatis.version>
    <org.mybatis.spring.version>1.3.0</org.mybatis.spring.version>
    <jedis.version>2.9.0</jedis.version>
    <druid.version>1.0.20</druid.version>
    <joda-time.version>2.3</joda-time.version>
    <hashids.version>1.0.1</hashids.version>
    <pagehelper.version>5.1.2</pagehelper.version>
    <mysql-connector-java.version>5.1.30</mysql-connector-java.version>
    <jackson.version>2.8.1</jackson.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
    <!-- servlet -->
    <dependency>
      <groupId>org.apache.tomcat</groupId>
      <artifactId>tomcat-servlet-api</artifactId>
      <version>7.0.64</version>
    </dependency>
    <!-- springmvc -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>
    <!-- spring-oxm -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-oxm</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>

    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${org.springframework.version}</version>
    </dependency>

    <!-- AOP -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.7.3</version>
    </dependency>
    <!-- AOP用 -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjrt</artifactId>
      <version>1.6.11</version>
    </dependency>

    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>${org.mybatis.spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>${org.mybatis.version}</version>
    </dependency>

    <!-- jackson -->
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-core</artifactId>
      <version>${jackson.version}</version>
    </dependency>
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-annotations</artifactId>
      <version>${jackson.version}</version>
    </dependency>
    <!-- json序列化与反序列化用到 -->
    <dependency>
      <groupId>org.codehaus.jackson</groupId>
      <artifactId>jackson-mapper-asl</artifactId>
      <version>1.9.12</version>
    </dependency>

    <!-- druid -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>${druid.version}</version>
    </dependency>

    <!-- logback日志 -->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.1.2</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-core</artifactId>
      <version>1.1.2</version>
      <scope>compile</scope>
    </dependency>

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql-connector-java.version}</version>
    </dependency>
    <!-- 丰富的工具类与数据结构 -->
    <dependency>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
      <version>20.0</version>
    </dependency>
    <!-- apache工具类：apache-common中的lang增强包 -->
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-lang3</artifactId>
      <version>3.5</version>
    </dependency>
    <!-- 集合类工具 -->
    <dependency>
      <groupId>commons-collections</groupId>
      <artifactId>commons-collections</artifactId>
      <version>3.2.1</version>
    </dependency>

    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
      <!--<scope>test</scope>-->
    </dependency>

    <!-- 时间处理 -->
    <dependency>
      <groupId>joda-time</groupId>
      <artifactId>joda-time</artifactId>
      <version>${joda-time.version}</version>
    </dependency>

    <!-- id加密解密 -->
    <dependency>
      <groupId>org.hashids</groupId>
      <artifactId>hashids</artifactId>
      <version>${hashids.version}</version>
    </dependency>

    <!-- ftpclient上传到ftp服务器用的jar包 -->
    <dependency>
      <groupId>commons-net</groupId>
      <artifactId>commons-net</artifactId>
      <version>3.1</version>
    </dependency>

    <!-- file upload -->
    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.2.2</version>
    </dependency>

    <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.0.1</version>
    </dependency>
    <!-- redis -->
    <dependency>
      <groupId>redis.clients</groupId>
      <artifactId>jedis</artifactId>
      <version>${jedis.version}</version>
    </dependency>

    <!-- mybatis pager分页 -->
    <!-- pageHelper分页 -->
    <dependency>
      <groupId>com.github.pagehelper</groupId>
      <artifactId>pagehelper</artifactId>
      <version>${pagehelper.version}</version>
    </dependency>

<!--    <dependency>-->
<!--      <groupId>com.github.miemiedev</groupId>-->
<!--      <artifactId>mybatis-paginator</artifactId>-->
<!--      <version>1.2.17</version>-->
<!--    </dependency>-->

<!--    <dependency>-->
<!--      <groupId>com.github.jsqlparser</groupId>-->
<!--      <artifactId>jsqlparser</artifactId>-->
<!--      <version>0.9.4</version>-->
<!--    </dependency>-->


    <!-- alipay 支付宝 -->
    <dependency>
      <groupId>commons-codec</groupId>
      <artifactId>commons-codec</artifactId>
      <version>1.10</version>
    </dependency>
    <dependency>
      <groupId>commons-configuration</groupId>
      <artifactId>commons-configuration</artifactId>
      <version>1.10</version>
    </dependency>
    <dependency>
      <groupId>commons-lang</groupId>
      <artifactId>commons-lang</artifactId>
      <version>2.6</version>
    </dependency>
    <dependency>
      <groupId>commons-logging</groupId>
      <artifactId>commons-logging</artifactId>
      <version>1.1.1</version>
    </dependency>
    <dependency>
      <groupId>com.google.zxing</groupId>
      <artifactId>core</artifactId>
      <version>2.1</version>
    </dependency>
    <dependency>
      <groupId>com.google.code.gson</groupId>
      <artifactId>gson</artifactId>
      <version>2.3.1</version>
    </dependency>
    <dependency>
      <groupId>org.hamcrest</groupId>
      <artifactId>hamcrest-core</artifactId>
      <version>1.3</version>
    </dependency>

    <!-- mybatis-generator-core 反向生成java代码-->
    <dependency>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-core</artifactId>
      <version>1.3.5</version>
    </dependency>
  </dependencies>

  <build>
    <finalName>mmall</finalName>
      <plugins>
        <plugin>
          <!-- mybatis generator 自动生成代码插件 -->
          <groupId>org.mybatis.generator</groupId>
          <artifactId>mybatis-generator-maven-plugin</artifactId>
          <version>1.3.2</version>
          <configuration>
            <verbose>true</verbose>
            <overwrite>true</overwrite>
          </configuration>
        </plugin>
        <!-- geelynote maven的核心插件之-complier插件默认只支持编译Java 1.4，因此需要加上支持高版本jre的配置，在pom.xml里面加上 增加编译插 -->
        <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-compiler-plugin</artifactId>
          <configuration>
            <source>1.8</source>
            <target>1.8</target>
            <encoding>UTF-8</encoding>
            <compilerArguments>
              <!-- 本地jar，支付宝jar包放到  src/main/webapp/WEB-INF/lib 文件夹下，
                  如果没有配置，本地没问题，但是线上会找不到sdk类
                  为什么要引入，因为支付宝jar包再中央仓库没有
               -->
              <extdirs>${project.basedir}/src/main/webapp/WEB-INF/lib</extdirs>
            </compilerArguments>
          </configuration>
        </plugin>
      </plugins>
  </build>
</project>
```
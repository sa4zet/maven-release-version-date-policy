# maven-release-version-date-policy

Release version policy for use with Maven's maven-release-plugin,
specifies a version scheme: year.month.day.minor

Where year.month.day is the date when the `mvn release` command executed.
Like: `2020.7.20`

The minor is a number that tells the `mvn release` command how many times executed on the same day.
If you released 4 times at 2020.7.20 on a same day, then it will be: `2020.7.20.4`

## Usage

### Maven
```XML
<!-- https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-release-plugin -->
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-release-plugin</artifactId>
<version>${maven.release.plugin.version}</version>
<configuration>
  <projectVersionPolicyId>date</projectVersionPolicyId>
  <tagNameFormat>@{project.version}</tagNameFormat>
</configuration>
<dependencies>
  <dependency>
    <groupId>win.sa4zet</groupId>
    <artifactId>maven-release-version-date-policy</artifactId>
    <version>2020.7.20.1</version>
  </dependency>
</dependencies>
</plugin>
```
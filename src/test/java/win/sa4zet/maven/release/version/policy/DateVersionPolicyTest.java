package win.sa4zet.maven.release.version.policy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateVersionPolicyTest{

  DateVersionPolicy dateVersionPolicy = new DateVersionPolicy();
  LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
  String today = "" + now.get(ChronoField.YEAR) + "." + now.get(ChronoField.MONTH_OF_YEAR) + "." + now
    .get(ChronoField.DAY_OF_MONTH) + ".";

  private static VersionPolicyRequest versionPolicyRequest(String version){
    return new VersionPolicyRequest().setVersion(version);
  }

  @Test
  public void emptyDevVersionString(){
    String dev = dateVersionPolicy.getDevelopmentVersion(versionPolicyRequest("")).getVersion();
    String release = dateVersionPolicy.getReleaseVersion(versionPolicyRequest("")).getVersion();
    assertEquals(today + "2-SNAPSHOT", dev);
    assertEquals(today + "1", release);
  }

  @Test
  public void snapshotVersionString(){
    String dev = dateVersionPolicy.getDevelopmentVersion(versionPolicyRequest(today + "55-SNAPSHOT")).getVersion();
    String release = dateVersionPolicy.getReleaseVersion(versionPolicyRequest(today + "55-SNAPSHOT")).getVersion();
    assertEquals(today + "56-SNAPSHOT", dev);
    assertEquals(today + "55", release);
  }

  @Test
  public void devToReleaseVersionString(){
    String dev = dateVersionPolicy.getDevelopmentVersion(versionPolicyRequest(today + "55-SNAPSHOT")).getVersion();
    String release = dateVersionPolicy.getReleaseVersion(versionPolicyRequest(dev)).getVersion();
    assertEquals(today + "56-SNAPSHOT", dev);
    assertEquals(today + "56", release);
  }

  @Test
  public void releaseToDevVersionString(){
    String release = dateVersionPolicy.getReleaseVersion(versionPolicyRequest(today + "55-SNAPSHOT")).getVersion();
    String dev = dateVersionPolicy.getDevelopmentVersion(versionPolicyRequest(release)).getVersion();
    assertEquals(today + "56-SNAPSHOT", dev);
    assertEquals(today + "55", release);
  }

  @Test
  public void releaseVersionString(){
    String release = dateVersionPolicy.getReleaseVersion(versionPolicyRequest(today + "55")).getVersion();
    String dev = dateVersionPolicy.getDevelopmentVersion(versionPolicyRequest(release)).getVersion();
    assertEquals(today + "56-SNAPSHOT", dev);
    assertEquals(today + "55", release);
  }
}

package win.sa4zet.maven.release.version.policy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.shared.release.policy.version.VersionPolicy;
import org.apache.maven.shared.release.policy.version.VersionPolicyRequest;
import org.apache.maven.shared.release.policy.version.VersionPolicyResult;
import org.codehaus.plexus.component.annotations.Component;

/**
 API for next version calculations, used by maven-release-plugin to suggest release and next development versions.
 Release version policy for use with Maven's maven-release-plugin, specifies a version scheme: year.month.day.minor
 */
@Component(
  role = VersionPolicy.class,
  hint = "date",
  description = "Release version policy for use with Maven's maven-release-plugin, specifies a version scheme: year.month.day.minor")
public final class DateVersionPolicy implements VersionPolicy{

  private static final String DEVELOPMENT_VERSION_POSTFIX = "-" + Artifact.SNAPSHOT_VERSION;

  private final String today;

  public DateVersionPolicy(){
    final LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    today = "" + now.get(ChronoField.YEAR) + "." + now.get(ChronoField.MONTH_OF_YEAR) + "." + now
      .get(ChronoField.DAY_OF_MONTH) + ".";
  }

  /**
   Calculation of the release version from development state.
   */
  @Override
  public VersionPolicyResult getReleaseVersion(final VersionPolicyRequest versionPolicyRequest){
    return calculateNextVersion(versionPolicyRequest.getVersion(), true);
  }

  /**
   Calculation of the next development version from release state.
   */
  @Override
  public VersionPolicyResult getDevelopmentVersion(final VersionPolicyRequest versionPolicyRequest){
    return calculateNextVersion(versionPolicyRequest.getVersion(), false);
  }

  private VersionPolicyResult calculateNextVersion(String currentVersion, final boolean release){
    int minorVersion = 1;
    currentVersion = currentVersion.replaceAll(DEVELOPMENT_VERSION_POSTFIX, "");
    final int minorVersionPosition = currentVersion.lastIndexOf('.');
    if(minorVersionPosition > -1){
      final String versionDate = currentVersion.substring(0, currentVersion.lastIndexOf('.') + 1);
      if(today.equals(versionDate)){
        currentVersion = currentVersion.substring(currentVersion.lastIndexOf('.') + 1);
        if(!currentVersion.isBlank()){
          minorVersion = Integer.parseInt(currentVersion);
        }
      }
    }
    String postfix = "";
    if(release){
      postfix += minorVersion;
    }else{
      minorVersion++;
      postfix += minorVersion;
      postfix += DEVELOPMENT_VERSION_POSTFIX;
    }

    return new VersionPolicyResult().setVersion(today + postfix);
  }
}

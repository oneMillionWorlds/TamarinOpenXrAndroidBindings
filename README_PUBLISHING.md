## Signing

Artifacts published to Maven Central must be signed. Configure your GPG details in your personal Gradle properties file (typically at C:\\Users\\{user}\\.gradle\\gradle.properties on Windows):

    signing.keyId=keyId
    signing.password=password
    signing.secretKeyRingFile=C:/Users/{user}/AppData/Roaming/gnupg/pubring.kbx

Note: keyId is the last 8 characters of the long id. If needed, export an old-style secret ring file with `gpg --export-secret-keys -o secring.gpg`.

## Publishing via Sonatype Central Publisher Portal (Portal Publisher API)

OSSRH is being sunset in 2025. This project now publishes using the Central Publisher API. The publication type is USER_MANAGED: uploads are validated automatically but must be manually published in the Central Portal UI.

### Credentials

Set Central credentials either in gradle.properties or as environment variables. Your existing OSSRH username/password work in the Portal.

- gradle.properties entries:

    centralUsername=your-username
    centralPassword=your-password

- or environment variables:

    CENTRAL_USERNAME=your-username
    CENTRAL_PASSWORD=your-password

The CI workflow maps existing OSSRH secrets to these properties automatically.

### How it works

1) Gradle stages the Maven publication into a local directory (no network upload).
2) Gradle zips that staged repository as build/central-bundle.zip.
3) CI (or the provided script) uploads the bundle to the Central Publisher API with publishingType=USER_MANAGED.
4) You complete the release in the Portal UI once validation passes.

### Publish from your machine

- Ensure you have configured signing and Central credentials as above.
- Stage and zip the bundle:

      ./gradlew :android-native:prepareCentralBundle

- Upload the bundle with the helper script (provide credentials via env vars):

      CENTRAL_USERNAME=your-username CENTRAL_PASSWORD=your-password \
      .github/scripts/upload_central.sh "android-native/build/central-bundle.zip"

On success, the script prints a Deployment ID. Visit https://central.sonatype.com/publishing/deployments to monitor and manually Publish when the deployment reaches VALIDATED state.

You can still publish to your local Maven for testing:

    ./gradlew publishToMavenLocal

### Publish in CI (GitHub Actions)

The workflow at .github/workflows/publish.yml has been updated to:
- Build the project
- Load signing keys from secrets
- Provide Central credentials
- Run `./gradlew :android-native:prepareCentralBundle`
- Run `.github/scripts/upload_central.sh "android-native/build/central-bundle.zip"`

No direct uploads to OSSRH occur anymore in CI.

### Legacy OSSRH notes

The previous OSSRH (SonaType) repository configuration remains in the Gradle file for local/manual experiments only, but the automated pipeline no longer uses it. Prefer the Central Portal going forward.

### Manual testing of validated bundles

Central supports consuming validated (but not yet published) artifacts via a special repository that requires an Authorization header. In gradle this would be done like

```
repositories {
    maven {
        name = "centralManualTesting"
        url "https://central.sonatype.com/api/v1/publisher/deployments/download/"
        credentials(HttpHeaderCredentials)
        authentication {
            header(HttpHeaderAuthentication)
        }
    }
    mavenCentral()
}
```


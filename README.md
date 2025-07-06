# IpMailSender

IpMailSender is a Java application that fetches the current public and local IP addresses and sends them via email. This project is useful for scenarios where you need to be notified of your machine's IP address, such as for remote access or dynamic DNS updates.

## Features
- Fetches the current public and local IP addresses
- Sends the IP addresses to a specified email address
- Configurable email settings via a properties file

## Prerequisites
- Java 8 or higher
- Gradle (wrapper included)
- Internet connection (for IP fetching and sending emails)

## Getting Started

### Clone the repository
```sh
git clone https://github.com/Havockeyan/IpMailSender.git
cd IpMailSender
```

### Build the project
```sh
./gradlew build
```

### Run the application
```sh
./gradlew run
```

Or, if you want to run the compiled class directly:
```sh
java -cp build/classes/java/main EmailSender
```

## Configuration

1. Edit `src/main/resources/email.properties` and set:
   - `USERNAME` (your Gmail address)
   - `APP_PASSWORD` (your Gmail app password)
   - `TO_EMAIL` (recipient email address)

   Example:
   ```properties
   USERNAME=your.email@gmail.com
   APP_PASSWORD=your_app_password
   TO_EMAIL=recipient@example.com
   ```

### How to get your Gmail App Password

If you use Gmail, you need to generate an App Password to use with this application (your regular Gmail password will not work).

1. Go to your Google Account: https://myaccount.google.com/apppasswords
2. Navigate to **Security**.
3. Under **Signing in to Google**, enable **2-Step Verification** if you haven't already.
4. After enabling 2-Step Verification, go back to the **Security** section and find **App passwords**.
5. Select **App passwords**. You may need to sign in again.
6. Under **Select app**, choose **Other (Custom name)** and enter a name (e.g., "IpMailSender").
7. Click **Generate**. Google will display a 16-character app password. Copy this password and use it as `APP_PASSWORD` in your `email.properties` file.

> **Note:** The `email.properties` file is listed in `.gitignore` and will not be tracked by git. Do not share this file publicly.

## Project Structure
- `src/main/java/EmailSender.java` - Main class for sending emails
- `src/main/java/IpFetcherApi.java` - Utility for fetching the public and local IP addresses
- `src/main/resources/email.properties` - Email configuration (not tracked by git)
- `build.gradle` - Gradle build configuration

## Continuous Integration (CI) with GitHub Actions

This project uses GitHub Actions to automatically build, test, and package the application on every push and pull request to the `main` branch. The workflow ensures that:

- The code compiles successfully and all tests pass.
- Both a regular JAR and a "fat JAR" (with all dependencies included) are built.
- The resulting JAR files are uploaded as build artifacts, so you can download and run them directly from the GitHub Actions interface.
- Dependency information is submitted to GitHub for security and dependency tracking (enabling Dependabot alerts).

### How it works

1. **Build and Test:**
   - The workflow checks out your code, sets up Java 17, and runs `./gradlew build` and `./gradlew test` to ensure your code compiles and passes all tests.

2. **Build JARs:**
   - It runs `./gradlew shadowJar` to create a fat JAR (includes all dependencies) and also builds the regular JAR.

3. **Upload Artifacts:**
   - Both the fat JAR and the regular JAR are uploaded as downloadable artifacts in the workflow run.

4. **Dependency Submission:**
   - The workflow submits a dependency graph to GitHub, enabling automated security alerts for your dependencies.

You can find the workflow configuration in `.github/workflows/gradle.yml`.

> **Tip:** Download the `*-all.jar` artifact from the Actions tab to get a runnable JAR with all dependencies included.

## Testing

This project includes unit tests for both the IP fetching and email sending logic. The tests use mock data and mocking frameworks to simulate network and email operations, so no real emails are sent and no real network calls are made during testing.

### How to run the tests

Run the following command in your project root:

```sh
./gradlew test
```

### What is tested?
- **IpFetcherApiTest**: Mocks network interfaces and public IP API to verify that the correct local and public IPs are returned.
- **EmailSenderTest**: Mocks the IP fetcher and JavaMail API to verify that the email is constructed and sent correctly, without actually sending an email.

The tests are located in `src/test/java/` and use JUnit 5 and Mockito for mocking.

## Running as a Service on Raspberry Pi

You can run IpMailSender automatically at boot or whenever your network IP changes by setting it up as a systemd service and/or a NetworkManager dispatcher script on your Raspberry Pi.

### 1. Create a systemd Service
Create a file named `ipmailsender.service` in `/etc/systemd/system/` with the following content:

```ini
[Unit]
Description=Run IpMailSender Java app once at boot
After=network-online.target
Wants=network-online.target

[Service]
Type=oneshot
User=pi
WorkingDirectory=/home/pi/myapp
ExecStart=/usr/bin/java -jar /home/pi/myapp/myapp.jar

[Install]
WantedBy=multi-user.target
```

- Adjust `WorkingDirectory` and `ExecStart` to match your JAR location and desired user.

#### Enable and Start the Service
Run these commands:

```bash
sudo systemctl daemon-reload
sudo systemctl enable ipmailsender.service
sudo systemctl start ipmailsender.service
```

### 2. Run on Network Change (Optional)
To run the JAR whenever the network IP changes (for both WiFi and Ethernet), create a dispatcher script:

Create `/etc/NetworkManager/dispatcher.d/99-myapp` with:

```bash
#!/bin/bash

IF=$1
STATUS=$2

# Only run when the connection is up on WiFi (wlan0) or Ethernet (eth0)
if { [ "$IF" = "wlan0" ] || [ "$IF" = "eth0" ]; } && [ "$STATUS" = "up" ]; then
    /usr/bin/java -jar /home/pi/myapp/myapp.jar
fi
```

Make it executable:
```bash
sudo chmod +x /etc/NetworkManager/dispatcher.d/99-myapp
```

This will run your JAR every time the `wlan0` (WiFi) or `eth0` (Ethernet) interface comes up.

## License
This project is licensed under the MIT License.

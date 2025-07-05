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

## License
This project is licensed under the MIT License.

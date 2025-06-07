# IpMailSender

IpMailSender is a Java application that fetches the current public IP address and sends it via email. This project is useful for scenarios where you need to be notified of your machine's IP address, such as for remote access or dynamic DNS updates.

## Features
- Fetches the current public IP address using an API
- Sends the IP address to a specified email address
- Configurable email settings

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
Edit the `EmailSender.java` file to set your email credentials and recipient address. You may also need to configure the SMTP server settings according to your email provider.

## Project Structure
- `src/main/java/EmailSender.java` - Main class for sending emails
- `src/main/java/IpFetcherApi.java` - Utility for fetching the public IP address
- `build.gradle` - Gradle build configuration

## License
This project is licensed under the MIT License.


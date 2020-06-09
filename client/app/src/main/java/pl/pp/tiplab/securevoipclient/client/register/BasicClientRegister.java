package pl.pp.tiplab.securevoipclient.client.register;

import android.os.Build;
import androidx.annotation.RequiresApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import pl.pp.tiplab.securevoipclient.rsa.RSAGenerator;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;

import java.io.File;
import java.io.FileWriter;
import java.util.Optional;
import java.util.Scanner;

@Setter
@Getter
@AllArgsConstructor
public class BasicClientRegister implements ClientRegister {

    private BasicClientData basicClientData;
    private RestTemplate restTemplate;
    private RSAGenerator rsaGenerator;

    @Override
    public boolean isUserRegistered() {
        File file = new File("config.dat");
        return file.exists() && fileReadData(file);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public RegisterResponse registerUser(RegisterRequest request) {
        File file = createConfigFile();
        if (fileReadData(file)) {
            HttpEntity<RegisterRequest> requestHttpEntity = new HttpEntity<>(request);
            RegisterResponse response = Optional.ofNullable(restTemplate.exchange(RegisterConstants.HOST + RegisterConstants.REGISTER_ENDPOINT,
                                                                                  HttpMethod.POST,
                                                                                  requestHttpEntity,
                                                                                  RegisterResponse.class)
                                                                        .getBody())
                                                .orElse(new RegisterResponse());
            return addDataToFile(file, response);
        } else {
            return new RegisterResponse();
        }
    }

    private RegisterResponse addDataToFile(File file, RegisterResponse response) {
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.append(response.getSessionId());
            fileWriter.append(response.getNick());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return response;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private File createConfigFile() {
        File file = new File("config.dat");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.append(rsaGenerator.getPrivateKey()
                                          .toString());
            fileWriter.append(rsaGenerator.getPublicKey()
                                          .toString());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return file;
    }

    private boolean fileReadData(File file) {
        try (Scanner scanner = new Scanner(file)) {

            int lineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (lineCount) {
                    case 0:
                        basicClientData.setPrivateKey(line);
                        break;
                    case 1:
                        basicClientData.setPublicKey(line);
                        break;
                    case 2:
                        basicClientData.setUserToken(line);
                        break;
                    case 3:
                        basicClientData.setNickName(line);
                        break;
                    default:
                        return false;
                }
                lineCount++;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}

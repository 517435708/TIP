package pl.pp.tiplab.securevoipclient.client.register;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.GenericController;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;
import pl.pp.tiplab.securevoipclient.rsa.RsaCoverter;

import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.FILE_NAME;
import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.ONE;
import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.THREE;
import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.TWO;
import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.ZERO;

@Setter
@Getter
public class BasicClientRegister extends GenericController implements ClientRegister {

    private BasicClientData basicClientData;
    private Context context;
    private RegisterService registerService;
    private RsaCoverter rsaCoverter;

    public BasicClientRegister(Context context) {
        super();
        this.context = context;
        registerService = retrofit.create(RegisterService.class);
    }

    @Override
    public boolean isUserRegistered() {
        if(configFileExist()) {
            readDataFromConfigFile();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        RegisterResponse response = registerService.registerUser(request);
        basicClientData.setNickName(response.getNick());
        basicClientData.setUserToken(response.getUserToken());
        return response;
    }


    @SneakyThrows
    private RegisterResponse createFile(RegisterResponse response) {
        String filePath = context.getFilesDir().getPath() + FILE_NAME;
        File file = new File(filePath);
        appendDataToFile(file, response);
        return response;
    }

    private void appendDataToFile(File file, RegisterResponse response) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.append(response.getUserToken());
            fileWriter.append(response.getNick());
        } catch (IOException ex) {
            throw new RegisterException(ex);
        }
    }

    private void readDataFromConfigFile() {
        String filePath = context.getFilesDir().getPath() + FILE_NAME;
        File file = new File(filePath);
        fileReadData(file);
    }

    private boolean configFileExist() {
        String filePath = context.getFilesDir().getPath() + FILE_NAME;
        File file = new File(filePath);
        return file.exists();
    }



    private boolean fileReadData(File file) {
        try (Scanner scanner = new Scanner(file)) {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate();

            int lineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (lineCount) {
                    case ZERO:
                        basicClientData.setPrivateKey(rsaCoverter.privateKeyFromString(line));
                        break;
                    case ONE:
                        basicClientData.setPublicKey(rsaCoverter.publicKeyFromString(line));
                        break;
                    case TWO:
                        basicClientData.setUserToken(line);
                        break;
                    case THREE:
                        basicClientData.setNickName(line);
                        break;
                    default:
                        return false;
                }
                lineCount++;
            }
            return true;
        } catch (IOException | NoSuchAlgorithmException ex) {
            return false;
        }
    }
}

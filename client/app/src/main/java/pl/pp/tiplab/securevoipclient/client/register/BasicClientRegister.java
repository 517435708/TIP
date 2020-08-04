package pl.pp.tiplab.securevoipclient.client.register;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import pl.pp.tiplab.securevoipclient.ApplicationContext;
import pl.pp.tiplab.securevoipclient.GenericController;
import pl.pp.tiplab.securevoipclient.client.BasicClientData;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterRequest;
import pl.pp.tiplab.securevoipclient.client.register.dto.RegisterResponse;
import pl.pp.tiplab.securevoipclient.rsa.BasicConverter;
import pl.pp.tiplab.securevoipclient.rsa.RsaCoverter;

import static pl.pp.tiplab.securevoipclient.client.register.RegisterConstants.*;

@Setter
@Getter
public class BasicClientRegister extends GenericController implements ClientRegister {


    private ApplicationContext context;
    private RegisterService registerService;
    private RsaCoverter rsaCoverter = new BasicConverter();

    public BasicClientRegister(ApplicationContext context) {
        super();
        this.context = context;
        registerService = retrofit.create(RegisterService.class);
    }

    @Override
    public boolean isUserRegistered() {
        return  false; /*
        if(configFileExist()) {
            return readDataFromConfigFile();
        } else {
            return false;
        }*/
    }

    @SneakyThrows
    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        RegisterResponse response = registerService.registerUser(request).execute().body();
        context.getData().setNickName(Objects.requireNonNull(response).getNick());
        context.getData().setUserToken(response.getUserToken());
        return createFile(response);
    }


    @SneakyThrows
    private RegisterResponse createFile(RegisterResponse response) {
        String filePath = context.getContext().getFilesDir().getPath() + FILE_NAME;
        File file = new File(filePath);
        appendDataToFile(file, response);
        return response;
    }

    private void appendDataToFile(File file, RegisterResponse response) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.append(rsaCoverter.stringFromPrivateKey(context.getData().getPrivateKey()));
            fileWriter.append(rsaCoverter.stringFromPublicKey(context.getData().getPublicKey()));
            fileWriter.append(response.getUserToken());
            fileWriter.append(response.getNick());
        } catch (IOException ex) {
            throw new RegisterException(ex);
        }
    }

    private boolean readDataFromConfigFile() {
        String filePath = context.getContext().getFilesDir().getPath() + FILE_NAME;
        File file = new File(filePath);
        return fileReadData(file);
    }

    private boolean configFileExist() {
        String filePath = context.getContext().getFilesDir().getPath() + FILE_NAME;
        File file = new File(filePath);
        return file.exists();
    }



    private boolean fileReadData(File file) {
        try (Scanner scanner = new Scanner(file)) {
            int lineCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                switch (lineCount) {
                    case ZERO:
                        context.getData().setPrivateKey(rsaCoverter.privateKeyFromString(line));
                        break;
                    case ONE:
                        context.getData().setPublicKey(rsaCoverter.publicKeyFromString(line));
                        break;
                    case TWO:
                        context.getData().setUserToken(line);
                        break;
                    case THREE:
                        context.getData().setNickName(line);
                        break;
                    default:
                        return false;
                }
                lineCount++;
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}

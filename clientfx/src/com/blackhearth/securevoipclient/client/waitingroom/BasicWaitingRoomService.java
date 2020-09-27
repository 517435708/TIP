package com.blackhearth.securevoipclient.client.waitingroom;


import com.blackhearth.securevoipclient.client.user.VoIPUser;
import com.blackhearth.securevoipclient.client.waitingroom.WaitingRoomService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.blackhearth.securevoipclient.ApplicationConstants.APPLICATION_ENDPOINT;
import static com.blackhearth.securevoipclient.client.waitingroom.WaitingRoomConstants.WAITING_ROOM_ENDPOINT;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BasicWaitingRoomService implements WaitingRoomService {

    private final RestTemplate restTemplate;

    @Override
    public List<VoIPUser> getUsers() {
        return Stream.of(Objects.requireNonNull(restTemplate.getForEntity(APPLICATION_ENDPOINT + WAITING_ROOM_ENDPOINT,
                                                                          VoIPUser[].class)
                                                            .getBody())).collect(toList());
    }
}

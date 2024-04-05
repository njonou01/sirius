package edu.ssng.ing1.sirius.client.controllers.core;

import edu.ssng.ing1.sirius.business.dto.BeFriend;
import edu.ssng.ing1.sirius.business.dto.BeFriends;
import edu.ssng.ing1.sirius.business.dto.Cities;
import edu.ssng.ing1.sirius.business.dto.Student;
import edu.ssng.ing1.sirius.business.dto.Universities;
import edu.ssng.ing1.sirius.client.requests.friend.FriendCommonRequest;

import java.io.IOException;

public class PreloadRequest {
    private Student self ;
    private BeFriends allFriends;
    private Universities allUniversities;
    private Cities allCities;
    private static PreloadRequest instance;

    private PreloadRequest() throws NullPointerException, IOException, InterruptedException {
        allFriends = FriendCommonRequest.selectFriends(new BeFriend(new Student(2), null));
        // self =

    }

    public static PreloadRequest getInstance() throws NullPointerException, IOException, InterruptedException {
        if (instance == null) {
            instance = new PreloadRequest();
        }
        return instance;
    }

    public BeFriends getAllFriends() throws NullPointerException, IOException, InterruptedException {
        return allFriends;
    }

    public Universities getAllUniversities() {
        return allUniversities;
    }

    public Cities getAllCities() {
        return allCities;
    }
}

package edu.ssng.ing1.sirius.business.dto;

public class BuilderRequest {
    private BeFriends allFriends;
    private Universities allUniversities;
    private Cities allCities;


    private BuilderRequest instance;

    private BuilderRequest() {

    }

    public BuilderRequest getInstance() {
        if (instance == null) {
            instance = new BuilderRequest();
        }
        return instance;
    }

    public static void initializeSignData(){
        
    }
}
